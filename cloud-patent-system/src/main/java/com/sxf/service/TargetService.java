package com.sxf.service;

import com.sxf.dao.TargetDao;
import com.sxf.entity.CaseInformation;
import com.sxf.entity.CaseTarget;
import com.sxf.entity.TargetAndCaseInfoDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description:指标维度查询
 * @author:muziru
 * @date:2019/11/07
 * @version:v1.0
 */
@Service
public class TargetService {
    @Autowired
    TargetDao targetDao;
    /**
     *查询所有指标信息
     * @param
     * @return
     */
    public List<CaseTarget> getTargetInfo(){
        return targetDao.getTargetInfo();
    }
    /**
     *根据属性查询专利信息
     * @param
     * @return
     */
    public List<TargetAndCaseInfoDTO> getList(@Param("targetAndCaseInfoDTO") TargetAndCaseInfoDTO targetAndCaseInfoDTO){return targetDao.selectTargetAndCase(targetAndCaseInfoDTO);}
    /**
     *查看详情
     * @param targetId 指标详情inventionName 专利名称
     * @return
     */
    public List<TargetAndCaseInfoDTO> getTargetDetail(@Param("targetId") String targetId,@Param("inventionName") String inventionName){
        return targetDao.getTargetDetail(targetId,inventionName);
    }

}
