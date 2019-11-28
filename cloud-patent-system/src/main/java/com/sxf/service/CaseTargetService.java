package com.sxf.service;

import com.sxf.dao.TargetDao;
import com.sxf.entity.CaseTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaseTargetService {

    @Autowired
    private TargetDao targetDao;

    public void addTarget(CaseTarget caseTarget) {
        targetDao.addTarget(caseTarget);
    }

    public CaseTarget getTarget(String caseId){
        return targetDao.getTargetByCaseId(caseId);
    }

    public void updateTarget(CaseTarget caseTarget) {
        targetDao.updateCaseTarget(caseTarget);
    }

}
