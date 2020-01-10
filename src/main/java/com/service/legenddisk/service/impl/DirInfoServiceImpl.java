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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DirInfoServiceImpl implements DirInfoService {
    private static   SFTPUtils sftpUtils=new SFTPUtils();
    @Autowired
    private DirInfoMapper dirInfoMapper;
    @Override
    public void addDir(String dirname, String username,String currentlevelindex,String dirPath) {
        try {
            sftpUtils.makeDir(dirname,username);
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
    }

    @Override
    public List<DirInfo> getLevelOneDirList(String username) {
        return dirInfoMapper.getLevelOneDirList(username);
    }
}
