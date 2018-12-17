package com.ligoo.chapter2.service;

import com.ligoo.framework.helper.DataSourceHelper;
import com.ligoo.chapter3.model.Customer;
import com.ligoo.chapter3.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Administrator
 * @Date: 2018/12/12 15:01:17
 * @Description:
 */
public class CustomerServiceTest {
    private final CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new CustomerService();
    }

    @Before
    public void init(){
        DataSourceHelper.executeSqlFile("ini_customer.sql");
    }
    @Test
    public void getCustomerList() {
        List<Customer> list = customerService.getCustomerList();
        Assert.assertEquals(2,list.size());
    }

    @Test
    public void getCustomer() {
        long id = 2;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomer() {
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("name", "customer100");
        fieldMap.put("contact", "John");
        fieldMap.put("telephone", "13712345678");
        boolean result = customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomer() {
        long id = 1;
        Map<String,Object> fieldMap = new HashMap<>();
        fieldMap.put("contact","Eric");
        boolean result = customerService.updateCustomer(id, fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void deleteCustomer() {
        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }

    @Test
    public void testMapHandler(){
        long id = 1;
        String sql = "select * from customer";
        List<Map<String, Object>> result =  DataSourceHelper.executeQuery(sql);
        Assert.assertEquals(2, result.size());
    }
}