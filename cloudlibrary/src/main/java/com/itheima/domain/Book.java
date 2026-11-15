package com.itheima.domain;

import java.io.Serializable;

/**
 * 图书实体类，属性与数据库表 book 的字段一一对应
 */
public class Book implements Serializable {
    private Integer id;          // 图书编号 book_id
    private String name;         // 图书名称 book_name
    private String isbn;         // 图书标准ISBN编号 book_isbn
    private String press;        // 图书出版社 book_press
    private String author;       // 图书作者 book_author
    private Integer pagination;  // 图书页数 book_pagination
    private Double price;        // 图书价格 book_price
    private String uploadTime;   // 图书上架时间 book_uploadtime
    private String status;       // 图书状态（0：可借阅，1：借阅中，2：归还中，3：已下架）
    private String borrower;     // 图书借阅人 book_borrower
    private String borrowTime;   // 图书借阅时间 book_borrowtime
    private String returnTime;   // 图书预计归还时间 book_returntime

    public Book() {
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPagination() {
        return pagination;
    }

    public void setPagination(Integer pagination) {
        this.pagination = pagination;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isbn='" + isbn + '\'' +
                ", press='" + press + '\'' +
                ", author='" + author + '\'' +
                ", pagination=" + pagination +
                ", price=" + price +
                ", uploadTime='" + uploadTime + '\'' +
                ", status='" + status + '\'' +
                ", borrower='" + borrower + '\'' +
                ", borrowTime='" + borrowTime + '\'' +
                ", returnTime='" + returnTime + '\'' +
                '}';
    }
}
