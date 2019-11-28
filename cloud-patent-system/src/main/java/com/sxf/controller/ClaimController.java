package com.sxf.controller;


import com.sxf.entity.CaseAccount;
import com.sxf.entity.CaseInformation;
import com.sxf.entity.CaseStatus;
import com.sxf.exception.GlobalException;
import com.sxf.result.CodeMsg;
import com.sxf.result.Result;
import com.sxf.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClaimController {

    @Autowired
    ClaimService claimService;


    /**
     * 用户认领的已经结束与未结束专利
     * @param request
     * @return
     */
    @GetMapping("/myClaim")
    public Result<Map<String, Object>> myClaim(HttpServletRequest request) {
        CaseAccount caseAccount = (CaseAccount) request.getSession().getAttribute("CA");
        if(caseAccount == null){
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        String accountId = caseAccount.getAccountId();
        Map<String, Object> map = new HashMap<>();
        List<CaseInformation> list1 = claimService.getMyClaim(accountId,1);
        List<CaseInformation> list2 = claimService.getMyClaim(accountId,0);
        List<CaseStatus> ls1 = claimService.getCaseListStatus(list1);
        List<CaseStatus> ls2 = claimService.getCaseListStatus(list2);
        map.put("list1", list1.isEmpty() ? null : list1);
        map.put("ls1", ls1.isEmpty() ? null : ls1);
        map.put("list2", list2.isEmpty() ? null : list2);
        map.put("ls2", ls2.isEmpty() ? null : ls2);
        return Result.success(map);
    }
}
