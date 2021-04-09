package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity);
    PaginationVo<Activity> pageList(Map<String,Object> map);
    boolean delete(String[] ids);
    Map<String,Object> getUserListAndActivity(String id);
    boolean update(Activity activity);
    Activity detail(String id);
    List<ActivityRemark> getRemarkListByAid(String activityId);
    boolean deleteRemark(String id);
    Map<String,Object> saveRemark(ActivityRemark activityRemark);
    Map<String,Object> updateRemark(ActivityRemark activityRemark);
    List<Activity> getActivityListByClueId(String clueId);
    List<Activity> getActivityByNameAndNotByClueId(Map<String,String> map);
    List<Activity> getActivityListByName(String aname);
}
