package com.sxf.dao;

import com.sxf.entity.CaseAccount;
import org.apache.ibatis.annotations.*;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/19 10:43
 * @description：对case_account表数据库操作
 * @version: $
 */

@Mapper
public interface AccountDao {

    String TABLE_NAME = " case_account ";
    String SELECT_FIELDS = " account_id, password, type, update_time, create_time ,modifier_name ";
    String INSERT_FIELDS = " account_id, password, type, create_time ,modifier_name ";

    /**
     * 获取用户
     *
     * @param id 用户id
     * @return 对应id用户
     */
    @Select({"SELECT id, ", SELECT_FIELDS, " FROM ", TABLE_NAME, " WHERE id=#{id}"})
    CaseAccount getById(@Param("id") int id);

    /**
     * 根据用户账号查询用户
     *
     * @param accountId 用户账号
     * @return 用户
     */
    @Select("SELECT * FROM case_account WHERE account_id = #{accountId}")
    CaseAccount getByAccountId(String accountId);

    /**
     * 插入用户
     *
     * @param caseAccount
     * @return
     */
    @Insert("INSERT INTO " + TABLE_NAME + "(" + INSERT_FIELDS + ")values(#{accountId}, #{password}, #{type}, #{createTime}, #{modifierName})")
    int insertCaseAccount(CaseAccount caseAccount);
}
