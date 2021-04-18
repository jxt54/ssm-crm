package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.dao.TranHistoryDao;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class TranServiceImpl implements TranService {
    @Resource
    private CustomerDao customerDao;
    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;
    @Override
    public boolean save(Tran tran,String customerName) {
        /**
         * 交易添加业务
         *      在做添加之前,参数t里面就少了一项信息,就是客户的主键,customerId
         *
         *      先处理客户相关的需求
         *      (1) 判断customerName,根据客户名称在客户表进行精确查询
         *          如果有这个客户,则取出这个客户的id,封装到t对象中
         *          如果没有这个客户,则在客户表新建一条客户信息,然后将新建的客户的id取出,封装到t对象中
         *
         *      (2) 经过以上操作后,t对象中的信息就全了,需要执行添加交易的操作
         *
         *      (3) 添加交易完毕后,需要创建一条交易历史
         */
        Customer customer= customerDao.getCustomerByName(customerName);
        if (customer == null){
            customer = new Customer();
            String customerId = UUIDUtil.getUUID();
            customer.setId(customerId);
            customer.setName(customerName);
            customer.setCreateTime(tran.getCreateTime());
            customer.setCreateBy(tran.getCreateBy());
            customer.setContactSummary(tran.getContactSummary());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setOwner(tran.getOwner());
            int result = customerDao.save(customer);
        }
        tran.setCustomerId(customer.getId());
        int result = tranDao.save(tran);

        //添加交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setCreateBy(tran.getCreateBy());
        int result2 = tranHistoryDao.save(tranHistory);

        if (result == 1 && result2 == 1){
            return true;
        }
        return false;
    }

    @Override
    public Tran detail(String id) {
        Tran tran = tranDao.detail(id);
        return tran;
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String id) {
        return tranHistoryDao.getHistoryListByTranId(id);
    }
}
