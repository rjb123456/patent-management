package com.sxf.dao;

import com.sxf.entity.CaseFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/20 11:55
 * @description：
 * @version: $
 */
@Mapper
public interface CaseFileDao {

    String TABLE_NAME = "case_file";

    String FIELD = "case_id, file_path, file_type, is_use, create_time, update_time";


    /**
     *
     * @param caseId
     * @return
     */
    @Select("select "+FIELD+" from "+TABLE_NAME+" where case_id=#{case_id}")
    @Results({
            @Result(column="case_id", property="caseId", id=true),
            @Result(column="file_path", property="filePath"),
            @Result(column="file_type", property="fileType"),
            @Result(column="is_use", property="isUse"),
            @Result(column="create_time", property="createTime"),
            @Result(column="update_time", property="updateTime")})
    List<CaseFile> getByCaseId(@Param("case_id") String caseId);


    /**
     *
     * @param caseFile
     * @return
     */
    @Insert({"insert into ", TABLE_NAME, "(", FIELD, ") values (#{caseId},#{filePath},#{fileType},#{isUse},#{createTime},#{updateTime})"})
    int insertCaseFile(CaseFile caseFile);

    /**
     * 更新专利文件的使用状态
     * @param caseFile
     */
    @Update({"update ",TABLE_NAME," set is_use=#{isUse}, update_time=#{updateTime} where case_id=#{caseId} and file_type=#{fileType}"})
    void update(CaseFile caseFile);

    /**
     * 查看专利交底书
     * @param caseId
     * @return
     */
    @Select({"select ",FIELD," from ",TABLE_NAME,"where case_id=#{case_id} and file_type=1 and is_use=0"})
    CaseFile forSecondCheck(@Param("case_id") String caseId);
}
