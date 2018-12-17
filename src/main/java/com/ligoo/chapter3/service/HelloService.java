package com.ligoo.chapter3.service;

import com.ligoo.framework.annotation.Service;
import com.ligoo.framework.helper.DataSourceHelper;
import com.ligoo.chapter3.model.Customer;

import java.util.List;

/**
 * @Author: Administrator
 * @Date: 2018/12/17 09:40:19
 * @Description:
 */
@Service
public class HelloService {
    public List<Customer> getCustomer(){
        String sql = "select * from customer";
        List<Customer> customerList = DataSourceHelper.queryEntityList(Customer.class, sql);
        return customerList;
    }
}
