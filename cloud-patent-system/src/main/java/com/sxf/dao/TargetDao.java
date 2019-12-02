package com.sxf.dao;


import com.sxf.entity.CaseTarget;
import com.sxf.entity.TargetAndCaseInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @description:指标维度查询专利信息
 * @author:muziru
 * @date:2019/11/07
 * @version:v1.0
 */

@Mapper
public interface TargetDao {
    /**
     * 查询所有指标
     */
    List<CaseTarget> getTargetInfo();
    /**
     * @Param targetAndCaseInfoDTO
     *根据指标属性查询
     * @return
     */
    List<TargetAndCaseInfoDTO> selectTargetAndCase(@Param("targetAndCaseInfoDTO") TargetAndCaseInfoDTO targetAndCaseInfoDTO);

    /**
     * 查看某一指标详情
     * @Patam targetId 指标详情 inventionName 专利名称
     * */
    List<TargetAndCaseInfoDTO> getTargetDetail(@Param("targetId") String targetId, @Param("inventionName") String inventionName);


    /**
     * 添加专利指标
     * @param caseTarget
     */
    void addTarget(CaseTarget caseTarget);


    /**
     * 获取专利指标
     * @param caseId
     * @return
     */
    CaseTarget getTargetByCaseId(@Param("caseId") String caseId);


    void updateCaseTarget(@Param("caseTarget") CaseTarget caseTarget);
}
