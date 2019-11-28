package com.sxf.service;

import com.sxf.entity.CaseInformation;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * excel导出接口
 * @author WangXin
 */
public interface PoiService {

    /**
     * 专利维度ExcelService接口
     * @param response
     * @param dataList
     * @return
     */
    String export(HttpServletResponse response, List<CaseInformation> dataList);


    /**
     * 指标维度ExcelService接口
     * @param response
     * @param dataListT
     * @return
     */
    String exports(HttpServletResponse response, List<CaseInformation> dataListT);

}
