package com.example.jedisdemo.service.impl;

import com.example.jedisdemo.model.ResultObject;
import com.example.jedisdemo.model.User;
import com.example.jedisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ResultObject login(String email, String password) {
        ResultObject resultObject = new ResultObject();
        ResultObject result = userLoginLock(email);
        if(result.isSuccess()){   //当用户被限制登录，返回剩余限制时间
            resultObject.setSuccess(false);
            resultObject.put("msg","该用户已被锁定登录，剩余解锁时间"+result.get("expired")+"分钟!");
            return resultObject;
        }else{  //当用户没有被限制登录
            //登录成功******

            //登录失败
            int num = 5;    //限制3分钟内只能登录5次
            String key = User.getLoginCountKey(email);
            if (!redisTemplate.hasKey(key)){
                redisTemplate.opsForValue().set(key,"1");
                redisTemplate.expire(key,3,TimeUnit.MINUTES);
                resultObject.setSuccess(false);
                resultObject.put("msg","登录失败，180秒内剩余登录次数4次!");
                return resultObject;
            }else{
                int loginCount = Integer.parseInt(redisTemplate.opsForValue().get(key));
                if(loginCount < 4){
                    redisTemplate.opsForValue().increment(key,1);
                    long time = redisTemplate.getExpire(key,TimeUnit.SECONDS);
                    resultObject.setSuccess(false);
                    resultObject.put("msg","登录失败，"+time+"秒内剩余登录次数"+(time - loginCount)+"次!");
                    return resultObject;
                }else{  //禁止2小时内登录
                    String lockkey = User.getLoginLockKey(email);
                    redisTemplate.opsForValue().set(key,"1");
                    redisTemplate.expire(key,2,TimeUnit.HOURS);
                    resultObject.setSuccess(false);
                    resultObject.put("msg","登录失败，该用户已被限制登录！");
                    return resultObject;

                }

            }


        }

    }

    private ResultObject userLoginLock(String email){
        String key = User.getLoginLockKey(email);
        ResultObject resultObject = new ResultObject();
        resultObject.setSuccess(true);
        if (redisTemplate.hasKey(key)){
            resultObject.setSuccess(false);
            resultObject.put("expired",redisTemplate.getExpire(key, TimeUnit.MINUTES));
        }
        return resultObject;
    }


}
