package com.itheima.domain;

import java.io.Serializable;

/**
 * 用户（员工）实体类，属性与数据库表 user 的字段一一对应
 */
public class User implements Serializable {
    private Integer id;              // 用户id user_id
    private String name;             // 用户名称 user_name
    private String password;         // 用户密码 user_password
    private String email;            // 用户邮箱（登录账号）user_email
    private String role;             // 用户角色 user_role（admin：管理员，其余为普通用户）
    private String status;           // 用户状态 user_status（0：正常，1：离职）
    private String hiredate;         // 入职时间 user_hiredate
    private String departuredate;    // 离职时间 user_departuredate

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    public String getDeparturedate() {
        return departuredate;
    }

    public void setDeparturedate(String departuredate) {
        this.departuredate = departuredate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", hiredate='" + hiredate + '\'' +
                ", departuredate='" + departuredate + '\'' +
                '}';
    }
}
