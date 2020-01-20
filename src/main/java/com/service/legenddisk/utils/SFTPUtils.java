/**
 * className:SFTPUtils
 * author:Lyibing
 * date: 2019/12/25
 */
package com.service.legenddisk.utils;

import com.jcraft.jsch.*;
import com.service.legenddisk.config.SftpConfig;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/*sftp操作工具类*/
public class SFTPUtils {
    private long count;
    /**
     * 已经连接次数
     */
    private long count1 = 0;

    private long sleepTime;
    private  static Logger ftplogger=Logger.getLogger("ftpLog");
    private static String ftp_host =ConfigureParser.getPropert("ftp_host");
    private static Integer ftp_port =Integer.parseInt(ConfigureParser.getPropert("ftp_port"));
    private static String username =ConfigureParser.getPropert("username");
    private static String password =ConfigureParser.getPropert("password");
    private static Integer timeout =Integer.parseInt(ConfigureParser.getPropert("timeout"));
    private static String remoteRootPath =ConfigureParser.getPropert("remoteRootPath");
    private  static  final SftpConfig sftpConfig=new SftpConfig(ftp_host, ftp_port, username, password, timeout, remoteRootPath);

    /**
     * 连接sftp服务器
     *
     * @return
     */
    public ChannelSftp connect(SftpConfig sftpConfig) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(sftpConfig.getUsername(), sftpConfig.getHostname(), sftpConfig.getPort());
            Session sshSession = jsch.getSession(sftpConfig.getUsername(), sftpConfig.getHostname(), sftpConfig.getPort());
            ftplogger.info("Session created ... UserName=" + sftpConfig.getUsername() + ";host=" + sftpConfig.getHostname() + ";port=" + sftpConfig.getPort());
            sshSession.setPassword(sftpConfig.getPassword());
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            ftplogger.info("Session connected ...");
            ftplogger.info("Opening Channel ...");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            ftplogger.info("登录成功");
        } catch (Exception e) {
            try {
                count1 += 1;
                if (count == count1) {
                    throw new RuntimeException(e);
                }
                Thread.sleep(sleepTime);
                ftplogger.info("重新连接....");
                connect(sftpConfig);
            } catch (InterruptedException e1) {
                throw new RuntimeException(e1);
            }
        }
        return sftp;
    }

    /**
     * 上传文件
     *
     * @param directory  上传的目录
     * @param uploadFile 要上传的文件
     * @param
     */
    public void upload(String directory, String uploadFile) {
        ChannelSftp sftp = connect(sftpConfig);
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            try {
                sftp.mkdir(directory);
                sftp.cd(directory);
            } catch (SftpException e1) {
                throw new RuntimeException("ftp创建文件路径失败" + directory);
            }
        }
        File file = new File(uploadFile);
        InputStream inputStream=null;
        try {
            inputStream = new FileInputStream(file);
            sftp.put(inputStream, file.getName());
        } catch (Exception e) {
            throw new RuntimeException("sftp异常" + e);
        } finally {
            disConnect(sftp);
            closeStream(inputStream,null);
        }
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public void download(String directory, String downloadFile, String saveFile ) {
        OutputStream output = null;
        try {
            File localDirFile = new File(saveFile);
            // 判断本地目录是否存在，不存在需要新建各级目录
            if (!localDirFile.exists()) {
                localDirFile.mkdirs();
            }
                ftplogger.info("开始获取远程文件:[{}]---->[{}]");
            ChannelSftp sftp = connect(sftpConfig);
            sftp.cd(directory);
                ftplogger.info("打开远程文件:[{}]");
            output = new FileOutputStream(new File(saveFile.concat(File.separator).concat(downloadFile)));
            sftp.get(downloadFile, output);
                ftplogger.info("文件下载成功");
            disConnect(sftp);
        } catch (Exception e) {
            if (ftplogger.isInfoEnabled()) {
                ftplogger.info("文件下载出现异常，[{}]", e);
            }
            throw new RuntimeException("文件下载出现异常，[{}]", e);
        } finally {
            closeStream(null,output);
        }
    }
    /**
     * 新建文件夹
     *
     * @param remoteFilePath
     * @throws Exception
     */
    public Map makeDir(String remoteFilePath, String username) throws Exception {
        Map map=new HashMap();
        try {
            ChannelSftp sftp = connect(sftpConfig);
            setEncodingUTF8(sftp);
            String path2=sftpConfig.getRemoteRootPath()+username+"/"+remoteFilePath;
            if (isDirExist(path2,sftp)) {
                map.put("msg","该路径已经存在!");
                ftplogger.info("该路径已经存在！");
            }else{
                String pathArry[] = path2.split("/");
                String checkpath="";

                for (String path : pathArry) {
                    if (path.equals("")) {
                        continue;
                    }
                    checkpath=checkpath+"/"+path;

                    if (isDirExist(checkpath,sftp)) {
                        sftp.cd(path);

                    }else {
                        String currentpath=checkpath.substring(0,checkpath.lastIndexOf("/"));
                        sftp.cd(currentpath);
                        // 建立目录
                        sftp.mkdir(path);
                        // 进入并设置为当前目录
                        sftp.cd(path);
                    }
                }
                map.put("msg","创建成功");
            }

        } catch (Exception e1) {
            throw new RuntimeException("ftp创建文件路径失败  " + e1.getMessage());
        }
        return map;

    }
    /**
     * 重命名文件夹
     *
     * @throws Exception
     */
    public void renameFileDir(String oldDir, String newDir, HttpSession session) throws Exception {
        try {
            String username=(String)session.getAttribute("username");
            ChannelSftp sftp = connect(sftpConfig);
            setEncodingUTF8(sftp);
            String newpath=sftpConfig.getRemoteRootPath()+username+"/"+newDir;
            String oldpath=sftpConfig.getRemoteRootPath()+username+"/"+oldDir;
            sftp.rename(oldpath,newpath);


        } catch (Exception e1) {
            throw new RuntimeException("ftp创建文件路径失败  " + e1.getMessage());
        }

    }
    /**
     * 下载远程文件夹下的所有文件
     *
     * @param remoteFilePath
     * @param localDirPath
     * @throws Exception
     */
    public void getFileDir(String remoteFilePath, String localDirPath ) throws Exception {
        File localDirFile = new File(localDirPath);
        // 判断本地目录是否存在，不存在需要新建各级目录
        if (!localDirFile.exists()) {
            localDirFile.mkdirs();
        }
            ftplogger.info("sftp文件服务器文件夹[{}],下载到本地目录[{}]");
        ChannelSftp channelSftp = connect(sftpConfig);
        Vector<ChannelSftp.LsEntry> lsEntries = channelSftp.ls(remoteFilePath);
            ftplogger.info("远程目录下的文件为[{}]");
        for (ChannelSftp.LsEntry entry : lsEntries) {
            String fileName = entry.getFilename();
            if (checkFileName(fileName)) {
                continue;
            }
            String remoteFileName = getRemoteFilePath(remoteFilePath, fileName);
            channelSftp.get(remoteFileName, localDirPath);
        }
        disConnect(channelSftp);
    }

    /**
     * 关闭流
     * @param outputStream
     */
    private void closeStream(InputStream inputStream,OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(inputStream != null){
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkFileName(String fileName) {
        if (".".equals(fileName) || "..".equals(fileName)) {
            return true;
        }
        return false;
    }

    private String getRemoteFilePath(String remoteFilePath, String fileName) {
        if (remoteFilePath.endsWith("/")) {
            return remoteFilePath.concat(fileName);
        } else {
            return remoteFilePath.concat("/").concat(fileName);
        }
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @param sftp
     */
    public void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory  要列出的目录
     * @return
     * @throws SftpException
     */
    public List<String> listFiles(String directory ) throws SftpException {
        ChannelSftp sftp = connect(sftpConfig);
        List fileNameList = new ArrayList();
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            return fileNameList;
        }
        Vector vector = sftp.ls(directory);
        for (int i = 0; i < vector.size(); i++) {
            if (vector.get(i) instanceof ChannelSftp.LsEntry) {
                ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) vector.get(i);
                String fileName = lsEntry.getFilename();
                if (".".equals(fileName) || "..".equals(fileName)) {
                    continue;
                }
                fileNameList.add(fileName);
            }
        }
        disConnect(sftp);
        return fileNameList;
    }

    /**
     * 断掉连接
     */
    public void disConnect(ChannelSftp sftp) {
        try {
            sftp.disconnect();
            sftp.getSession().disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 判断目录是否存在
     */
    public static boolean isDirExist(String directory,ChannelSftp sftp) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }
    /**
     *  处理上传文件夹的中文乱码问题
     * @param sftp
     * @return void
     */
    public static void setEncodingUTF8(ChannelSftp sftp) {

        /*通过反射处理上传文件夹的中文乱码问题*/
        try {
            Class<ChannelSftp> c = ChannelSftp.class;
            Field f = null;
            f = c.getDeclaredField("server_version");
            f.setAccessible(true);
            f.set(sftp, 2);
            sftp.setFilenameEncoding("gbk");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public SFTPUtils(long count, long sleepTime) {
        this.count = count;
        this.sleepTime = sleepTime;
    }

    public SFTPUtils() {

    }

    public static void main(String[] args) {
        SFTPUtils ftp = new SFTPUtils(3, 6000);
        try {
            List<String> list = ftp.listFiles("/usr/src");
            ftplogger.info("文件上传下载详情" );
        } catch (SftpException e) {
            ftplogger.error("文件上传下载异常:[{}]" );
        }
    }
}

