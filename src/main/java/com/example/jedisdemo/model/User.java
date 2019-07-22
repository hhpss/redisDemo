package com.example.jedisdemo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class User {
    private String email;
    private String password;

    public static String getLoginCountKey(String email){
        return "user:loginCount:"+email;
    }

    public static String getLoginLockKey(String email){
        return "user:loginLock:"+email;
    }

}
