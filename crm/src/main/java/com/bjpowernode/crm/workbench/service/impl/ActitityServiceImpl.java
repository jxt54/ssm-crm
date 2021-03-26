package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
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
    ActivityDao activityDao;
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
}
