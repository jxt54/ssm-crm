package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Resource
    private UserService userService;
    @Resource
    private ClueService clueService;
    @ResponseBody
    @RequestMapping("/getUserList.do")
    public List<User> getUserList(){
        return userService.getUserList();
    }
    @ResponseBody
    @RequestMapping("/save.do")
    public boolean save(HttpSession httpSession,Clue clue){
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateBy(((User)(httpSession.getAttribute("user"))).getName());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        System.out.println(clue.getCompany());
        return clueService.save(clue);
    }
}
