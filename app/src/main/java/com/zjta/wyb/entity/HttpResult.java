package com.zjta.wyb.entity;

import java.io.Serializable;

/**
 * 网络请求通用基本对象
 */
public class HttpResult<T> implements Serializable {
    private int code;
    private String message;
    private T data;


    public HttpResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
