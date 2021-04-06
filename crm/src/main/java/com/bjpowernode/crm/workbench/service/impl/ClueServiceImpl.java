package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    private ClueDao clueDao;
    @Override
    public boolean save(Clue clue) {
        System.out.println(clue.getCompany());
        int result = clueDao.save(clue);
        if (result == 1){
            return true;
        }
        return false;
    }
}
