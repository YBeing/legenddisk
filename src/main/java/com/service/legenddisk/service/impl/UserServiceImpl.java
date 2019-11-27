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
    public Map login(String username, String password){
        Map map=new HashMap();
        User user = userMapper.login(username, password);
        if(user==null){
            map.put("flag",false);
            map.put("msg","账号密码错误，请重新输入！");
        }else {
            map.put("flag",true);
            map.put("msg","登陆成功！");
        }
        return map;
    }
    public void register(User user){
        userMapper.insert(user);
    }

}
