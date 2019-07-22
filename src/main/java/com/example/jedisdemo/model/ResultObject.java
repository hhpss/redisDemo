package com.example.jedisdemo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ResultObject {

    private boolean success;
    private Map<String,Object> data;

    public void put(String key, Object obj){
        data.put(key,obj);
    }

    public Object get(String key){
        return data.get(key);
    }


}
