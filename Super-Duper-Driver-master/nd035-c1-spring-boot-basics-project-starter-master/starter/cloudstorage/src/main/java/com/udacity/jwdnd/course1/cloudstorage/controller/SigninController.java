package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signin")
public class SigninController {

//  Đăng nhập tài khoản
    @GetMapping()
    public String loginView() {
        return "signin";
    }
}
