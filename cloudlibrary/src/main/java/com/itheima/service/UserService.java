package com.itheima.service;

import com.itheima.domain.User;
import com.itheima.entity.PageResult;

/**
 * 用户（员工）业务层接口
 */
public interface UserService {

    //用户登录
    User login(User user);

    //新增员工
    void addUser(User user);

    //办理离职
    void delUser(Integer id);

    //修改员工信息
    void editUser(User user);

    //分页查询员工
    PageResult searchUsers(User user, Integer pageNum, Integer pageSize);

    //根据id查询员工
    User findById(Integer id);

    //校验用户名是否重复
    Integer checkName(String name);

    //校验邮箱是否重复
    Integer checkEmail(String email);
}
