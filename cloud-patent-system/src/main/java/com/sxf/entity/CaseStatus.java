package com.sxf.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/18 17:38
 * @description：专利状态表
 * @version: 0.1$
 */
@Data
public class CaseStatus {
    private int id;
    private int status;
    private String caseId;
    private String applyNo;
    private Date applyDate;
    private String inventionName;
    private String modifierName;
    private Date createTime;
    private Date updateTime;
    private int isUse;
    private String inventorName;
}
