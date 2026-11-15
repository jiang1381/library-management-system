package com.itheima.entity;

import java.io.Serializable;

/**
 * 后端返回给前端的数据封装类，会被 Jackson 转成 JSON
 * success：操作是否成功
 * message：提示信息
 * data：返回的数据
 */
public class Result<T> implements Serializable {
    private boolean success;
    private String message;
    private T data;

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
