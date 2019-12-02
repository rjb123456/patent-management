package com.sxf.dao;

import com.sxf.entity.CaseInformation;
import com.sxf.entity.CaseStatus;
import com.sxf.entity.TargetAndCaseInfoDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/19 14:05
 * @description：对case_information表数据库操作
 * @version: 0.0.1
 */

@Mapper
public interface CaseInfoDao {
    String TABLE_NAME = " case_information ";
    String FIELDS = " id, batch, case_id, apply_no, apply_date, tec_contact, apply_name, " +
            "invention_name, law_status, patent_type, inventor_name, modifier_name, account_id, " +
            "is_check, is_new, mark, update_time, create_time, is_use ";

    /**
     * 按专利id获取专利信息
     *
     * @param id 专利自增id
     * @return 专利信息
     */
    @Select({"select ", FIELDS, " from ", TABLE_NAME, "where id = #{id}"})
    CaseInformation getById(@Param("id") int id);


    /**
     * 专利被认领 专利绑定撰写人及撰写人account_id
     *
     * @param caseInformation 专利信息
     * @return
     */
    @Update({"update ", TABLE_NAME, " set modifier_name=#{modifierName} , account_id=#{accountId} , law_status=#{lawStatus} where id=#{id}"})
    int update(CaseInformation caseInformation);


    /**
     * 修改专利信息
     *
     * @param caseInformation
     * @return
     */
    @Update({"update ", TABLE_NAME, " set batch=#{batch} , case_id=#{caseId} , apply_no=#{applyNo}, apply_date=#{applyDate}, tec_contact=#{tecContact}, apply_name=#{applyName} , " +
            "invention_name=#{inventionName}, law_status=#{lawStatus}, patent_type=#{patentType}, inventor_name=#{inventorName}, is_check=#{isCheck}, is_new=#{isNew}," +
            " mark=#{mark}, update_time=#{updateTime}, create_time=#{createTime}, is_use=#{isUse} , modifier_name=#{modifierName} , account_id=#{accountId} where id=#{id}"})
    int changeCaseInfo(CaseInformation caseInformation);


    /**
     * @param case_id
     * @return
     * @author ：jhj
     */
    @Select({"select modifier_name from ", TABLE_NAME, "where case_id = #{case_id}"})
    CaseInformation getModifierNameById(@Param("case_id") String case_id);

    /**
     * @param is_check
     * @param case_id
     * @return
     * @author ：jhj
     */
    @Update({"update ", TABLE_NAME, " set is_check=#{is_check} where case_id=#{case_id}"})
    int updateIsCheck(@Param("is_check") int is_check,
                      @Param("case_id") String case_id);


    /**
     * 添加专利信息
     *
     * @param caseInformation
     */
    void addCaseInfo(CaseInformation caseInformation);

    /**
     * 我的认领页面查询
     *
     * @param accountId
     * @return
     */
    @Select({"select ", FIELDS, " from ", TABLE_NAME, "where account_id = #{account_id}"})
    List<CaseInformation> getByInfoId(@Param("account_id") String accountId);

    /**
     * 删除第一次审核不通过的数据
     *
     * @param case_id
     * @return
     */
    @Delete({"delete from ", TABLE_NAME, "where case_id=#{case_id}"})
    int delete(@Param("case_id") String case_id);

    /**
     * 查询需要审核的专利(包含两次审核)
     *
     * @return
     */
    @Select({"select ", FIELDS, " from ", TABLE_NAME, "where is_check = 1"})
    List<CaseInformation> getAdminCheckList();

    /**
     * 按caseId获取专利信息
     *
     * @param caseId
     * @return
     */
    @Select({"select ", FIELDS, " from ", TABLE_NAME, "where case_id = #{caseId}"})
    CaseInformation getByCaseId(@Param("caseId") String caseId);


    /**
     * 查询传来的专利caseId与applyNo是否重复存在
     * @param caseId
     * @param applyNo
     * @return
     */
    CaseInformation getCaseInfoByCaseId(@Param("caseId") String caseId, @Param("applyNo") String applyNo);


    /**
     * 查询所有专利信息
     */
    List<CaseInformation> getCaseInfo();

    /**
     * 依据属性查询专利信息
     *
     * @return
     * @Param caseInformation
     */

    List<CaseInformation> selectCaseByAttribute(@Param("caseInformation") CaseInformation caseInformation);

    /**
     * 查看某-专利详情
     *
     * @Param applyNo 申请号
     */
    List<TargetAndCaseInfoDTO> getCaseDetail(String applyNo);


    /**
     * 查看我的认领详情，流程历史
     * @Param applyNo 申请号
     */
    List<CaseStatus> getCaseStatus(String applyNo);

    /**
     * 依据属性查询专利信息为导出Excel服务
     *
     * @return
     * @Param caseInformation
     */

    List<CaseInformation> selectCaseExcel(@Param("caseInformation") CaseInformation caseInformation);

}
