package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {

    //线索相关表
    @Resource
    private ClueDao clueDao;
    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;
    @Resource
    private ClueRemarkDao clueRemarkDao;

    //客户相关表
    @Resource
    private CustomerDao customerDao;
    @Resource
    private CustomerRemarkDao customerRemarkDao;

    //联系人相关表
    @Resource
    private ContactsDao contactsDao;
    @Resource
    private ContactsRemarkDao contactsRemarkDao;
    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;

    //交易相关表
    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;

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

    @Override
    public void convert(String id, Tran tran, String createBy) {
        String createTime = DateTimeUtil.getSysTime();
        boolean flag = true;

        Clue clue = clueDao.getById(id);
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);
        if (customer == null){
            //创建客户
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setContactSummary(clue.getContactSummary());
            customer.setCreateBy(createBy);
            customer.setCreateTime(createTime);
            customer.setDescription(clue.getDescription());
            customer.setName(company);
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setOwner(clue.getOwner());
            customer.setPhone(clue.getPhone());
            customer.setWebsite(clue.getWebsite());
            int result = customerDao.save(customer);
            if (result != 1){
                flag = false;
            }
        }
        //通过线索对象提取联系人信息，保存联系人
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setAddress(clue.getAddress());
        contacts.setAppellation(clue.getAppellation());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setCustomerId(customer.getId());
        contacts.setDescription(clue.getDescription());
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        int result2 = contactsDao.save(contacts);
        if (result2 != 1){
            flag = false;
        }

        //线索备注转换到客户备注以及联系人备注

        //取出线索备注信息
        List<ClueRemark> clueRemarkList = clueRemarkDao.getListByClueId(id);
        for (ClueRemark clueRemark : clueRemarkList){
            String clueRemarkId = clueRemark.getId();
            String noteContent = clueRemark.getNoteContent();

            //保存客户备注
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setEditFlag("0");
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(noteContent);
            int result3 = customerRemarkDao.save(customerRemark);
            if (result3 != 1){
                flag = false;
            }

            //保存联系人备注
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setEditFlag("0");
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(noteContent);
            int result4 = contactsRemarkDao.save(contactsRemark);
            if (result4 != 1){
                flag = false;
            }
        }

        //“线索和市场活动”的关系转换到“联系人和市场活动”的关系
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getListByClueId(id);
        for (ClueActivityRelation clueActivityRelation: clueActivityRelationList){
            String activityId = clueActivityRelation.getActivityId();
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());
            int result5 = contactsActivityRelationDao.save(contactsActivityRelation);
            if (result5 != 1){
                flag = false;
            }
        }
        //如果有创建交易需求，创建一条交易
        if (tran.getId() != null){
            tran.setSource(clue.getSource());
            tran.setOwner(clue.getOwner());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setDescription(clue.getDescription());
            tran.setCustomerId(customer.getId());
            tran.setContactSummary(clue.getContactSummary());
            tran.setContactsId(contacts.getId());
            int result6 = tranDao.save(tran);
            if (result6 != 1){
                flag = false;
            }

            //如果创建了交易，则创建一条该交易下的交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setStage(tran.getStage());
            tranHistory.setTranId(tran.getId());
            int result7 = tranHistoryDao.save(tranHistory);
            if (result7 != 1){
                flag = false;
            }
        }

        //删除线索备注
        for (ClueRemark clueRemark : clueRemarkList){
            int result8 = clueRemarkDao.delete(clueRemark.getId());
            if (result8 != 1){
                flag = false;
            }
        }

        //删除线索和市场活动的关系
        int result9 = clueActivityRelationDao.delete(id);
        if (result9 == 0){
            flag = false;
        }

        //删除线索
        int result10 = clueDao.delete(id);
        if (result10 != 1){
            flag = false;
        }
        System.out.println(flag);
    }
}
