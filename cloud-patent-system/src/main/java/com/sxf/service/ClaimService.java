package com.sxf.service;

import com.sxf.dao.CaseInfoDao;
import com.sxf.entity.CaseInformation;
import com.sxf.entity.CaseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClaimService {

    @Autowired
    CaseInfoDao caseInfoDao;

    @Autowired
    CaseStatusService caseStatusService;

    /**
     * 查询以及认领的专利
     *
     * @param accountId
     * @param isFinished 1：已经结束流程的专利 2：未结束流程的专利
     * @return
     */
    public List<CaseInformation> getMyClaim(String accountId, int isFinished) {
        List<CaseInformation> list = caseInfoDao.getByInfoId(accountId);
        List<CaseInformation> temp = new ArrayList<>();
        if (isFinished == 1) {
            for (CaseInformation ci : list) {
                if ("3".equals(ci.getLawStatus())) {
                    temp.add(ci);
                }
            }
        } else {
            for (CaseInformation ci : list) {
                if (!"3".equals(ci.getLawStatus())) {
                    temp.add(ci);
                }
            }
        }
        return temp;
    }

    public List<CaseStatus> getCaseListStatus(List<CaseInformation> list) {
        List<CaseStatus> temp = new ArrayList<>();
        CaseStatus cs;
        for (CaseInformation ci : list) {
            cs = caseStatusService.getById(ci.getCaseId());
            temp.add(cs);
        }
        return temp;
    }
}
