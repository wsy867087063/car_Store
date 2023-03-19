package com.zr.service.sys.impl;

import com.zr.mapper.lease.CustomerMapper;
import com.zr.service.sys.CustomerService;
import com.zr.util.Md5Util;
import com.zr.vo.lease.Customer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomerServicelmpl implements CustomerService {


    @Resource
    private  CustomerMapper customerMapper;


    /**
     * 顾客登录
     * @param
     * @return String
     */
    @Override
    public Customer login(Customer customer) {
        //customer.setPassword(Md5Util.EncoderByMd5(customer.getPassword()));
        return customerMapper.login(customer);
    }
}
