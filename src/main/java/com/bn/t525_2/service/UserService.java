package com.bn.t525_2.service;

import com.bn.t525_2.pojo.User;
import org.springframework.stereotype.Service;

public interface UserService {

    User login(String username, String password);
}
