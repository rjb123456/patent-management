package com.sxf.utils;


import com.sxf.entity.bussinessEnum.StatusEnum;

/**
 * @description:
 * @author:muziru
 * @date:2019/11/07
 * @version:v1.0
 */
public class FailureResult<T> extends Result<T> {

    /**
     * 返回失败消息，无msg
     * @param date
     */
    public FailureResult( T date) {
        this.code = StatusEnum.FAILURE.key;
        this.msg = "";
        this.date = date;
    }

    /**
     * 返回失败消息，有msg
     * @param msg
     * @param date
     */
    public FailureResult(String msg, T date) {
        if (null == msg || "".equals(msg)){
            msg = "";
        }
        this.code = StatusEnum.FAILURE.key;
        this.msg = msg;
        this.date = date;
    }
}
