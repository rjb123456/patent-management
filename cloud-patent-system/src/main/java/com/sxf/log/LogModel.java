package com.sxf.log;

import lombok.Data;

import java.util.Arrays;
import java.util.Date;

@Data
public class LogModel {
    /**日志id */
    private Integer id;

    /** * 当前操作人id */
    private String loginAccount;

    /**当前操作人ip */
    private String loginIp;

    /**操作请求的链接     */
    private String actionUrl;

    /**执行的模块 */
    private String module;


    /**执行的方法 */
    private String method;

    /**执行的具体方法 */
    private String methodJuti;


    /**执行的具体方法的参数 */
    private Object[] parameter;

    /**执行操作时间 */
    private Long actionTime;

    /** 描述     */
    private String description;

    /** 执行的时间 */
    private Date gmtCreate;


    /** 该操作状态，1表示成功，-1表示失败！ */
    private Short state;
    //set()/get()

   // private  Object object;

    @Override
    public String toString() {
        return "LogModel{" +
                "id=" + id +
                ", loginAccount='" + loginAccount + '\'' +"\n"+
                ", loginIp='" + loginIp + '\'' +"\n"+
                ", actionUrl='" + actionUrl + '\'' +"\n"+
                ", module='" + module + '\'' +"\n"+
                ", method='" + method + '\'' +"\n"+
                ", methodJuti='" + methodJuti + '\'' +"\n"+
                ", parameter=" + Arrays.toString(parameter) +"\n"+
                ", actionTime=" + actionTime +"\n"+
                ", description='" + description + '\'' +"\n"+
                ", gmtCreate=" + gmtCreate +"\n"+
                ", state=" + state +"}"+"\n";

    }
}
