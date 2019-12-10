package com.service.legenddisk.service;

import com.service.legenddisk.pojo.User;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService {
    public Map login(String username, String password, HttpSession session);
    public Map register(User user);
}
