package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.exception.MyUserException;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ActitityController {

    public Map<String,Object> denglu(HttpServletRequest request, String loginAct, String loginPwd) throws MyUserException {
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        User user = new User();

        user.setLoginAct(loginAct);
        String pwd = MD5Util.getMD5(loginPwd);
        user.setLoginPwd(pwd);
        String ip = request.getRemoteAddr();
        user.setAllowIps(ip);
        System.out.println("ip"+ip);

        User user1 = new User();
        //user1 = userService.denglu(user);

        request.getSession().setAttribute("user",user1);
        return map;
    }
}
