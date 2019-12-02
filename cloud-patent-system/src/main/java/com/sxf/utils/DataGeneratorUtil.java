package com.sxf.utils;

import com.sxf.entity.CaseInformation;
import com.sxf.mock.Pcd;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据生成工具，用来生成当前页面所展示的数据
 * @author WangXin
 */



@Mapper
public class DataGeneratorUtil {

    /**
     * 数据生成方法
     * @author WangXin
     *
     */

    public static List<Pcd> generatorPcd(List<CaseInformation> dataList) {

        List<Pcd> listPcd = new ArrayList<>();
        int id = 0;



        for (int i = 0; i < dataList.size(); i++) {
            Pcd pcd = new Pcd();

            pcd.setId(id+i+1);//编号
            pcd.setInventionName(dataList.get(i).getInventionName());//中文发明名称
            pcd.setCaseId(dataList.get(i).getCaseId());//案件文号
            pcd.setApplyNo(dataList.get(i).getApplyNo());//申请号
            pcd.setApplyDate(dataList.get(i).getApplyDate());//申请日期
            pcd.setInventorName(dataList.get(i).getInventorName());//发明人中文名
            pcd.setLawStatus(dataList.get(i).getLawStatus());//进度
            pcd.setModifierName(dataList.get(i).getModifierName());//撰写人
            pcd.setApplyNo(dataList.get(i).getApplyNo());
            pcd.setTargetId("指标详情");
            listPcd.add(pcd);
        }
        return listPcd;
    }



}
