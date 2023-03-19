package com.zr.controller.sys;

import com.alibaba.fastjson.JSON;
import com.zr.service.sys.CustomerService;
import com.zr.util.AjaxResult;
import com.zr.vo.lease.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/lease/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    /**
     * 登录
     * @param
     * @return
     */
    @RequestMapping("/cuslogin")
    public String login(@RequestBody Customer customer){
        Customer loginCustomer = customerService.login(customer);
        AjaxResult ajaxResult = null;
        if (loginCustomer!=null){
            ajaxResult = new AjaxResult(true,"登录成功");
        }else{
            ajaxResult = new AjaxResult(false, "手机号或密码错误");
        }
        return JSON.toJSONString(ajaxResult);
    }
}
