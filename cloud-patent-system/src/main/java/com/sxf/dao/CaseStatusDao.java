package com.sxf.dao;


import com.sxf.entity.CaseStatus;
import org.apache.ibatis.annotations.*;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/20 10:44
 * @description：
 * @version: $
 */
@Mapper
public interface CaseStatusDao {

    String TABLE_NAME = " case_status ";
    String FIELDS = " id, case_id, apply_no, apply_date, invention_name, modifier_name, " +
            "status, update_time, create_time, is_use ";


    /**
     * @param caseId 专利案件文号
     * @return 专利状态
     */
    @Select({"select ",FIELDS," from ",TABLE_NAME,"where case_id = #{case_id}"})
    CaseStatus getById(@Param("case_id") String caseId);


    /**
     * 更新专利状态表的状态信息
     * @param caseStatus 专利状态
     */
    @Update({"update ",TABLE_NAME," set status=#{status} where case_id=#{caseId}"})
    void updateCaseStatus(CaseStatus caseStatus);

    /**
     * 更新专利状态表的撰写人信息
     * @param caseStatus 专利状态
     */
    @Update({"update ",TABLE_NAME," set modifier_name=#{modifierName} where case_id=#{caseId}"})
    void updateStatusModifierName(CaseStatus caseStatus);


    /**
     *
     * @param case_id
     * @param status
     * @return
     */
    @Update({"update" , TABLE_NAME, " set status=#{status} where case_id=#{case_id}"})
    int updateCaseStatuss(String case_id,int status);

    /**
     * 添加专利状态
     * @param caseStatus
     */
    void addCaseStatus(CaseStatus caseStatus);


}
