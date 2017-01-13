package com.wl.demo.mvpsample.net.resp.model.base;

/**
 * Created by wangliang on 16-9-28.
 */

public class Response<T> {
    private static final long serialVersionUID = -5314664883649119793L;

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 仅请求返回时有数据 向上层返回时候该方法返回空
     * @return
     */
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
