package com.sxf.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author:muziru
 * @date:2019/11/07
 * @version:v1.0
 */
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Data
public class TargetAndCaseInfoDTO {

    /**
     * 指标详情
     */
    private String targetId;
    /**
     * 所属专利
     */
    private String caseId;
    /**
     * 专利进度
     */
    private String lawStatus;
    /**
     * 申请日
     */
    private Date applyDate;
    /**
     * 发明人中文名称
     */
    private String inventorName;
    /**
     * 撰写人
     */
    private String modifierName;

    /**
     * 申请号
     */
    private String applyNo;
    /**
     * 专利名称
     */
    private String inventionName;
    /**
     * 申请人中文名称
     */
    private String applyName;
    /**
     * 技术联系人
     */
    private String tecContact;
    /**
     * 备注
     */
    private String mark;

    private Integer isCheck;
    private String batch;
    private Date updateTime;
    private Date createTime;
    private Integer isNew;
    private String patentType;
    private String accountId;
    private Integer isUse;

}