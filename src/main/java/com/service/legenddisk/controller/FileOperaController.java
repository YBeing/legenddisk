/**
 * className:FileOperaController
 * author:Lyibing
 * date: 2019/12/10
 */
package com.service.legenddisk.controller;

import com.service.legenddisk.utils.FtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private FtpUtils ftpUtils;
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
        boolean uploadFlag = ftpUtils.uploadFile(filename, is,session,reqStr);
        if (uploadFlag){
            filepath="/usr/images/"+filename;
            map.put("flag",true);
        }else {
            map.put("flag",false);

        }
        return map;


    }
    @RequestMapping("/createDir")
    @ResponseBody
//    public void upload( @RequestBody String  reqStr,HttpSession session) throws Exception{
    public void upload( String  reqStr,HttpSession session) throws Exception{
//        Map map= (Map) JSON.parse(reqStr);
//        String filepath = map.get("filepath").toString();
        String createFileDirName="usr/file/admin/a";
//        User user = (User)session.getAttribute("user");
//        createFileDirName=createFileDirName+user.getUsername()+"/"+filepath;
        ftpUtils.createDir(createFileDirName);

    }
}
