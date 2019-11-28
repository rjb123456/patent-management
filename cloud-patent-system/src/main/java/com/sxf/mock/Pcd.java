package com.sxf.mock;

import com.sxf.entity.CaseInformation;
import com.sxf.entity.CaseTarget;
import lombok.Data;

import java.util.Date;

/**
 * 专利维度与指标维度的输出调用中转
 * 中转数据类Pcd
 *
 * @author WangXin
 */
@Data
public class Pcd {

    CaseInformation CaseInfo = new CaseInformation();
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //获取中文发明名称
    public String getInventionName() {
        return CaseInfo.getInventionName();
    }

    public void setInventionName(String name) {
        CaseInfo.setInventionName(name);
    }


    //获取案件文号
    public String getCaseId() {
        return CaseInfo.getCaseId();
    }

    public void setCaseId(String Id) {
        CaseInfo.setCaseId(Id);
    }

    //获取申请号
    public String getApplyNo() {
        return CaseInfo.getApplyNo();
    }

    public void setApplyNo(String No) {
        CaseInfo.setApplyNo(No);
    }

    //获取申请日期
    public Date getApplyDate() {
        return CaseInfo.getApplyDate();
    }

    public void setApplyDate(Date date) {
        CaseInfo.setApplyDate(date);
    }

    //获取发明人中文名
    public String getInventorName() {
        return CaseInfo.getInventorName();
    }

    public void setInventorName(String IName) {
        CaseInfo.setInventorName(IName);
    }

    //获取法律状态
    public String getLawStatus() {
        return CaseInfo.getLawStatus();
    }

    public void setLawStatus(String IStatus) {
        CaseInfo.setLawStatus(IStatus);
    }

    //获取撰写人
    public String getModifierName() {
        return CaseInfo.getModifierName();
    }

    public void setModifierName(String MName) {
        CaseInfo.setModifierName(MName);
    }

    //获取指标
    CaseTarget CaseTar = new CaseTarget();

    public String getTargetId() {
        return CaseTar.getTargetId();
    }

    public void setTargetId(String TId) {
        CaseTar.setTargetId(TId);
    }

}


