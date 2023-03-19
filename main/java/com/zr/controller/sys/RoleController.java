package com.zr.controller.sys;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.sun.deploy.net.MessageHeader;
import com.zr.service.sys.RoleService;
import com.zr.util.AjaxResult;
import com.zr.vo.sys.Role;
import com.zr.vo.sys.RoleAuth;
import com.zr.vo.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    /**
     * 获取所有角色列表
     * @return
     */
    @RequestMapping("/selectList")
    public String selectList(){
        List<Role> roleList = roleService.selectList();
        return JSON.toJSONString(AjaxResult.success("查询成功", roleList));
    }

    /**
     * 查询用户列表
     * @param
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestBody Role role){
        //查询用户列表
        List<Role> roleList = roleService.list(role);
        //封装到分页对象中
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        return JSON.toJSONString(AjaxResult.success("查询成功", pageInfo));
    }


    @RequestMapping("/add")
    public String insertbynameid(@RequestBody Role role){
        roleService.add(role);
        return JSON.toJSONString(AjaxResult.success("插入成功"));
    }


    @RequestMapping("/selectbyid")
    public String selectbyid(@RequestBody Role role){
        roleService.add(role);
        return JSON.toJSONString(AjaxResult.success("插入成功"));
    }

    @RequestMapping("/edit")
    public  String edit(@RequestBody Role role){
        roleService.edit(role);
        return JSON.toJSONString(AjaxResult.success("编辑成功"));
    }

    @RequestMapping("/del/{id}")
    public String del(@PathVariable("id") Long id){
        roleService.del(id);
        return JSON.toJSONString(AjaxResult.success("删除成功"));
    }

}
