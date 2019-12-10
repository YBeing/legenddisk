/**
 * className:FtpUtils
 * author:Lyibing
 * date: 2019/12/10
 */
package com.service.legenddisk.utils;

import com.service.legenddisk.pojo.User;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public   class   FtpUtils {
    private static final String ftp_address="192.168.198.128";
    private static final int ftp_port=22;
    private static final String ftp_username="root";
    private static final String ftp_password="root";
    private static final String ftp_filepath="/usr/file/";
    /*上传文件*/
    public  boolean uploadFile(String originFileName, InputStream input, HttpSession session,String currentPath) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            int reply;
            ftp.connect(ftp_address, ftp_port);// 连接FTP服务器
            ftp.login(ftp_username, ftp_password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            User user = (User)session.getAttribute("user");
            ftp.changeWorkingDirectory(ftp_filepath+user.getUsername()+"/"+currentPath);
            ftp.storeFile(originFileName, input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }
    /*新建文件夹*/
    public void createDir(String createFileDirName){
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            int reply;
            ftp.connect(ftp_address, ftp_port);// 连接FTP服务器
            ftp.login(ftp_username, ftp_password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(createFileDirName);
            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
    }
    /*删除文件夹*/
    public void deleteFile(String FileDirName){
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            int reply;
            ftp.connect(ftp_address, ftp_port);// 连接FTP服务器
            ftp.login(ftp_username, ftp_password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.removeDirectory(FileDirName);
            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
    }
    /*读取指定目录下的文件名*/
    public List<String> getFilelist(String filepath ){
        boolean success = false;
        FTPClient ftp = new FTPClient();
        List<String> filenamelist=new ArrayList<String>();
        ftp.setControlEncoding("GBK");
        try {
            FTPFile[]  ftpfiles=null;
            int reply;
            ftp.connect(ftp_address, ftp_port);// 连接FTP服务器
            ftp.login(ftp_username, ftp_password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
            }

            ftp.changeWorkingDirectory(filepath);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpfiles=ftp.listFiles(filepath);
            for (int i = 0; i < ftpfiles.length; i++) {
                FTPFile file=ftpfiles[i];
                filenamelist.add(file.getName());
                
            }
            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return  filenamelist;
    }

}
