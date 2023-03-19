package com.zr.service.sys;

import com.zr.vo.sys.Role;

import java.util.List;

public interface RoleService {
    List<Role> selectList();

    List<Role> list(Role role);

    void add(Role role);

    void edit(Role role);

    void del(Long id);
}
