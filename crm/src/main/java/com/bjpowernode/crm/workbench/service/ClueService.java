package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;


public interface ClueService {
    boolean save(Clue clue);
    PaginationVo<Clue> pageList(Integer pageNo,Integer pageSize);
    Clue detail(String id);
    boolean unbund(String id);
    boolean bund(String cid,String[] aid);
    void convert(String id, Tran tran,String createBy);
}
