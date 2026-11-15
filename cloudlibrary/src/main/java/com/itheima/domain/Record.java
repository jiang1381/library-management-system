package com.itheima.domain;

import java.io.Serializable;

/**
 * 借阅记录实体类，属性与数据库表 record 的字段一一对应
 */
public class Record implements Serializable {
    private Integer id;          // 借阅记录id record_id
    private String bookname;     // 借阅的图书名称 record_bookname
    private String bookisbn;     // 借阅的图书的ISBN编号 record_bookisbn
    private String borrower;     // 图书借阅人 record_borrower
    private String borrowTime;   // 图书借阅时间 record_borrowtime
    private String remandTime;   // 图书归还时间 record_remandtime

    public Record() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookisbn() {
        return bookisbn;
    }

    public void setBookisbn(String bookisbn) {
        this.bookisbn = bookisbn;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getRemandTime() {
        return remandTime;
    }

    public void setRemandTime(String remandTime) {
        this.remandTime = remandTime;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", bookname='" + bookname + '\'' +
                ", bookisbn='" + bookisbn + '\'' +
                ", borrower='" + borrower + '\'' +
                ", borrowTime='" + borrowTime + '\'' +
                ", remandTime='" + remandTime + '\'' +
                '}';
    }
}
