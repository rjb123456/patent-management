package com.sxf.service;

import com.sxf.dao.CaseInfoDao;
import com.sxf.dao.CaseInfoTarget;
import com.sxf.entity.CaseInformation;
import com.sxf.entity.CaseStatus;
import com.sxf.entity.TargetAndCaseInfoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @date ：Created in 2019/11/19 10:53
 * @description：
 * @version: $
 */
@Service
public class CaseInfoService {

    @Autowired
    CaseInfoDao caseInfoDao;

    /**
     *查询所有专利信息
     * @param
     * @return
     */
    public List<CaseInformation> getCaseInfo(){
        return caseInfoDao.getCaseInfo();
    }
    /**
     *根据属性查询专利信息
     * @param
     * @return
     */
    public List<CaseInformation> selectCaseByAttribute(@RequestBody CaseInformation caseInformation){return caseInfoDao.selectCaseByAttribute(caseInformation);}
    /**
     *查看详情
     * @param applyNo 申请号
     * @return
     */
    public List<TargetAndCaseInfoDTO> getCaseDetail(String applyNo){
        System.out.println("service" + applyNo);
        System.out.println(caseInfoDao.getCaseDetail(applyNo));
        return caseInfoDao.getCaseDetail(applyNo);
    }
    /**
     *根据属性查询当前页面专利信息为生成Excel提供服务
     * @param
     * @return
     */
    public List<CaseInformation> selectCaseExcel(@RequestBody CaseInformation caseInformation){return caseInfoDao.selectCaseExcel(caseInformation);}

    public CaseInformation getById(int id) {
        return caseInfoDao.getById(id);
    }

    /**
     * 绑定撰写人及撰写人id
     *
     * @param caseInformation
     */
    public void updateCaseInfo(CaseInformation caseInformation) {
        caseInfoDao.update(caseInformation);
    }

    /**
     * 修改专利信息
     *
     * @param caseInformation
     */
    public void changeCaseInfo(CaseInformation caseInformation) {
        caseInfoDao.changeCaseInfo(caseInformation);
    }

    public CaseInformation getModifierNameById(String caseId) {
        return caseInfoDao.getModifierNameById(caseId);
    }

    public int updateIsCheck(int isCheck, String caseId) {
        return caseInfoDao.updateIsCheck(isCheck, caseId);
    }

    public void addCase(CaseInformation caseInformation) {
        /**
         * 初始化专利
         */
        //处理时间，自动生成批次
        caseInformation.setBatch("第一批");
        caseInformation.setApplyDate(new Date());
        //申请日期
        caseInformation.setCreateTime(new Date());
        caseInformation.setUpdateTime(new Date());
        caseInformation.setIsCheck(1);
        //默认待审核
        caseInformation.setIsUse(1);
        //记录标识默认正常
        caseInformation.setIsNew(1);
        caseInformation.setLawStatus("1");
        // System.out.println(caseInformation + "****************************************");
        caseInfoDao.addCaseInfo(caseInformation);
    }

    public CaseInformation getCaseInfo(CaseInfoTarget caseInfoTarget) {
        CaseInformation caseInformation = new CaseInformation();
        BeanUtils.copyProperties(caseInfoTarget, caseInformation);
        return caseInformation;
    }

    public void delete(String caseId) {
        caseInfoDao.delete(caseId);
    }

    /**
     * 获取第一次审核数据
     *
     * @return
     */
    public List<CaseInformation> getAdminCheckList(int isFirst) {
        //1 表示待审核
        List<CaseInformation> caseInformationList = caseInfoDao.getAdminCheckList();
        List<CaseInformation> temp = new ArrayList<>();
        if (isFirst == 1) {
            //第一次审核
            for (CaseInformation ci : caseInformationList) {
                if ("".equals(ci.getAccountId()) || ci.getAccountId() == null) {
                    temp.add(ci);
                }
            }
            return temp;
        } else {
            //第二次审核
            for (CaseInformation ci : caseInformationList) {
                if (!"".equals(ci.getAccountId())) {
                    temp.add(ci);
                }
            }
            return temp;
        }
    }

    public CaseInformation getByCaseId(String caseId) {
        return caseInfoDao.getByCaseId(caseId);
    }

    /**
     * 查询传来的专利caseId与applyNo是否重复存在
     * @param caseId
     * @param applyNo
     * @return
     */
    public CaseInformation getCaseInfoByCaseId(String caseId,String applyNo){
        return caseInfoDao.getCaseInfoByCaseId(caseId,applyNo);
    }

    /**
     * 查看我的认领详情，流程历史
     * @Param applyNo 申请号
     */
    public  List<CaseStatus> getCaseStatus(String applyNo){
        return caseInfoDao.getCaseStatus(applyNo);
    }

}
