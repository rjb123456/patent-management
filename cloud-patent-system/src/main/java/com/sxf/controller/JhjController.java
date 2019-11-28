package com.sxf.controller;

import com.sxf.entity.CaseInformation;
import com.sxf.service.CaseInfoService;
import com.sxf.service.CaseStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName:JhjController
 * Package:com.sxf.controller
 * Description
 *
 * @data:2019/11/21 20:13
 * @author:jiahongjie
 */
@Slf4j
@RestController
public class JhjController {

    @Autowired
    CaseStatusService statusService;
    @Autowired
    CaseInfoService caseInfoService;

    /**
     * is_check:1,待审核；2，通过；3，审核未通过。
     */
    @PostMapping("/check")
    public String check(@RequestBody CaseInformation ci) {
        String case_id = ci.getCaseId();
        int is_check = ci.getIsCheck();
        CaseInformation caseInformation = caseInfoService.getModifierNameById(case_id);
        int go = 2;
        int notGo = 3;
        String accountId = caseInformation.getAccountId();
        String response = "";
        if (accountId == null || "".equals(accountId)) {
            if (is_check == go) {
                int flag = caseInfoService.updateIsCheck(is_check, case_id);
                if (flag == 1) {
                    response = "操作成功";
                } else {
                    response = "操作失败";
                }
            } else if (is_check == notGo) {
                caseInfoService.delete(case_id);
                response = "删除成功";
            }

        } else {
            if (is_check == go) {
                caseInfoService.updateIsCheck(is_check, case_id);
            } else if (is_check == notGo) {
                is_check = 1;
                caseInfoService.updateIsCheck(is_check, case_id);
            }

        }
        return response;
    }


}


