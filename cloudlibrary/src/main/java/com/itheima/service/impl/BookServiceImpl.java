package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.domain.Book;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.mapper.BookMapper;
import com.itheima.mapper.RecordMapper;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public PageResult selectNewBooks(Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page = bookMapper.selectNewBooks();
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查询图书信息
     *
     * @param id 图书id
     */
    public Book findById(String id) {
        return bookMapper.findById(id);
    }

    /**
     * 借阅图书
     */
    @Override
    public Integer borrowBook(Book book) {
        //根据id查询出需要借阅的完整图书信息
        Book b = this.findById(book.getId() + "");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //设置当天为借阅时间
        book.setBorrowTime(dateFormat.format(new Date()));
        //设置所借阅的图书状态为借阅中
        book.setStatus("1");
        //将图书的价格设置在book对象中
        book.setPrice(b.getPrice());
        //将图书的上架时间设置在book对象中
        book.setUploadTime(b.getUploadTime());
        return bookMapper.editBook(book);
    }

    @Override
    public PageResult search(Book book, Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page = bookMapper.searchBooks(book);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public Integer addBook(Book book) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //设置新增图书的上架时间
        book.setUploadTime(dateFormat.format(new Date()));
        return bookMapper.addBook(book);
    }

    /**
     * 编辑图书信息
     */
    public Integer editBook(Book book) {
        return bookMapper.editBook(book);
    }

    /**
     * 查询用户当前借阅的图书
     *
     * @param book     封装查询条件的对象
     * @param user     当前登录用户
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     */
    @Override
    public PageResult searchBorrowed(Book book, User user, Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page;
        //将当前登录的用户放入查询条件中
        book.setBorrower(user.getName());
        //如果是管理员，查询自己借阅但未归还的图书和所有待确认归还的图书
        if ("admin".equals(user.getRole())) {
            page = bookMapper.selectBorrowed(book);
        } else {
            //如果是普通用户，查询自己借阅但未归还的图书
            page = bookMapper.selectMyBorrowed(book);
        }
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 归还图书
     *
     * @param id   归还的图书的id
     * @param user 归还的人员，也就是当前图书的借阅者
     */
    @Override
    public boolean returnBook(String id, User user) {
        //根据图书id查询出图书的完整信息
        Book book = this.findById(id);
        //再次核验当前登录人员和图书借阅者是不是同一个人
        boolean rb = book.getBorrower().equals(user.getName());
        //如果是同一个人，允许归还
        if (rb) {
            //将图书借阅状态修改为归还中
            book.setStatus("2");
            bookMapper.editBook(book);
        }
        return rb;
    }

    /**
     * 归还确认
     *
     * @param id 归还确认的图书id
     */
    @Override
    public Integer returnConfirm(String id) {
        Book book = this.findById(id);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //图书归还时，向借阅记录表中写入一条借阅记录（先取借阅信息，下面要把图书的借阅信息清空）
        Record record = new Record();
        record.setBookname(book.getName());
        record.setBookisbn(book.getIsbn());
        record.setBorrower(book.getBorrower());
        record.setBorrowTime(book.getBorrowTime());
        record.setRemandTime(dateFormat.format(new Date()));
        recordMapper.addRecord(record);
        //将图书状态恢复为可借阅
        book.setStatus("0");
        //清空借阅人、借阅时间和归还时间
        //注意：BookMapper.xml 中的动态 SQL 判断条件是 xxx != null，会忽略 null 值，
        //所以这里用空字符串，才能真正把数据库中的借阅信息清空
        book.setBorrower("");
        book.setBorrowTime("");
        book.setReturnTime("");
        return bookMapper.editBook(book);
    }

}
