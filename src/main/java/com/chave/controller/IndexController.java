package com.chave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
