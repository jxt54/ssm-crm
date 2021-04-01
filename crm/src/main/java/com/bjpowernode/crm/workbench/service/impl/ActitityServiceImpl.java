package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActitityServiceImpl implements ActivityService {
    @Resource
    UserDao userDao;
    @Resource
    ActivityDao activityDao;
    @Resource
    ActivityRemarkDao activityRemarkDao;
    @Override
    public boolean save(Activity activity) {
        System.out.println("进入service");
        int result = activityDao.save(activity);
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {
        Activity activity = (Activity) map.get("activity");
        Integer pageNo = (Integer) map.get("pageNo");
        Integer pageSize = (Integer) map.get("pageSize");
        String name = activity.getName();
        String owner = activity.getOwner();
        String startDate = activity.getStartDate();
        String endDate = activity.getEndDate();
        //计算略过的记录数
        Integer skipCount = (pageNo-1)*pageSize;
        Map<String,Object> map1 = new HashMap<>();
        map1.put("name",name);
        map1.put("owner",owner);
        map1.put("startDate",startDate);
        map1.put("endDate",endDate);
        map1.put("skipCount",skipCount);
        map1.put("pageSize",pageSize);
        Integer total = activityDao.getTotalByCondition(map1);
        List<Activity> aList = activityDao.getActivityListByCondition(map1);
        PaginationVo<Activity> vo = new PaginationVo<>();
        vo.setTotal(total);
        vo.setDataList(aList);
        return vo;

    }

    @Override
    public boolean delete(String[] ids) {
        System.out.println(ids[0]);
        //查询出需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByAids(ids);
        System.out.println(count1);
        //删除备注，返回收到影响的条数（实际删除的数量）
        int count2 = activityRemarkDao.deleteByAids(ids);
        System.out.println(count2);
        if (count1 != count2){
            return false;
        }
        //删除市场活动
        int count3 = activityDao.delete(ids);
        if (count3 != ids.length){
            return false;
        }
        return true;
    }

    @Override
    public Map<String,Object> getUserListAndActivity(String id) {
        Map<String,Object> map = new HashMap<>();
        Activity activity = activityDao.getUserListAndActivity(id);
        System.out.println("==============="+activity.getOwner());

        List<User> list = userDao.select();

        map.put("uList",list);
        map.put("a",activity);

        return map;
    }

    @Override
    public boolean update(Activity activity) {
        int result = activityDao.update(activity);
        if (result == 1){
            return true;
        }
        return false;
    }
}
