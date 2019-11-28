package com.sxf.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/18 17:38
 * @description：专利信息表
 * @version: 0.1$
 */

@Data
public class CaseInformation {

    private int id;
    private String batch;
    private String caseId;
    private String applyNo;
    private Date applyDate;
    private String tecContact;
    private String applyName;
    private String inventionName;
    private String inventorName;
    private String lawStatus;
    private String patentType;
    private String modifierName;
    private String accountId;
    private int isCheck;
    private int isNew;
    private String mark;
    private Date updateTime;
    private Date createTime;
    private int isUse;

}
