package com.zr.service.sys.impl;

import com.zr.mapper.sys.AuthMapper;
import com.zr.mapper.sys.RoleAuthMapper;
import com.zr.service.sys.AuthService;
import com.zr.util.TokenUtil;
import com.zr.vo.sys.Auth;
import com.zr.vo.sys.RoleAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {


    @Resource
    private  RoleAuthMapper roleAuthMapper;

    @Resource
    private AuthMapper authMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public List<Auth> selectpreList() {
        return authMapper.selectpreList();
    }

    @Override
    public List<Auth> selectList() {
        return authMapper.selectList();
    }

    @Override
    public List<Auth> selectListbyid(Long id) {
        return authMapper.selectListbyid(id);
    }

    @Override
    public Auth selectOnebyid(long id) {
        return authMapper.selectAuthOneByPrimaryKey(id);
    }

    @Override
    public void add(Auth auth) {
        auth.setCreateId(tokenUtil.getUserId());
        auth.setCreateTime(new Date());
        authMapper.insertSelective(auth);
        RoleAuth roleAuth = new RoleAuth();
        roleAuth.setAuthId(auth.getId());
        roleAuth.setRoleId(Long.valueOf(1));
        roleAuthMapper.insert(roleAuth);
    }

    @Override
    public void edit(Auth auth) {
        auth.setModifyId((tokenUtil.getUserId()));
        auth.setModifyTime(new Date());
        authMapper.updateByPrimaryKeySelective(auth);
    }

    @Override
    public void del(List<Long> id) {

        for (int idx = id.size() - 1; idx>=0; --idx) {
            authMapper.deleteByPrimaryKey(id.get(idx));
            roleAuthMapper.deleteByAuthid(id.get(idx));
        }
    }

    @Override
    public Long selectMaxIdFromAuthTable() {
        return authMapper.selectMaxIdFromAuthTable();
    }

    @Override
    public List<Auth> selectListbyidTwo(Long id) {
        return authMapper.selectListbyidTwo(id);
    }
}
