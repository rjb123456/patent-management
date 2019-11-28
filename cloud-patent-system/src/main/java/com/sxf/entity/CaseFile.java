package com.sxf.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/20 11:51
 * @description：专利文件
 * @version: 0.0.1$
 */
@Data
public class CaseFile {
    private String caseId;
    private String filePath;
    private int fileType;
    private int isUsed;
    private Date createTime;
    private Date updateTime;

}
