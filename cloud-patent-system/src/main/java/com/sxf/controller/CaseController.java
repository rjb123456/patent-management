package com.sxf.controller;


import com.sxf.dao.CaseInfoTarget;
import com.sxf.entity.CaseInformation;
import com.sxf.entity.CaseStatus;
import com.sxf.entity.TargetAndCaseInfoDTO;
import com.sxf.exception.GlobalException;
import com.sxf.log.SystemLog;
import com.sxf.result.CodeMsg;
import com.sxf.service.CaseInfoService;
import com.sxf.service.CaseStatusService;
import com.sxf.service.CaseTargetService;
import com.sxf.utils.FailureResult;
import com.sxf.utils.Result;
import com.sxf.utils.SuccessResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
public class CaseController {
    @Autowired
    CaseInfoService caseInfoService;
    @Autowired
    CaseTargetService caseTargetService;

    @Autowired
    CaseStatusService caseStatusService;

    /**
     * 专利更改
     * @param caseInformation 前端传回的专利信息
     * @return 更改成功  更改出错
     */
    @SystemLog(methods = "专利更改", module = "专利更改")
    @PostMapping("/change")
    public com.sxf.result.Result<String> changeCase(@RequestBody CaseInformation caseInformation){
        CaseInformation ci = caseInfoService.getByCaseId(caseInformation.getCaseId());

        log.info(caseInformation.toString());
        caseInformation.setId(ci.getId());
        caseInformation.setIsCheck(ci.getIsCheck());
        caseInformation.setIsNew(ci.getIsUse());
        caseInformation.setIsUse(ci.getIsUse());
        caseInformation.setPatentType(ci.getPatentType());
        caseInformation.setAccountId(ci.getAccountId());
        caseInformation.setModifierName(ci.getModifierName());
        caseInformation.setApplyName(ci.getApplyName());
        caseInformation.setBatch(ci.getBatch());

        caseInformation.setCreateTime(ci.getCreateTime());
        caseInformation.setUpdateTime(new Date());
        try{
            caseInfoService.changeCaseInfo(caseInformation);
        }catch (Exception e){
            log.info("更改出错");
            throw new GlobalException(CodeMsg.CHANGE_CI_FAILED);
        }
        return com.sxf.result.Result.success("更改成功");
    }

    /**
     * @param
     * @param
     * @return String 0 是失败   1是成功
     * 添加专利，生成指标
     */
    @SystemLog(methods = "专利列表", module = "专利申添请加")
    @RequestMapping("/addCase")
    public Map<String, String> addCase(@RequestBody CaseInfoTarget caseInfoTarget) {
        log.info("caseInfoTarget={}",caseInfoTarget);
        CaseInformation caseInformation = caseInfoService.getCaseInfo(caseInfoTarget);
        log.info("caseInformation={}",caseInformation);
        Map<String, String> map = new HashMap<String, String>();
        String flag = "0";
        map.put("code", flag);
        if (caseInfoTarget.getCaseId() == null || "".equals(caseInfoTarget.getCaseId().trim())) {
            return map;
        }
        if (caseInfoTarget.getApplyNo() == null || caseInfoTarget.getApplyNo().trim().equals("")) {
            return map;
        }
        if (caseInfoTarget.getTargetId() == null || "".equals(caseInfoTarget.getTargetId().trim())) {
            return map;
        }
        if (caseInfoService.getCaseInfoByCaseId(caseInformation.getCaseId(), caseInfoTarget.getApplyNo()) != null) {
            flag = "caseId重复或者applyNo重复";
            map.put("code", flag);
            return map;
        } else {
            flag = "1";
            map.put("code", flag);
        }
        /**
         *
         * 添加专利状态初始化
         */
        caseStatusService.addCaseStatus(caseInformation);
        /**
         * 初始化指标对象
         */

        caseTargetService.addTarget(caseInfoTarget);
        /**
         *
         *添加专利
         */
        caseInfoService.addCase(caseInformation);

        return map;
    }


    /**
     * 查询所有专利的信息
     *
     * @return
     */
    @SystemLog(methods = "专利列表", module = "查询所有专利信息")
    @RequestMapping(value = "/getCaseInfo", method = RequestMethod.POST)
    public Result getCaseInfo() {
        Map<String, Object> map = new HashMap<>();
        List<CaseInformation> caseList = new ArrayList<>();
        try {
            caseList = caseInfoService.getCaseInfo();
        } catch (Exception e) {
            return new FailureResult("查询失败", null);
        }
        map.put("list", caseList.isEmpty() ? null : caseList);
        return new SuccessResult("查询成功", map);

    }

    /**
     * 查询,根据各个属性查询
     *
     * @param
     * @return
     */
    @SystemLog(methods = "专利列表", module = "根据各个属性查询专利")
    @PostMapping("/getCaseList")
    public Result getCaseList(@RequestBody CaseInformation caseInformation) {
        Map<String, Object> map = new HashMap<>();
        List<CaseInformation> caseList = new ArrayList<>();
        try {
            caseList = caseInfoService.selectCaseByAttribute(caseInformation);
        } catch (Exception e) {
            return new FailureResult("查询专利失败", null);
        }
        // 被认领的专利历表
        List<CaseInformation> claimList = new ArrayList<>();
        // 未被认领的专利列表
        List<CaseInformation> unClaimList = new ArrayList<>();
        for (CaseInformation tar : caseList) {
            if(tar.getIsCheck() == 2){
                if (null == tar.getModifierName() ||"".equals(tar.getModifierName())) {
                    unClaimList.add(tar);
                } else {
                    claimList.add(tar);
                }
            }
        }
        map.put("claimList", claimList.isEmpty() ? null : claimList);
        map.put("unClaimList", unClaimList.isEmpty() ? null : unClaimList);
        return new SuccessResult("查询成功", map);

    }

    /**
     * 查看详情
     *
     * @param
     * @return
     */
    @SystemLog(methods = "单个专利", module = "专利详情")
    @RequestMapping(value = "/getCaseDetail", method = RequestMethod.POST)
    public Result getCaseDetail(@RequestBody TargetAndCaseInfoDTO targetAndCaseInfoDTO) {

        String applyNo = targetAndCaseInfoDTO.getApplyNo();

        log.info(targetAndCaseInfoDTO.toString());

        Map<String, Object> map = new HashMap<>();
        List<TargetAndCaseInfoDTO> caseTarget = new ArrayList<>();
        try {
            caseTarget = caseInfoService.getCaseDetail(applyNo);
        } catch (Exception e) {
            return new FailureResult("查询失败", null);
        }
        System.out.println(caseTarget + "*******************************************************");
        map.put("list", caseTarget.isEmpty() ? null : caseTarget);
        return new SuccessResult("查询成功", map);
    }


    /**
     * 查看我的认领详情，流程历史
     * @Param applyNo 申请号
     */
    @SystemLog(methods = "认领详情", module = "流程历史")
    @RequestMapping(value = "/getCaseStatus", method = RequestMethod.POST)
    public Result getCaseStatus(@RequestBody CaseStatus caseStatus){
        String applyNo =caseStatus.getApplyNo();
        Map<String, Object> map = new HashMap<>();
        List<CaseStatus> caseTarget = new ArrayList<>();
        try {
            caseTarget = caseInfoService.getCaseStatus(applyNo);
            System.out.println(caseTarget.toString()+"--------------------------------------------");
        } catch (Exception e) {
            return new FailureResult("查询失败", null);
        }
        map.put("list", caseTarget.isEmpty() ? null : caseTarget);
        return new SuccessResult("查询成功", map);
    }
}
