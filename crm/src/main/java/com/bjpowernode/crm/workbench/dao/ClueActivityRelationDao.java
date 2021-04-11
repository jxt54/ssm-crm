package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {
    int bund(List<ClueActivityRelation> list);
    List<ClueActivityRelation> getListByClueId(String id);
    int delete(String id);
}
