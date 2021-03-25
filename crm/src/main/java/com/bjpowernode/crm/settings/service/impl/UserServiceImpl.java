package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.exception.MyUserException;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    UserDao userDao;
    @Override
    public User denglu(User user) throws MyUserException {

        System.out.println("输入的账号："+user.getLoginAct());
        System.out.println("输入的密码："+user.getLoginPwd());

        User user1 = new User();
        user1 = userDao.login(user);
        //验证账号密码
        if (user1 == null){
            throw new LoginException("账号或密码错误");
        }
        //验证ip地址
        if (!user1.getAllowIps().contains(user.getAllowIps())){
            throw new LoginException("ip地址受限");
        }
        //验证账号是否锁定
        if ("0".equals(user1.getLockState())){
            throw new LoginException("账号已被锁定");
        }
        //验证失效时间
        String expireTime = user1.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime) < 0){
            throw new LoginException("账号已失效");
        }
        return user1;
    }
    public List<User> getUserList(){
        List<User> uList = new ArrayList<>();
        uList = userDao.select();
        return uList;
    }
}
