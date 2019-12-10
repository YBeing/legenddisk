package com.service.legenddisk.service.impl;

import com.service.legenddisk.mapper.UserMapper;
import com.service.legenddisk.pojo.User;
import com.service.legenddisk.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    public Map login(String username, String password, HttpSession session){
        Map map=new HashMap();
        User user = userMapper.login(username, password);
        if(user==null){
            map.put("flag",false);
            map.put("msg","账号密码错误，请重新输入！");
        }else {
            map.put("flag",true);
            map.put("msg","登陆成功！");
            session.setAttribute("user",user);
        }
        return map;
    }
    public Map register(User user){
        Map map=new HashMap();
        int effectRows=userMapper.register(user);
        if (effectRows==1){
            map.put("addflag",true);
        }else{
            map.put("addflag",false);

        }
        return map;

    }

}
