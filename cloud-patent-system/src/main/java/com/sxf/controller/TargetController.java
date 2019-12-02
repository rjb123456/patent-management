package com.sxf.controller;

import com.sxf.entity.CaseTarget;
import com.sxf.entity.TargetAndCaseInfoDTO;
import com.sxf.log.SystemLog;
import com.sxf.service.TargetService;
import com.sxf.utils.FailureResult;
import com.sxf.utils.Result;
import com.sxf.utils.SuccessResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @description:指标维度查询详细信息
 * @author:muziru
 * @date:2019/11/07
 * @version:v1.0
 */
@Slf4j
@RestController
public class TargetController {
    @Autowired
    TargetService targetService;

    /**
     * 查询所有指标的信息
     *
     * @return
     */
    @SystemLog(methods = "指标详情", module = "查询指标详情")
    @RequestMapping(value = "/getTargetInfo", method = RequestMethod.POST)
    public Result getTargetInfo() {
        Map<String, Object> map = new HashMap<>();
        List<CaseTarget> caseTargetList = new ArrayList<>();
        try {
            caseTargetList = targetService.getTargetInfo();
        } catch (Exception e) {
            return new FailureResult("查询指标信息失败", null);
        }

        map.put("list", caseTargetList.isEmpty() ? null : caseTargetList);
        map.put("code", 0);

        return new SuccessResult("查询成功", map);

    }

    /**
     * 联合查询,根据各个属性查询
     *
     * @param targetAndCaseInfoDTO 对象
     * @return
     */
    @SystemLog(methods = "指标查询", module = "联合查询指标")
    @RequestMapping(value = "/getTargetList", method = RequestMethod.POST)
    public Result getCaseInfo(@RequestBody TargetAndCaseInfoDTO targetAndCaseInfoDTO) {
        Map<String, Object> map = new HashMap<>();
        List<TargetAndCaseInfoDTO> tarList = new ArrayList<>();
        try {
            tarList = targetService.getList(targetAndCaseInfoDTO);
            log.info(tarList.toString()+"***********************");
        } catch (Exception e) {
            System.out.println(e);
            return new FailureResult("查询列表失败", null);
        }

        // 被认领的专利历表
        List<TargetAndCaseInfoDTO> claimList = new ArrayList<>();
        // 未被认领的专利列表
        List<TargetAndCaseInfoDTO> unClaimList = new ArrayList<>();
        for (TargetAndCaseInfoDTO tar : tarList) {
           if(tar.getIsCheck() == 2){
               if (tar.getModifierName() == null || "".equals(tar.getModifierName())) {
                   unClaimList.add(tar);
               } else {
                   claimList.add(tar);
               }
           }
        }

        map.put("unClaimList", unClaimList.isEmpty() ? null : unClaimList);
        map.put("claimList", claimList.isEmpty() ? null : claimList);

        return new SuccessResult(map);

    }

    /**
     * 查看详情
     *
     * @param targetAndCaseInfoDTO 指标详情   专利名称
     * @return
     */
    @SystemLog(methods = "指标详情", module = "指标详情")
    @RequestMapping(value = "/getTargetDetail", method = RequestMethod.POST)
    public Result getTargetDetail(@RequestBody TargetAndCaseInfoDTO targetAndCaseInfoDTO) {
        String targetId = targetAndCaseInfoDTO.getTargetId();
        String inventionName = targetAndCaseInfoDTO.getInventionName();
        System.out.println(targetId + "------------" + inventionName);
        Map<String, Object> map = new HashMap<>();
        List<TargetAndCaseInfoDTO> caseTarget = new ArrayList<>();
        try {
          caseTarget=  targetService.getTargetDetail(targetId, inventionName);
          System.out.println(caseTarget.toString()+"************************我沒錯");

        } catch (Exception e) {
            System.out.println("我錯了嗎");
            return new FailureResult("查询列表失败", null);
        }
        System.out.println(caseTarget.toString()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        map.put("list", caseTarget.isEmpty() ? null : caseTarget);
        return new SuccessResult("查看详情成功", map);

    }

}

