package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Customer;

public interface CustomerDao {
    Customer getCustomerByName(String name);
    int save(Customer customer);

}
