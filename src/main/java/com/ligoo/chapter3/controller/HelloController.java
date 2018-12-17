package com.ligoo.chapter3.controller;

import com.ligoo.framework.annotation.Action;
import com.ligoo.framework.annotation.Controller;
import com.ligoo.framework.annotation.Inject;
import com.ligoo.framework.bean.Data;
import com.ligoo.framework.bean.View;
import com.ligoo.chapter3.model.Customer;
import com.ligoo.chapter3.service.HelloService;

import java.util.List;

/**
 * @Author: Administrator
 * @Date: 2018/12/17 09:39:20
 * @Description:
 */
@Controller
public class HelloController {
    @Inject
    private HelloService helloService;

    @Action(value = "get:/customer")
    public Data getData(){
        List<Customer> customerList =  helloService.getCustomer();
        return new Data(customerList);
    }

    @Action(value = "get:/show_customer")
    public View getView(){
        List<Customer> customerList = helloService.getCustomer();
        View view = new View("customer.jsp");
        view.addModel("customerList", customerList);
        return view;
    }
}
