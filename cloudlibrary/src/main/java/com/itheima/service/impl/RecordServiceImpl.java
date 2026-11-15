package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.mapper.RecordMapper;
import com.itheima.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    /**
     * 新增借阅记录
     */
    @Override
    public Integer addRecord(Record record) {
        return recordMapper.addRecord(record);
    }

    /**
     * 分页查询借阅记录：管理员可以查询所有人的记录，普通用户只能查询自己的记录
     */
    @Override
    public PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //如果是普通用户，把当前登录的用户名放入查询条件，只能查到自己的借阅记录
        if (user != null && !"admin".equals(user.getRole())) {
            record.setBorrower(user.getName());
        }
        Page<Record> page = recordMapper.searchRecords(record);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 删除借阅记录
     */
    @Override
    public Integer deleteRecord(Integer id) {
        return recordMapper.deleteRecord(id);
    }
}
