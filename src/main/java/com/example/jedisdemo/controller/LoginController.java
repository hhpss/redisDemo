package com.example.jedisdemo.controller;

import com.example.jedisdemo.model.ResultObject;
import com.example.jedisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResultObject login(String email, String password){
        ResultObject resultObject = userService.login(email,password);
        return resultObject;
    }

}
