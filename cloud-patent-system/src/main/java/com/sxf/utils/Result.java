package com.sxf.utils;


/**
 * @description:
 * @author:muziru
 * @date:2019/11/07
 * @version:v1.0
 */
public class Result<T> {
    /**
     * 返回状态码
     */
    public Integer code;
    /**
     * 返回消息，返回错误时使用，描述失败原因，可用于弹窗
     */
    public String msg;
    /**
     * 返回数据，泛型
     */
    public T date;

    public Result(Integer code, String msg, T date) {
        this.code = code;
        this.msg = msg;
        this.date = date;
    }

    public Result(){

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }
}
