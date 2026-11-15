package com.itheima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果封装类
 * total：总记录数
 * rows：当前页的数据集合
 */
public class PageResult implements Serializable {
    private long total;
    private List rows;

    public PageResult(long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
