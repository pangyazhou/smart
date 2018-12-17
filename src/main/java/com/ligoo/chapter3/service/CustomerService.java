package com.ligoo.chapter3.service;

import com.ligoo.framework.helper.DataSourceHelper;
import com.ligoo.chapter3.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @Author: Administrator
 * @Date: 2018/12/12 14:54:32
 * @Description:提供客户数据服务
 */
public class CustomerService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    /**
     * description: 根据客户名称获取客户列表
     * author: Administrator
     * date: 2018/12/12 14:56
     *
     * @param: keyword  客户名称
     * @return: 客户列表
     */
    public List<Customer> getCustomerList(){
           String sql = "select * from customer";
           return DataSourceHelper.queryEntityList(Customer.class, sql);
    }

    /**
     * description: 根据用户id获取用户信息
     * author: Administrator
     * date: 2018/12/12 14:57
     *
     * @param: id 用户id
     * @return:  用户信息
     */
    public Customer getCustomer(long id){
        // TODO
        String sql = "select * from customer where id = ?";
        return DataSourceHelper.queryEntity(Customer.class, sql, id);
    }

    /**
     * description: 创建新用户
     * author: Administrator
     * date: 2018/12/12 14:58
     *
     * @param: fieldMap 构建新用户信息
     * @return:
     */
    public boolean createCustomer(Map<String, Object> fieldMap){
        return DataSourceHelper.insertEntity(Customer.class, fieldMap);
    }

    /**
     * description: 根据用户id更新用户信息
     * author: Administrator
     * date: 2018/12/12 14:59
     *
     * @param: id 用户id
     * @param fieldMap 更新的用户信息
     * @return:
     */
    public boolean updateCustomer(long id, Map<String,Object> fieldMap){
       return DataSourceHelper.updateEntity(Customer.class, id, fieldMap);
    }

    /**
     * description: 根据用户id删除用户
     * author: Administrator
     * date: 2018/12/12 15:00
     *
     * @param:
     * @return:
     */
    public boolean deleteCustomer(long id){
       return DataSourceHelper.deleteEntity(Customer.class, id);
    }

}
