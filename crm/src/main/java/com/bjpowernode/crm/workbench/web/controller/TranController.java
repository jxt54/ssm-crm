package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/workbench/transaction")
public class TranController {
    @Resource
    private UserService userService;
    @Resource
    private CustomerService customerService;
    @RequestMapping("/add.do")
    public ModelAndView add(){
        ModelAndView mv = new ModelAndView();
        List<User> uList = userService.getUserList();
        mv.addObject("uList",uList);
        mv.setViewName("transaction/save");
        return mv;
    }
    @ResponseBody
    @RequestMapping("/getCustomerName.do")
    public List<String> getCustomerName(String name){
        return customerService.getCustomerName(name);
    }
}
