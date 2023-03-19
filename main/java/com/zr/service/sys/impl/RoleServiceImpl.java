package com.zr.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.zr.mapper.sys.RoleAuthMapper;
import com.zr.mapper.sys.RoleMapper;
import com.zr.service.sys.RoleService;
import com.zr.util.TokenUtil;
import com.zr.vo.sys.Role;
import com.zr.vo.sys.RoleAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Resource
    private RoleAuthMapper roleAuthMapper;

    @Override
    public List<Role> selectList() {
        return roleMapper.selectList();
    }

    @Override
    public List<Role> list(Role role) {
        PageHelper.startPage(role.getPageNum(), role.getPageSize());
        return roleMapper.list(role);
    }
    @Transactional
    @Override
    public void add(Role role) {

        role.setCreateId(tokenUtil.getUserId());
        String p=role.getName();
        role.setCreateTime(new Date());
        roleMapper.insertSelective(role);
        Long n=roleMapper.selectidbyname(p);
        for(Long m:role.getRoleauthlist()){
            RoleAuth roleAuth=new RoleAuth();
            roleAuth.setRoleId(n);
            roleAuth.setAuthId(m);
            roleAuthMapper.insert(roleAuth);
        }

    }

    @Transactional
    @Override
    public void edit(Role role) {
        //是否有权限修改
        //删除该角色所有权限
        roleAuthMapper.delAllByroleid(role.getId());
        if(role.getRoleauthlist()!=null)
        {
            //新增该角色权限
            for(Long m:role.getRoleauthlist()){
                RoleAuth roleAuth=new RoleAuth();
                roleAuth.setRoleId(role.getId());
                roleAuth.setAuthId(m);
                roleAuthMapper.insert(roleAuth);
            }
        }
        role.setModifyId((tokenUtil.getUserId()));
        role.setModifyTime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);

    }

    @Override
    public void del(Long id) {
//删除改角色拥有的权限
        roleAuthMapper.delAllByroleid(id);
        roleMapper.deleteByPrimaryKey(id);

    }
}
