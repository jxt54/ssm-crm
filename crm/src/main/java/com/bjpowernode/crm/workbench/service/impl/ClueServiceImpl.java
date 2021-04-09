package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.dao.ClueActivityRelationDao;
import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    private ClueDao clueDao;
    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;
    @Override
    public PaginationVo<Clue> pageList(Integer pageNo, Integer pageSize) {
        //计算略过的记录数
        Integer skipCount = (pageNo-1)*pageSize;
        System.out.println(skipCount);

        Integer total = clueDao.getTotalByCondition();
        /*Map<String,Integer> map = new HashMap<>();
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        List<Clue> cList = clueDao.getClueListByCondition(map);*/
        List<Clue> cList = clueDao.getClueListByCondition(skipCount,pageSize);
        PaginationVo<Clue> paginationVo = new PaginationVo<>();
        paginationVo.setTotal(total);
        paginationVo.setDataList(cList);

        return paginationVo;
    }

    @Override
    public boolean save(Clue clue) {
        System.out.println(clue.getCompany());
        int result = clueDao.save(clue);
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public Clue detail(String id) {
        return clueDao.detail(id);
    }

    @Override
    public boolean unbund(String id) {
        int result = clueDao.unbund(id);
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public boolean bund(String cid, String[] aids) {
        List<ClueActivityRelation> list = new ArrayList<>();
        for (String aid: aids){
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            String id = UUIDUtil.getUUID();
            clueActivityRelation.setId(id);
            clueActivityRelation.setClueId(cid);
            clueActivityRelation.setActivityId(aid);
            list.add(clueActivityRelation);
        }

        int result = clueActivityRelationDao.bund(list);
        if (result != 0){
            return true;
        }
        return false;
    }

}
