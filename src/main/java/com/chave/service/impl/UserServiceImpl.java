package com.chave.service.impl;

import com.chave.mapper.UserMapper;
import com.chave.pojo.User;
import com.chave.service.UserService;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean userRegister(User user) {
        if (getUserByName(user.getUsername()) == null) {
            //带盐+迭代加密+md5（3次）
            String encPassword = new Md5Hash(user.getPassword(), user.getSalt(),3).toString();
            user.setPassword(encPassword);
            if (userMapper.insertUser(user) > 0) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }
}
