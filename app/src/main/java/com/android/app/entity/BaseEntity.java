package com.android.app.entity;

/**
 * Created by Justin on 2016/6/1.
 */
public class BaseEntity<T> {

    private String message;

    private String status;

    private T data;


    public BaseEntity() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
