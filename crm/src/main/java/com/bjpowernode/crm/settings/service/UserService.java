package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.exception.MyUserException;
import com.bjpowernode.crm.settings.domain.User;

public interface UserService {
    User denglu(User user) throws MyUserException;
}
