package com.bn.t525_2.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bn.t525_2.mapper.UserMapper;
import com.bn.t525_2.pojo.User;
import com.bn.t525_2.service.UserService;
import com.bn.t525_2.util.JwtUtil;
import com.bn.t525_2.util.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    @ExceptionHandler
    public Map<String, Object> login(String username, String password) {
        log.info("用户名:[[]]", username);
        log.info("密码:[[]]", password);
        Map<String, Object> map = new HashMap<>();

        try {
            User loginDB = userService.login(username, password);
//            生成jwt
            Map<String,String> payload = new HashMap<>();
            payload.put("id", loginDB.getUsername());
            payload.put("name", loginDB.getUsername());
            payload.put("password",loginDB.getPassword());
//            返回token
            String token = JwtUtil.getToken(payload);

            map.put("state", true);
            map.put("msg", "验证成功");
            map.put("token", token);
        } catch (Exception e) {
            map.put("state", false);
            map.put("msg", e.getMessage());
            throw new RuntimeException(e);
        }

        return map;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Map<String, Object> test() {
        Map<String, Object> map = new HashMap<>();
        map.put("state",true);
        map.put("msg","请求成功");
        return map;
    }




}
