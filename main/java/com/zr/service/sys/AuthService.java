package com.zr.service.sys;

import com.zr.vo.sys.Auth;

import java.util.List;

public interface AuthService {
    List<Auth> selectpreList() ;

    List<Auth> selectList();

    List<Auth> selectListbyid(Long id);

    Auth selectOnebyid(long id);

    void add(Auth auth);

    void edit(Auth auth);

    void del(List<Long> id);

    Long selectMaxIdFromAuthTable();

    List<Auth> selectListbyidTwo(Long id);
}
