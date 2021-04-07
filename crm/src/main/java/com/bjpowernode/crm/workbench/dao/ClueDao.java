package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Clue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueDao {
    int save(Clue clue);
    Integer getTotalByCondition();
    //传递多个基本类型参数时，必须加@param注解
    List<Clue> getClueListByCondition(@Param("skipCount") Integer skipCount,@Param("pageSize") Integer pageSize);
    Clue detail(String id);
    int unbund(String id);
}
