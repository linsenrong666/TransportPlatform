package com.linsr.linlibrary.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description
 @author Linsr
 */
@EqualsAndHashCode(callSuper = true)
public class ResponsePojo<T> extends BasePojo {

    String msg;
    int code;
    T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
