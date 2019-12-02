package com.sxf.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/18 17:37
 * @description：专利指标对应表
 * @version: 0.1$
 */
@Data
public class CaseTarget {
    private int id;
    private String caseId;
    private String targetId;
    private Date updateTime;
    private Date createTime;
    private String isUse;
    private String modifierName;

}
