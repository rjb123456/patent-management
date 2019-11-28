package com.sxf.utils;


import com.sxf.entity.bussinessEnum.StatusEnum;

/**
 * @description:
 * @author:muziru
 * @date:2019/11/07
 * @version:v1.0
 */
public class SuccessResult<T> extends Result<T> {
    public SuccessResult(Integer code, String msg, T date) {
        super(code, msg, date);
    }

    /**
     * 返回成功消息，无msg
     * @param date
     */
    public SuccessResult( T date) {
        this.code = StatusEnum.SUCCESS.key;
        this.msg = "";
        this.date = date;
    }

    /**
     * 返回成功消息，有msg
     * @param msg
     * @param date
     */
    public SuccessResult(String msg, T date) {
        if (null == msg || "".equals(msg)){
            msg = "";
        }
        this.code = StatusEnum.SUCCESS.key;
        this.msg = msg;
        this.date = date;
    }
}
