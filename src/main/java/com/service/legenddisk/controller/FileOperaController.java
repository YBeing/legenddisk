/**
 * className:FileOperaController
 * author:Lyibing
 * date: 2019/12/10
 */
package com.service.legenddisk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileOperaController {

    @RequestMapping("/makeDir")
    public void makeDir(){

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
