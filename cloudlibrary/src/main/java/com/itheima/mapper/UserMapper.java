package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.domain.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * 用户（员工）数据访问层
 */
public interface UserMapper {

    /**
     * 用户登录：登录账号既可以是邮箱，也可以是用户名（管理员账号 root 可直接登录）。
     * 同时排除已办理离职（user_status = 1）的用户。
     */
    @Select("SELECT * FROM user WHERE (user_email=#{email} OR user_name=#{email}) " +
            "AND user_password=#{password} AND user_status!='1'")
    @Results(id = "userMap", value = {
            @Result(id = true, column = "user_id", property = "id"),
            @Result(column = "user_name", property = "name"),
            @Result(column = "user_password", property = "password"),
            @Result(column = "user_email", property = "email"),
            @Result(column = "user_role", property = "role"),
            @Result(column = "user_status", property = "status"),
            @Result(column = "user_departuredate", property = "departuredate")
    })
    User login(User user);

    //新增员工
    void addUser(User user);

    //修改员工信息
    void editUser(User user);

    @Select({"<script>" +
            "SELECT * FROM user where 1=1 " +
            "<if test=\"id != null\"> AND  user_id  like  CONCAT('%',#{id},'%')</if>" +
            "<if test=\"name != null\"> AND user_name like  CONCAT('%', #{name},'%') </if>" +
            "order by user_status" +
            "</script>"
    })
    @ResultMap("userMap")
        //分页查询员工
    Page<User> searchUsers(User user);

    @Select(" select * from user where user_id=#{id}")
    @ResultMap("userMap")
        //根据id查询员工
    User findById(Integer id);

    @Select("select count(user_name) from user where user_name=#{name}")
        //校验用户名是否重复
    Integer checkName(String name);

    @Select("select count(user_email) from user where user_email=#{email}")
        //校验邮箱是否重复
    Integer checkEmail(String email);
}
