package com.sxf.controller;


import com.sxf.entity.CaseInformation;
import com.sxf.log.SystemLog;
import com.sxf.service.CaseInfoService;
import com.sxf.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author WangXin
 */

@RestController
public class PoiController {

    @Autowired
    PoiService poiService;

    @Autowired
    CaseInfoService caseInfoService;

    /**
     * 专利维度导出
     * 输出调用controller
     *
     * @author WangXin
     */
    @SystemLog(methods = "专利维度导出", module = "专利维度导出")
    @RequestMapping(value = "/export", produces = {"application/vnd.ms-excel;charset=UTF-8"}, method = RequestMethod.GET)
    public String export(HttpServletResponse response, @RequestParam String inventionName, @RequestParam String caseId, @RequestParam String applyNo, @RequestParam String inventorName, @RequestParam String lawStatus) {
        CaseInformation caseInformation = new CaseInformation();
        caseInformation.setApplyNo(applyNo);
        caseInformation.setCaseId(caseId);
        caseInformation.setInventionName(inventionName);
        caseInformation.setLawStatus(lawStatus);
        caseInformation.setInventorName(inventorName);
        List<CaseInformation> dataList = caseInfoService.selectCaseExcel(caseInformation);
        return poiService.export(response, dataList);

    }


    /**
     * 指标维度导出
     * 输出调用controller
     *
     * @author WangXin
     */
    @SystemLog(methods = "专利指标维度导出", module = "专利指标维度导出")
    @RequestMapping(value = "/exports", produces = {"application/vnd.ms-excel;charset=UTF-8"}, method = RequestMethod.GET)
    public String exports(HttpServletResponse response, @RequestParam String inventionName, @RequestParam String inventorName, @RequestParam String applyNo, @RequestParam String targetId, @RequestParam String lawStatus) {

        CaseInformation caseInformation = new CaseInformation();
        caseInformation.setInventionName(inventionName);
        caseInformation.setInventorName(inventorName);
        // caseInformation.setTargetId(targetId);
        caseInformation.setApplyNo(applyNo);
        caseInformation.setLawStatus(lawStatus);
        List<CaseInformation> dataListT = caseInfoService.selectCaseExcel(caseInformation);
        return poiService.exports(response, dataListT);
    }

}
