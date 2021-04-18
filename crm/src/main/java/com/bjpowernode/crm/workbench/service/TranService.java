package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranService {
    boolean save(Tran tran,String customerName);
    Tran detail(String id);
    List<TranHistory> getHistoryListByTranId(String id);
}
