package com.ligoo.chapter4.controller;

import com.ligoo.chapter4.service.CustomerService;
import com.ligoo.framework.annotation.Action;
import com.ligoo.framework.annotation.Controller;
import com.ligoo.framework.annotation.Inject;
import com.ligoo.framework.bean.Data;
import com.ligoo.framework.bean.Param;
import com.ligoo.framework.bean.View;
import com.ligoo.chapter4.model.Customer;
import com.ligoo.framework.helper.ServletHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: Administrator
 * @Date: 2018/12/17 09:39:20
 * @Description:
 */
@Controller
public class CustomerController {
    @Inject
    private CustomerService customerService;

    @Action(value = "get:/customer")
    public Data getData(){
        List<Customer> customerList =  customerService.getCustomerList();
        return new Data(customerList);
    }

    @Action(value = "get:/show_customer")
    public View getView(){
        HttpServletRequest request = ServletHelper.getRequest();
        String servletPath = request.getServletPath();
        String contextPath = request.getContextPath();
        HttpSession session = request.getSession();
        List<Customer> customerList = customerService.getCustomerList();
        View view = new View("customer.jsp");
        view.addModel("customerList", customerList);
        return view;
    }

    @Action(value = "get:/customer_create")
    public View createCustomer(){
        View view = new View("customer_create.jsp");
        return view;
    }

    @Action(value = "post:/customer_create")
    public Data SaveCustomer(Param param){
        return null;
    }
}
