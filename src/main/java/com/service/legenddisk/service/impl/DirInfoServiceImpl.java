/**
 * className:DirInfoServiceImpl
 * author:Lyibing
 * date: 2019/12/26
 */
package com.service.legenddisk.service.impl;

import com.service.legenddisk.mapper.DirInfoMapper;
import com.service.legenddisk.pojo.DirInfo;
import com.service.legenddisk.service.DirInfoService;
import com.service.legenddisk.utils.SFTPUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DirInfoServiceImpl implements DirInfoService {
    private static   SFTPUtils sftpUtils=new SFTPUtils();
    @Resource
    private DirInfoMapper dirInfoMapper;
    @Override
    public Map addDir(String dirname, String username, String currentlevelindex, String dirPath) {
        Map map=new HashMap();
        try {
            map= sftpUtils.makeDir(dirname,username);
            DirInfo dirInfo=new DirInfo();
            dirInfo.setDirlevel(Integer.parseInt(currentlevelindex));
            dirInfo.setDirname(dirname);
            dirInfo.setUsername(username);
            dirInfo.setType("1");
            dirInfo.setDirpath(dirPath);
            dirInfoMapper.insert(dirInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<DirInfo> getLevelOneDirList(String username) {
        return dirInfoMapper.getLevelOneDirList(username);
    }

    @Override
    public List<DirInfo> renameFileDir(String oldDir, String newDir,Integer did, HttpSession session)   {
        try {
            sftpUtils.renameFileDir(oldDir,newDir,session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dirInfoMapper.updateFilename(newDir,did);
        return null;
    }
}
