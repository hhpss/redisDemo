package com.example.jedisdemo.test;

import com.example.jedisdemo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    //@Autowired
   // private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource(name="redisTemplate")
    private ValueOperations String;

    @Resource(name="redisTemplate")
    private HashOperations<String,String,User> hash;

    public static void main(String[] args) {
        RedisTest test = new RedisTest();
        test.t1();
    }


    @Test
    public void t1(){
        //设置key序列化
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);


        String.set("username","haha");
        System.out.println(String.get("username"));
        User user = new User();
        user.setEmail("123456@qq.com");
        user.setPassword("123456");
        System.out.println(user);

        //存储  对象(实现Serializable)
        hash.put("user",user.getEmail(),user);

        User u = hash.get("user",user.getEmail());
        System.out.println(u);

    }



}
