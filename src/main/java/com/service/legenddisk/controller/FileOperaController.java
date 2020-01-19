/**
 * className:FileOperaController
 * author:Lyibing
 * date: 2019/12/10
 */
package com.service.legenddisk.controller;

import com.alibaba.fastjson.JSON;
import com.service.legenddisk.service.DirInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileOperaController {
    @Resource
    private DirInfoService dirInfoService;
    /**
     * 创建新文件夹&重命名
     * @param reqStr
     * @param request
     * @return void
     */
    @RequestMapping("/makeDir")
    @ResponseBody
    public void makeDir(@RequestBody String  reqStr, HttpServletRequest request){

        HttpSession session=request.getSession();
        String username =(String) session.getAttribute("username");
        Map fileinfo= (Map) JSON.parse(reqStr);
        String directoryname = fileinfo.get("directory").toString();
        String currentLevelIndex = fileinfo.get("index").toString();
        String dirPath = fileinfo.get("index").toString();
        dirInfoService.addDir(directoryname,username,currentLevelIndex,dirPath);


    }
    /**
     * 重命名文件夹
     * @param reqStr
     * @param request
     * @return void
     */
    @RequestMapping("/renameFileDir")
    @ResponseBody
    public void renameFileDir(@RequestBody String  reqStr, HttpServletRequest request){

        HttpSession session=request.getSession();
        Map fileinfo= (Map) JSON.parse(reqStr);
        String oldDirectory = fileinfo.get("oldDirectory").toString();
        String newDirectory = fileinfo.get("newDirectory").toString();

//        try {
//            sftpUtils.renameFileDir(oldDirectory,newDirectory,session);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }
    /**
     * 获取首页的文件夹列表
     * @param reqStr
     * @param request
     * @return void
     */
    @RequestMapping("/getLevelOneDirList")
    @ResponseBody
    public void getLevelOneDirList(@RequestBody String  reqStr, HttpServletRequest request){




    }
    /**
     * 上传
     * @param file
     * @param reqStr
     * @param session
     * @return java.util.Map
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("file")MultipartFile file, @RequestBody String  reqStr, HttpSession session) throws Exception{
        InputStream is=file.getInputStream();
        String filename=file.getOriginalFilename();
        String filepath=null;
        Map map=new HashMap();
        return map;


    }
    @RequestMapping("/createDir")
    @ResponseBody
    public void upload( String  reqStr,HttpSession session) throws Exception{
        String createFileDirName="usr/file/admin/a";

    }
}
