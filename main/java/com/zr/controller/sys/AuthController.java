package com.zr.controller.sys;


import com.alibaba.fastjson.JSON;
import com.zr.service.sys.AuthService;
import com.zr.util.AjaxResult;
import com.zr.vo.sys.Auth;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

@RestController
@RequestMapping("/sys/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //获取单个权限对象
    @RequestMapping("/authone")
    public String  selectOne(@RequestBody List<String> id){
        Auth auth = new Auth();
        List<Integer>  idx = new ArrayList<>();
        for (String str : id) {
            idx.add(Integer.valueOf(str));
        }
        auth = authService.selectOnebyid(idx.get(0));
        //map映射
        Map<Long,String>  M = new HashMap<>();
        List<Auth> preList = authService.selectpreList();
        for (Auth auth1 : preList){
            M.put(auth1.getId(),auth1.getName());
        }


        if (auth.getPid()!=null) {
            Long in = auth.getPid();
            auth.setPidText(M.get(in));
        }

        return JSON.toJSONString(AjaxResult.success("查询成功",auth));
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    @RequestMapping("/del")
    public String del(@RequestBody List<String> id){
        List<Long>  idx = new ArrayList<>();
        for (String str : id) {
            idx.add(Long.valueOf(str));
        }
        authService.del(idx);
        return JSON.toJSONString(AjaxResult.success("删除成功"));
    }

    
    /**
     * 添加权限
     * @param auth
     * @return
     */
    @RequestMapping("/add")
    public String add(@RequestBody Auth auth){
        Long maxId = authService.selectMaxIdFromAuthTable();
        auth.setId(maxId+1);

        Map<String,Long>  M = new HashMap<>();
        List<Auth> preList = authService.selectpreList();
        for (Auth auth1 : preList){
            M.put(auth1.getName(),auth1.getId());
        }
        if (auth.getPidText()!=null) {
            String str = new String(auth.getPidText());
            auth.setPid(Long.valueOf(M.get(str)));
        }

        authService.add(auth);
        return JSON.toJSONString(AjaxResult.success("新增成功"));
    }

    /**
     * 编辑权限
     * @param auth
     * @return
     */
    @RequestMapping("/edit")
    public String edit(@RequestBody  Auth auth){
        authService.edit(auth);
        return JSON.toJSONString(AjaxResult.success("编辑成功"));
    }

    //获取角色权限树
    @RequestMapping("/selectList")
    public String selectList(){
        List<Auth> authList=authService.selectList();
        AjaxResult ajaxResult=null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0;i<authList.size();i++){
            Map dataMap=new HashMap();
            List<Auth> children =authService.selectListbyid(authList.get(i).getId());
            dataMap.put("children",children);
            dataMap.put("name",authList.get(i).getName());
            dataMap.put("id",authList.get(i).getId());
            list.add(dataMap);
        }
        ajaxResult = new AjaxResult(true,list);

        return JSON.toJSONString(ajaxResult );
    }

    //获取角色权限树
    @RequestMapping("/selectListTwo")
    public String selectListTwo(){
        List<Auth> authList=authService.selectList();
        AjaxResult ajaxResult=null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0;i<authList.size();i++){
            Map dataMap=new HashMap();
            List<Auth> children =authService.selectListbyidTwo(authList.get(i).getId());
            dataMap.put("children",children);
            dataMap.put("label",authList.get(i).getName());
            dataMap.put("id",authList.get(i).getId());
            list.add(dataMap);
        }
        ajaxResult = new AjaxResult(true,list);

        return JSON.toJSONString(ajaxResult );
    }



    /**
     * 获取所有父亲节点，即上级菜单
     * @return
     */
    @RequestMapping("/selectpreList")
    public String selectpreList(){
        List<Auth> preList = authService.selectpreList();
        return JSON.toJSONString(AjaxResult.success("查询成功", preList));
    }

}
