package com.bn.t525_2.service.impl;

import com.bn.t525_2.mapper.UserMapper;
import com.bn.t525_2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl implements com.bn.t525_2.service.UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional (propagation = Propagation.SUPPORTS)
    public User login(String username, String password) {
        User userDB = userMapper.login(username, password);
        if (userDB != null) {
            return userDB;
        }
        throw new RuntimeException("登录失败");
    }
}

