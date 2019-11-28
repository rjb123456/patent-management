package com.sxf.controller;

import com.sxf.entity.CaseAccount;
import com.sxf.entity.CaseInformation;
import com.sxf.exception.GlobalException;
import com.sxf.result.CodeMsg;
import com.sxf.result.Result;
import com.sxf.service.CaseInfoService;
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

    /**
     * 查询待审核的专利数据
     * @param request
     * @return 需要审核的专利数据
     */
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
}
