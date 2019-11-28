package com.sxf.service.implExc;

import com.sxf.entity.CaseInformation;
import com.sxf.mock.Pcd;
import com.sxf.service.PoiService;
import com.sxf.utils.DataGeneratorUtil;
import com.sxf.utils.ExcelUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author WangXin
 */
@Service
public class PoiServiceImpl implements PoiService {

    /**
     * 输出方法
     * 输出当前显示的专利维度信息为Excel表格
     *
     * @author WangXin
     */
    @Override
    public String export(HttpServletResponse response, List<CaseInformation> dataList) {
        try {
            // int dataNum = 10;
            List<Pcd> listPcd = DataGeneratorUtil.generatorPcd(dataList);
            String fileName = "专利详情信息表";
            List<Map<String, Object>> list = createExcelRecord(listPcd);
            //列名
            String[] columnNames = {"编号", "中文发明名称", "案件文号", "申请号", "申请日期", "发明人中文名", "进度", "撰写人"};
            //map中的key
            String[] keys = {"id", "invention_name", "case_id", "apply_no", "apply_date", "inventor_name", "law_status", "modifier_name"};
            ExcelUtil.downloadWorkBook(list, keys, columnNames, fileName, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "excel";
    }

    /**
     * 创建Excel表中的记录
     *
     * @param pcdList
     * @author WangXin
     */
    private List<Map<String, Object>> createExcelRecord(List<Pcd> pcdList) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sheetName", "sheet1");
            listmap.add(map);
            for (int j = 0; j < pcdList.size(); j++) {
                Pcd pcd = pcdList.get(j);
                Map<String, Object> mapValue = new HashMap<String, Object>();
                mapValue.put("id", pcd.getId());
                mapValue.put("invention_name", pcd.getInventionName());
                mapValue.put("case_id", pcd.getCaseId());
                mapValue.put("apply_no", pcd.getApplyNo());
                mapValue.put("apply_date", pcd.getApplyDate());
                mapValue.put("inventor_name", pcd.getInventorName());
                mapValue.put("law_status", pcd.getLawStatus());
                mapValue.put("modifier_name", pcd.getModifierName());
                listmap.add(mapValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listmap;
    }

    /**
     * 输出方法
     * 输出当前显示的指标维度信息为Excel表格
     * @author WangXin
     *
     */
    @Override
    public String exports(HttpServletResponse response,List<CaseInformation> dataListT) {
        try {
            CaseInformation Pcd = new CaseInformation();
            List<Pcd> listPcd = DataGeneratorUtil.generatorPcd(dataListT);
            String fileName="指标详情信息表";
            List<Map<String,Object>> list=createExcelRecords(listPcd);
            String columnNames[] = {"编号","指标详情","中文发明名称","申请号","申请日期","发明人中文名","进度"};
            //列名
            String keys[] = {"id","target_id","invention_name","apply_no","apply_date","inventor_name","law_status"};
            //map中的key
            ExcelUtil.downloadWorkBook(list,keys,columnNames,fileName,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "excel";
    }

    /**
     * 创建Excel表中的记录
     * @author WangXin
     * @param pcdList
     * @return
     */
    private List<Map<String, Object>> createExcelRecords(List<Pcd> pcdList){
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sheetName", "sheet1");
            listmap.add(map);
            for (int j = 0; j < pcdList.size(); j++) {
                Pcd pcd=pcdList.get(j);
                Map<String, Object> mapValue = new HashMap<String, Object>();
                mapValue.put("id",pcd.getId());
                mapValue.put("target_id",pcd.getTargetId());
                mapValue.put("inventor_name",pcd.getInventorName());
                mapValue.put("apply_no",pcd.getApplyNo());
                mapValue.put("case_id",pcd.getCaseId());
                mapValue.put("apply_date",pcd.getApplyDate());
                mapValue.put("invention_name",pcd.getInventionName());
                mapValue.put("law_status",pcd.getLawStatus());
                listmap.add(mapValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listmap;
    }
}
