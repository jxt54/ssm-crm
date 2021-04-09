package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Resource
    private UserService userService;
    @Resource
    private ClueService clueService;
    @Resource
    private ActivityService activityService;
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
    @ResponseBody
    @RequestMapping("/pageList.do")
    public PaginationVo<Clue> pageList(Integer pageNo,Integer pageSize){
        return clueService.pageList(pageNo,pageSize);
    }
    @RequestMapping("/detail.do")
    public ModelAndView detail(String id){
        Clue clue = clueService.detail(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("c",clue);
        mv.setViewName("clue/detail");
        return mv;
    }
    @ResponseBody
    @RequestMapping("/getActivityListByClueId.do")
    public List<Activity> getActivityListByClueId(String clueId){
        return activityService.getActivityListByClueId(clueId);
    }
    @ResponseBody
    @RequestMapping("/unbund.do")
    public boolean unbund(String id){
        return clueService.unbund(id);
    }
    @ResponseBody
    @RequestMapping("/getActivityByNameAndNotByClueId.do")
    public List<Activity> getActivityByNameAndNotByClueId(String aname,String clueId){
        Map<String,String> map = new HashMap<>();
        map.put("aname",aname);
        map.put("clueId",clueId);
        return activityService.getActivityByNameAndNotByClueId(map);
    }
    @ResponseBody
    @RequestMapping("/bund.do")
    public boolean bund(@RequestParam(value = "cid") String cid,@RequestParam(value = "aid") String[] aid){
        return clueService.bund(cid,aid);
    }
    @ResponseBody
    @RequestMapping("getActivityListByName.do")
    public List<Activity> getActivityListByName(String aname){
        return activityService.getActivityListByName(aname);
    }
}
