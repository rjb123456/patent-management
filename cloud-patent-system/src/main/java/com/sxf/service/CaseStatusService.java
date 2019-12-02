package com.sxf.service;

import com.sxf.dao.CaseFileDao;
import com.sxf.dao.CaseStatusDao;
import com.sxf.entity.CaseFile;
import com.sxf.entity.CaseInformation;
import com.sxf.entity.CaseStatus;
import com.sxf.exception.GlobalException;
import com.sxf.result.CodeMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
@Slf4j
@Service
public class CaseStatusService {

    @Autowired
    CaseStatusService caseStatusService;

    @Autowired
    CaseStatusDao caseStatusDao;

    @Autowired
    CaseInfoService caseInfoService;

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

        CaseStatus caseStatus = caseStatusDao.getById(caseId);
        CaseInformation ci = caseInfoService.getByCaseId(caseId);
        if(ftype != 1 && ci.getIsCheck() == 3){
            throw new GlobalException(CodeMsg.SECOND_CHECK_FAILED);
        }
        if(ftype == 2 && ci.getIsCheck() == 1){
            throw new GlobalException(CodeMsg.SECOND_CHECK_FAILED);
        }

        if(ftype == 2 && ci.getIsCheck() == 3){
            throw new GlobalException(CodeMsg.SECOND_CHECK_FAILED);
        }
        //不可跨阶段上传文件
        if(caseStatus.getStatus() < (ftype-1)){
            throw new GlobalException(CodeMsg.UPLOAD_FAILED);
        }
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
                if (cf.getFileType() == ftype && cf.getIsUse() == 0) {
                    flag = 1;
                    //旧的记录无效化-1
                    cf.setIsUse(-1);
                    cf.setUpdateTime(new Date());
                    updateCaseFile(cf);
                    //插入新的记录（复用）
                    cf.setFilePath(filePath);
                    cf.setCreateTime(new Date());
                    cf.setUpdateTime(null);
                    cf.setIsUse(0);
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
        }else if(flag == 1 && ci.getIsCheck() == 3){
            changeStatus(caseId,ftype);
        }
        return 1;

    }

    private void changeStatus(String caseId, int status){
        CaseStatus caseStatus = getById(caseId);
        caseStatus.setStatus(status);
        updateCaseStatus(caseStatus);
        if(status == 1){
            //开始二审
            caseInfoService.updateIsCheck(1,caseId);
        }
        //第九个文件上传 表示结束专利流程 专利结案
        if(status == 9){
            CaseInformation ci = caseInfoService.getByCaseId(caseId);
            ci.setLawStatus("3");
            caseInfoService.updateCaseInfo(ci);
        }
    }

    private static CaseFile addCaseFile(String caseId, int ftype, Date date, String filePath) {
        CaseFile cf = new CaseFile();
        cf.setCaseId(caseId);
        cf.setFileType(ftype);
        //设置 0 有效
        cf.setIsUse(0);
        cf.setCreateTime(date);
        cf.setFilePath(filePath);
        return cf;
    }

    /**
     * 添加专利状态
     * @param
     */
    public void addCaseStatus(CaseInformation caseInformation) {
        CaseStatus caseStatus = new CaseStatus();
        caseStatus.setCaseId(caseInformation.getCaseId());
        caseStatus.setApplyNo(caseInformation.getApplyNo());
        caseStatus.setApplyDate(new Date());
        caseStatus.setInventionName(caseInformation.getInventionName());
        caseStatus.setCreateTime(new Date());
        caseStatus.setUpdateTime(new Date());
        caseStatus.setIsUse(1);
        caseStatus.setStatus(0);
        caseStatus.setInventorName(caseInformation.getInventorName());
        caseStatusDao.addCaseStatus(caseStatus);
    }


    public CaseFile getSecondCheckFile(String caseId) {
        return caseFileDao.forSecondCheck(caseId);
    }




    public ResponseEntity<InputStreamResource> download(String caseId) {
        ResponseEntity<InputStreamResource> response = null;

        if("".equals(caseId)){
            throw new GlobalException(CodeMsg.REQUEST_ILLEGAL);
        }
        CaseFile caseFile = caseStatusService.getSecondCheckFile(caseId);
        if(caseFile == null){
            throw new GlobalException(CodeMsg.DOWNLOAD_FAILED);
        }
        String[] fileNames = caseFile.getFilePath().split("\\.");
        String fileName = "handbook."+fileNames[1];
        try {
            FileInputStream inputStream = new FileInputStream(new File(caseFile.getFilePath()));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", "attachment; filename=" + fileName);
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            response = ResponseEntity.ok().headers(headers)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(inputStream));

        } catch (FileNotFoundException e1) {
            log.error("找不到指定的文件", e1);
        }
        return response;
    }
}
