package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/activity")
public class ActitityController {
    @Resource
    private UserService userService;
    @Resource
    private ActivityService activityService;
    @ResponseBody
    @RequestMapping("/getUserList.do")
    public List<User> getUserList() {
        List<User> list = new ArrayList<>();

        list = userService.getUserList();
        return list;
    }
    @ResponseBody
    @RequestMapping("/save.do")
    public boolean save(HttpSession session, Activity activity){
        System.out.println("进入controller");
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        activity.setCreateBy(((User)(session.getAttribute("user"))).getName());
        return activityService.save(activity);
    }
    @ResponseBody
    @RequestMapping("/pageList.do")
    public PaginationVo<Activity> pageList(Activity activity, Integer pageNo, Integer pageSize){
        System.out.println(pageNo+"   "+pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("activity",activity);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        return activityService.pageList(map);
    }
}
