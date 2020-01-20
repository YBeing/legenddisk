/**
 * className:DirInfoService
 * author:liuyibing
 * date:2019/12/26
 */
package com.service.legenddisk.service;

import com.service.legenddisk.pojo.DirInfo;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface DirInfoService {
    public Map addDir(String dirname, String username, String currentlevelindex, String dirPath);
    public List<DirInfo> getLevelOneDirList(String username);
    public List<DirInfo> renameFileDir(String oldDir, String newDir, Integer did,HttpSession session)  ;
}
