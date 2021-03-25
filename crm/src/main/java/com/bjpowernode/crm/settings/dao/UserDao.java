package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.User;

import java.util.List;

public interface UserDao {
    User login(User user);
    List<User> select();
}
