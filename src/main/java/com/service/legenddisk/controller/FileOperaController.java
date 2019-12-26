/**
 * className:FileOperaController
 * author:Lyibing
 * date: 2019/12/10
 */
package com.service.legenddisk.controller;

import com.alibaba.fastjson.JSON;
import com.service.legenddisk.utils.SFTPUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileOperaController {
    private static   SFTPUtils sftpUtils=new SFTPUtils();
    @RequestMapping("/makeDir")
    @ResponseBody
    public void makeDir(@RequestBody String  reqStr, HttpServletRequest request){
        HttpSession session=request.getSession();
        Map fileinfo= (Map) JSON.parse(reqStr);
        String directory = fileinfo.get("directory").toString();

        try {
            sftpUtils.makeDir(directory,session);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @RequestMapping("/test")
    @ResponseBody
    public void makeDir(HttpServletRequest request){
        HttpSession session=request.getSession();


        String username=(String)session.getAttribute("username");
        System.out.println(username);



    }
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
