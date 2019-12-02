package com.sxf.controller;

import com.sxf.entity.CaseAccount;
import com.sxf.entity.CaseInformation;
import com.sxf.exception.GlobalException;
import com.sxf.log.SystemLog;
import com.sxf.result.CodeMsg;
import com.sxf.result.Result;
import com.sxf.service.CaseInfoService;
import com.sxf.service.CaseStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2019-11-25 13:27
 *
 */
@RestController
public class CheckController {

    @Autowired
    CaseInfoService caseInfoService;

    @Autowired
    CaseStatusService statusService;

    /**
     * 查询待审核的专利数据
     * @param request
     * @return 需要审核的专利数据
     */
    @SystemLog(methods = "查询带审核的专利数据", module = "查询带审核的专利数据")
    @GetMapping("/adminCheck")
    public Result<Map<String, Object>> needAdminCheck1(HttpServletRequest request) {
        CaseAccount caseAccount = (CaseAccount) request.getSession().getAttribute("CA");
        //管理员类型为2
        if(caseAccount.getType() != 2){
            throw new GlobalException(CodeMsg.IS_NOT_ADMIN);
        }
        Map<String, Object> map = new HashMap<>();
        List<CaseInformation> firstCheck = caseInfoService.getAdminCheckList(1);
        List<CaseInformation> secondCheck = caseInfoService.getAdminCheckList(0);
        map.put("firstCheck", firstCheck.isEmpty() ? null : firstCheck);
        map.put("secondCheck", secondCheck.isEmpty() ? null : secondCheck);
        return Result.success(map);
    }


    /**
     * is_check:1,待审核；2，通过；3，审核未通过。
     */
    @SystemLog(methods = "专利列表", module = "查询待审核的专利数据")
    @PostMapping("/check")
    public String check(@RequestBody CaseInformation ci) {
        String case_id = ci.getCaseId();
        int is_check = ci.getIsCheck();
        CaseInformation caseInformation = caseInfoService.getModifierNameById(case_id);
        int go = 2;
        int notGo = 3;
        String accountId = caseInformation.getAccountId();
        String response = "";
        if ("".equals(accountId)) {
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
                caseInfoService.updateIsCheck(is_check, case_id);
                //第二次审核不通过 将专利状态改为初始状态 重新上传交底书
                statusService.updateCaseStatuss(case_id,0);
            }

        }
        return response;
    }


}
