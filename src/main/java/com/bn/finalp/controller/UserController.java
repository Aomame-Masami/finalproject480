package com.bn.finalp.controller;

import com.bn.finalp.pojo.User;
import com.bn.finalp.service.UserService;
import com.bn.finalp.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
