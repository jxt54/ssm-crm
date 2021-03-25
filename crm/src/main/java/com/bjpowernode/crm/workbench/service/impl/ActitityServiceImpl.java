package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
