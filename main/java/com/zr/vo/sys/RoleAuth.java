package com.zr.vo.sys;

import com.zr.vo.BaseVo;

import java.io.Serializable;

public class RoleAuth extends BaseVo implements Serializable {
    private Long roleId;

    private Long authId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }
}