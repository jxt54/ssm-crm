package com.bjpowernode.crm.settings.handler;

import com.bjpowernode.crm.exception.LoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = LoginException.class)
    @ResponseBody
    public Map<String,Object> loginException(Exception e){
        String msg = e.getMessage();
        Map<String,Object> map = new HashMap<>();
        map.put("success",false);
        map.put("msg",msg);
        System.out.println("进入异常类");
        System.out.println(map.get("success")+"  "+map.get("msg"));
        return map;
    }
}
