package com.sxf.controller;


import com.sxf.entity.CaseInformation;
import com.sxf.service.CaseInfoService;
import com.sxf.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
/**
 * @author WangXin
 */

@RestController
public class  PoiController {

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
    @RequestMapping(value = "/export", produces = {"application/vnd.ms-excel;charset=UTF-8"}, method = RequestMethod.GET)
    public String export(HttpServletResponse response) {

        List<CaseInformation> dataList = new ArrayList<>();
        dataList = caseInfoService.getCaseInfo();
        return poiService.export(response, dataList);
    }

    /**
     * 指标维度导出
     * 输出调用controller
     *
     * @author WangXin
     */
    @RequestMapping(value = "/exports", produces = {"application/vnd.ms-excel;charset=UTF-8"}, method = RequestMethod.GET)
    public String exports(HttpServletResponse response) {
        List<CaseInformation> dataListT = new ArrayList<>();
        dataListT = caseInfoService.getCaseInfo();
        return poiService.exports(response, dataListT);
    }

}
