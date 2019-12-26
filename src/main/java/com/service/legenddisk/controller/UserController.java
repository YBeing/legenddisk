package com.service.legenddisk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.service.legenddisk.pojo.User;
import com.service.legenddisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/login")
    @ResponseBody
    public Map login(@RequestBody String  reqStr,HttpSession session){
        Map user= (Map)JSON.parse(reqStr);
        String username = user.get("username").toString();
        String password = user.get("password").toString();
        Map loginuser = userService.login(username, password);
        User user1 = (User)loginuser.get("user");
        session.setAttribute("username",user1.getUsername());
        return loginuser;
    }
    @RequestMapping("/register")
    public Map register(@RequestBody String  reqStr){
        Map user= (Map)JSON.parse(reqStr);
        String u = (String) user.get("record");
        JSON j=(JSON) JSON.parse(u);
        User user1 = JSONObject.toJavaObject(j, User.class);
        Map map = userService.register(user1);
        return map;
    }
}
