package com.sxf.service;

import com.sxf.dao.CaseInfoTarget;
import com.sxf.dao.TargetDao;
import com.sxf.entity.CaseTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CaseTargetService {

    @Autowired
    private TargetDao targetDao;

    public void addTarget(CaseInfoTarget caseInfoTarget) {
        CaseTarget caseTarget = new CaseTarget();
        caseTarget.setTargetId(caseInfoTarget.getTargetId());
        //指标
        caseTarget.setCaseId(caseInfoTarget.getCaseId());
        //更新时间和创建时间
        caseTarget.setUpdateTime(new Date());
        //更新时间和创建时间
        caseTarget.setCreateTime(new Date());
        caseTarget.setIsUse("1");
        targetDao.addTarget(caseTarget);
    }

    public CaseTarget getTarget(String caseId){
        return targetDao.getTargetByCaseId(caseId);
    }

    public void updateTarget(CaseTarget caseTarget) {
        targetDao.updateCaseTarget(caseTarget);
    }

}
