package com.zr.mapper.sys;

import com.zr.vo.sys.Auth;

import java.util.List;

public interface AuthMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Auth record);

    int insertSelective(Auth record);

    Auth selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Auth> listByroleid(Long id);

    List<Auth> selectList();


    List<Auth> selectListbyid(Long id);

    Auth selectAuthOneByPrimaryKey(long id);

    List<Auth> selectpreList();

    Long selectMaxIdFromAuthTable();

    List<Auth> selectListbyidTwo(Long id);
}