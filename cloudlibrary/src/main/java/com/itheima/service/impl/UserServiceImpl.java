package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.mapper.UserMapper;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     */
    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    /**
     * 新增员工：新员工默认状态为正常（0），角色默认是普通用户
     */
    @Override
    public void addUser(User user) {
        if (user.getStatus() == null || "".equals(user.getStatus())) {
            user.setStatus("0");
        }
        if (user.getRole() == null || "".equals(user.getRole())) {
            user.setRole("user");
        }
        userMapper.addUser(user);
    }

    /**
     * 办理离职：将用户状态修改为 1（离职），并记录离职时间
     */
    @Override
    public void delUser(Integer id) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        User user = new User();
        user.setId(id);
        user.setStatus("1");
        user.setDeparturedate(dateFormat.format(new Date()));
        userMapper.editUser(user);
    }

    /**
     * 修改员工信息
     */
    @Override
    public void editUser(User user) {
        userMapper.editUser(user);
    }

    /**
     * 分页查询员工
     */
    @Override
    public PageResult searchUsers(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<User> page = userMapper.searchUsers(user);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查询员工
     */
    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    /**
     * 校验用户名是否重复
     */
    @Override
    public Integer checkName(String name) {
        return userMapper.checkName(name);
    }

    /**
     * 校验邮箱是否重复
     */
    @Override
    public Integer checkEmail(String email) {
        return userMapper.checkEmail(email);
    }
}
