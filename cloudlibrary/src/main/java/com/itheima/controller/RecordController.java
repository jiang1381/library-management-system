package com.itheima.controller;

import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 借阅记录相关的控制器
 */
@Controller
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    /**
     * 分页查询借阅记录
     *
     * @param record   查询的条件（借阅人、图书名称）
     * @param request  用于获取当前登录用户和请求路径
     * @param pageNum  数据列表的当前页码
     * @param pageSize 数据列表1页展示多少条数据
     */
    @RequestMapping("/searchRecords")
    public ModelAndView searchRecords(Record record, HttpServletRequest request, Integer pageNum, Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        //获取当前登录的用户
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        PageResult pageResult = recordService.searchRecords(record, user, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("record");
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("search", record);
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }

    /**
     * 删除借阅记录（仅管理员）
     *
     * @param id 记录的id
     */
    @ResponseBody
    @RequestMapping("/deleteRecord")
    public Result deleteRecord(Integer id, HttpServletRequest request) {
        try {
            User user = (User) request.getSession().getAttribute("USER_SESSION");
            if (user == null || !"admin".equals(user.getRole())) {
                return new Result(false, "无权限执行删除操作");
            }
            Integer count = recordService.deleteRecord(id);
            if (count != 1) {
                return new Result(false, "删除失败!");
            }
            return new Result(true, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败!");
        }
    }
}
