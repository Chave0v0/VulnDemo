package com.chave.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chave.pojo.User;
import com.chave.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    Logger logger = LogManager.getLogger(UserController.class);


    @GetMapping("/user/login")
    public String toLoginPage() {
        return "login";
    }

    @GetMapping("/user/register")
    public String toRegisterPage() {
        return "register";
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> userRegister(@RequestBody String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String username = (String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");

        User user = new User(username, password);

        // 用于存放json响应信息
        Map<String, Object> response = new HashMap<>();
        if (userService.userRegister(user)) {
            logger.info("[+] reg sucess. username:" + username + " password:" + password);
            // 返回成功的 JSON 响应
            response.put("message", "Register Success");
            response.put("success", true);
            return ResponseEntity.ok(response);
        } else {
            logger.info("[-] reg failed. username:" + username + " password:" + password);
            response.put("message", "Register Failed");
            response.put("success", false);
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> userLogin(@RequestBody String jsonString, HttpSession session) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String username = (String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");

        // 获取subject对象
        Subject subject = SecurityUtils.getSubject();
        // 封装请求数据到token
        AuthenticationToken token = new UsernamePasswordToken(username, password);
        // 用于存放json响应信息
        Map<String, Object> response = new HashMap<>();
        try {
            //调用login方法进行登录
            subject.login(token);
            logger.info("[+] login success. username:" + username + " password:" + password);
            // 获取用户信息（假设你有 UserService 来处理用户数据）
            User user = userService.getUserByName(username);
            session.setAttribute("user", user);
            // 返回成功的 JSON 响应
            response.put("message", "Login Success");
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("[-] login failed. username:" + username + " password:" + password);
            response.put("message", "Login Failed");
            response.put("success", false);
            return ResponseEntity.status(401).body(response);
        }
    }

    @GetMapping("/user/home")
    public String userHome() {
        return "home";
    }
}
