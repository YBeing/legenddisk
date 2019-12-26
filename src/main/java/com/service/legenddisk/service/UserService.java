package com.service.legenddisk.service;

import com.service.legenddisk.pojo.User;

import java.util.Map;

public interface UserService {
    public Map login(String username, String password);
    public Map register(User user);
}
