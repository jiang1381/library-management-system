package com.itheima.service;

import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;

/**
 * 借阅记录业务层接口
 */
public interface RecordService {

    //新增借阅记录
    Integer addRecord(Record record);

    //分页查询借阅记录
    PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize);

    //删除借阅记录
    Integer deleteRecord(Integer id);
}
