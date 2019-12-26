/**
 * className:DirInfoService
 * author:liuyibing
 * date:2019/12/26
 */
package com.service.legenddisk.service;

import com.service.legenddisk.pojo.DirInfo;

import java.util.List;

public interface DirInfoService {
    public void addDir(String dirname,String username,String currentlevelindex);
    public List<DirInfo> getLevelOneDirList(String username);
}
