package com.sxf.entity.bussinessEnum;

/**
 * @description:
 * @author:muziru
 * @date:2019/11/07
 * @version:v1.0
 */
public enum StatusEnum {
    /**
     * 成功
     */
    SUCCESS(1,"成功"),
    /**
     * 失败
     */
    FAILURE(0,"失败")
    ;

    public int key;
    public String desc;

    StatusEnum(int key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
