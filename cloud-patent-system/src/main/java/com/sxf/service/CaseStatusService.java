package com.sxf.service;

import com.sxf.dao.CaseFileDao;
import com.sxf.dao.CaseStatusDao;
import com.sxf.entity.CaseFile;
import com.sxf.entity.CaseStatus;
import com.sxf.exception.GlobalException;
import com.sxf.result.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/20 10:42
 * @description：面向专利状态表服务
 * @version: 0.0.1
 */

@Service
public class CaseStatusService {

    @Autowired
    CaseStatusDao caseStatusDao;

    @Autowired
    CaseFileDao caseFileDao;

    public CaseStatus getById(String caseId) {
        return caseStatusDao.getById(caseId);
    }

    public void updateCaseStatus(CaseStatus caseStatus) {
        caseStatusDao.updateCaseStatus(caseStatus);
    }

    public void updateStatusModifierName(CaseStatus caseStatus) {
        caseStatusDao.updateStatusModifierName(caseStatus);
    }

    public List<CaseFile> getCaseFileByCaseId(String caseId) {
        return caseFileDao.getByCaseId(caseId);
    }

    public int insertCaseFile(CaseFile caseFile) {
        return caseFileDao.insertCaseFile(caseFile);
    }

    public void updateCaseFile(CaseFile caseFile) {
        caseFileDao.update(caseFile);
    }

    public int updateCaseStatuss(String case_id, int status) {
        return caseStatusDao.updateCaseStatuss(case_id, status);
    }

    public int upload(String caseId, int ftype, MultipartFile file) {
        //创建专利专用文件夹
        String casePath = "D:\\" + caseId;
        File myPath = new File(casePath);
        if (!myPath.exists()) {
            myPath.mkdir();
            System.out.println("创建文件夹路径:" + casePath);
        }

        //上传文件
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_MM_SS");
        String fileName = df.format(new Date()) + file.getOriginalFilename();
        File f = new File(casePath, fileName);
        try {
            file.transferTo(f);
        } catch (IOException e) {
            throw new GlobalException(CodeMsg.UPLOAD_FAILED);
        }

        //文件完整路径插表
        String filePath = casePath + "\\" + fileName;

        //是否对旧数据操作 添加新类型文件改变专利状态
        int flag = 0;

        List<CaseFile> caseFileList = getCaseFileByCaseId(caseId);
        if (caseFileList.size() == 0) {
            CaseFile cf = addCaseFile(caseId,ftype,new Date(),filePath);
            insertCaseFile(cf);
        } else {
            for (CaseFile cf : caseFileList) {
                if (cf.getFileType() == ftype && cf.getIsUsed() == 0) {
                    flag = 1;
                    //旧的记录无效化-1
                    cf.setIsUsed(-1);
                    cf.setUpdateTime(new Date());
                    updateCaseFile(cf);
                    //插入新的记录（复用）
                    cf.setFilePath(filePath);
                    cf.setCreateTime(new Date());
                    cf.setUpdateTime(null);
                    cf.setIsUsed(0);
                    insertCaseFile(cf);
                    break;
                }
            }
            //库中不存在
            if (flag == 0) {
                CaseFile cf = addCaseFile(caseId,ftype,new Date(),filePath);
                insertCaseFile(cf);
            }
        }
        //上传新的文件 改变专利状态
        if(flag == 0){
            changeStatus(caseId, ftype);
        }
        return 1;

    }

    private void changeStatus(String caseId, int status){
        CaseStatus caseStatus = getById(caseId);
        caseStatus.setStatus(status);
        updateCaseStatus(caseStatus);
    }

    private static CaseFile addCaseFile(String caseId, int ftype, Date date, String filePath) {
        CaseFile cf = new CaseFile();
        cf.setCaseId(caseId);
        cf.setFileType(ftype);
        //设置 0 有效
        cf.setIsUsed(0);
        cf.setCreateTime(date);
        cf.setFilePath(filePath);
        return cf;
    }

    /**
     * 添加专利状态
     * @param caseStatus
     */
    public void addCaseStatus(CaseStatus caseStatus) {
        caseStatusDao.addCaseStatus(caseStatus);
    }


    public CaseFile getSecondCheckFile(String caseId) {
        return caseFileDao.forSecondCheck(caseId);
    }
}
