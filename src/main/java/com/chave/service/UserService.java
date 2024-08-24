package com.chave.service;

import com.chave.pojo.User;


public interface UserService {
    boolean userRegister(User user);

    User getUserByName(String username);
}
