package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
        Map<String,Object> map = new HashMap<>();
        map.put("activity",activity);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);

        return activityService.pageList(map);
    }
    @ResponseBody
    @RequestMapping("/delete.do")
    public boolean delete (@RequestParam(value = "id")String[] ids){   // 前端传过来的参数是id,后端变量名称变成ids使用
        return activityService.delete(ids);
    }
    @ResponseBody
    @RequestMapping("/getUserListAndActivity.do")
    public Map<String,Object> getUserListAndActivity(String id){
        return activityService.getUserListAndActivity(id);
    }
    @ResponseBody
    @RequestMapping("/update.do")
    public boolean update(Activity activity){
        return activityService.update(activity);
    }


    @RequestMapping("/detail.do")
    public ModelAndView detail(String id){
        Activity activity = activityService.detail(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("a",activity);
        mv.setViewName("activity/detail");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/activityRemarkListByAid.do")
    public List<ActivityRemark> activityRemarkListByAid(String activityId){
        return activityService.getRemarkListByAid(activityId);
    }
    @ResponseBody
    @RequestMapping("/deleteRemark.do")
    public boolean deleteRemark(String id){
        return activityService.deleteRemark(id);
    }
    @ResponseBody
    @RequestMapping("/saveRemark.do")
    public Map<String,Object> saveRemark(HttpSession session,ActivityRemark activityRemark){
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setCreateBy(((User)(session.getAttribute("user"))).getName());
        activityRemark.setEditFlag("0");
        return activityService.saveRemark(activityRemark);
    }
    @ResponseBody
    @RequestMapping("/updateRemark.do")
    public Map<String,Object> updateRemark(HttpSession session,ActivityRemark activityRemark){
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        activityRemark.setEditBy(((User)(session.getAttribute("user"))).getName());
        activityRemark.setEditFlag("1");
        return activityService.updateRemark(activityRemark);
    }
}
