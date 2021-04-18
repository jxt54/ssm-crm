package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/transaction")
public class TranController {
    @Resource
    private UserService userService;
    @Resource
    private CustomerService customerService;
    @Resource
    private TranService tranService;
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
    @RequestMapping("/save.do")
    public ModelAndView save(String customerName ,Tran tran, HttpSession httpSession){
        ModelAndView mv = new ModelAndView();

        tran.setId(UUIDUtil.getUUID());
        tran.setCreateTime(DateTimeUtil.getSysTime());
        tran.setCreateBy(((User)httpSession.getAttribute("user")).getName());
        tranService.save(tran,customerName);

        mv.setViewName("transaction/index");
        return mv;
    }
    @RequestMapping("/detail.do")
    public ModelAndView detail(String id, HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        Tran tran = tranService.detail(id);

        //从服务器缓存中取出“可能性”
        ServletContext application = request.getServletContext();
        Map<String,String> map = (Map<String,String >)application.getAttribute("pMap");
        String possibility = map.get(tran.getStage());
        tran.setPossibility(possibility);

        mv.addObject("t",tran);
        mv.setViewName("transaction/detail");
        return mv;
    }
    @ResponseBody
    @RequestMapping("/getHistoryListByTranId.do")
    public List<TranHistory> getHistoryListByTranId(String id,HttpServletRequest request){
        ServletContext application = request.getServletContext();
        Map<String,String> map = (Map<String,String >)application.getAttribute("pMap");

        List<TranHistory> list = tranService.getHistoryListByTranId(id);
        for (TranHistory tranHistory : list){
            String stage = tranHistory.getStage();
            String possibility = map.get(stage);
            tranHistory.setPossibility(possibility);
        }
        return list;
    }
}
