package com.sxf.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/18 17:36
 * @description：账户表
 * @version: 0.1$
 */

@Data
public class CaseAccount {
    private int id;
    private String modifierName;
    private String accountId;
    private String password;
    private int type;
    private Date updateTime;
    private Date createTime;

}
