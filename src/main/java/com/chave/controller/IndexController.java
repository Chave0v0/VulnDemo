package com.chave.controller;

import com.chave.security.NoReflectionSecurityManager;
import com.oceanbase.jdbc.Driver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.Properties;

@Controller
public class IndexController {
    // 未经身份认证自动跳转登录
    @GetMapping("/login.jsp")
    public String toLoginPage_default() {
        return "login";
    }

    // 主页
    @RequestMapping("/index.html")
    public String index() {
        return "index";
    }

    // SecurityManager测试
    @RequestMapping("/setup")
    @ResponseBody
    public String setup() {
        try {
            System.setSecurityManager(new NoReflectionSecurityManager());
            return "setup success";
        } catch (Exception e) {
            return e.toString();
        }
    }

    // SecurityManager测试
    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        try {
            Method connect = Driver.class.getDeclaredMethod("connect", String.class, Properties.class);
            connect.setAccessible(true);
            return connect.toString();
        } catch (Exception e) {
            return "deny";
        }
    }
}
