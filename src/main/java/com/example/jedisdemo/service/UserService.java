package com.example.jedisdemo.service;

import com.example.jedisdemo.model.ResultObject;
import org.springframework.stereotype.Service;


public interface UserService {

    public ResultObject login(String email, String password);

}
