package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Resource
    private UserService userService;
    @ResponseBody
    @RequestMapping("/getUserList.do")
    public List<User> getUserList(){
        return userService.getUserList();
    }
}
