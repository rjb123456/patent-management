package com.sxf.controller;


import com.sxf.dao.CaseInfoTarget;
import com.sxf.entity.CaseInformation;
import com.sxf.entity.CaseStatus;
import com.sxf.entity.CaseTarget;
//import com.sxf.log.SystemLog;
import com.sxf.entity.TargetAndCaseInfoDTO;
import com.sxf.service.CaseInfoService;
import com.sxf.service.CaseStatusService;
import com.sxf.service.CaseTargetService;
import com.sxf.utils.FailureResult;
import com.sxf.utils.Result;
import com.sxf.utils.SuccessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CaseController {
    @Autowired
    CaseInfoService caseInfoService;
    @Autowired
    CaseTargetService caseTargetService;

    @Autowired
    CaseStatusService caseStatusService;


 /*   @RequestMapping("/addCaseIDD")
    public Map<String,String> ad(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("code","123");
        return  map;
    }*/
    /**
     * @param
     * @param
     * @return String 0 是失败   1是成功
     * 添加专利，生成指标
     */
    //@SystemLog(methods = "专利列表", module = "专利申添请加")
    @RequestMapping("/addCase")
    public Map<String,String> addCase(@RequestBody CaseInfoTarget caseInfoTarget) {
        System.out.println(caseInfoTarget);
        String targetId = caseInfoTarget.getTargetId();
        String applyNo = caseInfoTarget.getApplyNo();
        System.out.println(targetId);
        CaseInformation caseInformation = caseInfoService.getCaseInfo(caseInfoTarget);
        System.out.println(caseInformation);
        String flag = "0";
        Map<String,String> map = new HashMap<String,String>();
        map.put("code",flag);
        if (caseInfoTarget.getCaseId() == null || caseInfoTarget.getCaseId().trim().equals("")){
            return map;
        }
        if (caseInfoTarget.getApplyNo() == null || caseInfoTarget.getApplyNo().trim().equals("")){
            return  map;
        }
        //判断
        if (targetId == null || targetId.trim().equals("") ) {
            return map;
        }

        String caseId=caseInformation.getCaseId();
        if(caseInfoService.getCaseInfoByCaseId(caseId,applyNo) != null){
            flag = "caseId重复或者applyNo重复";
            map.put("code",flag);
            return  map;
        }else {
            flag = "1";
            map.put("code",flag);
        }

            /**
             *
             * 添加专利状态初始化
             */
            CaseStatus caseStatus = new CaseStatus();
            caseStatus.setCaseId(caseInformation.getCaseId());
            caseStatus.setApplyNo(caseInformation.getApplyNo());
            caseStatus.setApplyDate(new Date());
            caseStatus.setInventionName(caseInformation.getInventionName());
            caseStatus.setCreateTime(new Date());
            caseStatus.setUpdateTime(new Date());
            caseStatus.setIsUse(1);
            caseStatus.setStatus(0);
            caseStatus.setInventorName(caseInformation.getInventorName());
            // caseStatus.setModifierName();

            //添加专利
            caseStatusService.addCaseStatus(caseStatus);
            /**
             * 初始化指标对象
             */
            CaseTarget caseTarget = new CaseTarget();
            System.out.println(targetId);
            caseTarget.setTargetId(targetId);
            //指标
            caseTarget.setCaseId(caseInformation.getCaseId());
            //更新时间和创建时间
            caseTarget.setUpdateTime(new Date());
            //更新时间和创建时间
            caseTarget.setCreateTime(new Date());
            caseTarget.setIsUsed("1");
            //添加
            caseTargetService.addTarget(caseTarget);
            //添加指标
            caseInfoService.addCase(caseInformation);
            //添加专利
            return  map;


    }


    /**
     * 查询所有专利的信息
     *
     * @return
     */
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
    @RequestMapping(value = "/getCaseDetail", method = RequestMethod.POST)
    public Result getCaseDetail(@RequestBody TargetAndCaseInfoDTO targetAndCaseInfoDTO) {

        String applyNo = targetAndCaseInfoDTO.getApplyNo();
        System.out.println(applyNo + "_______________________________________________________");
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
}
