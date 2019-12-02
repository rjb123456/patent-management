package com.sxf.controller;

import com.sxf.entity.CaseFile;
import com.sxf.entity.CaseStatus;
import com.sxf.exception.GlobalException;
import com.sxf.log.SystemLog;
import com.sxf.result.CodeMsg;
import com.sxf.service.CaseInfoService;
import com.sxf.service.CaseStatusService;
import com.sxf.service.CaseTargetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/19 10:02
 * @description：文件操作
 * @version: 0.0.1$
 */
@Slf4j
@RestController
public class FileController {


    @Autowired
    CaseInfoService caseInfoService;

    @Autowired
    CaseStatusService caseStatusService;

    @Autowired
    CaseTargetService caseTargetService;

    /**
     * 功能：文件上传及二次上传(实现逻辑删除)文件覆盖 同时更新专利状态(即文件类型)
     * 1.交底书
     * 2.申请文件
     * 3.受理通知书
     * 4.初审合格通知书
     * 5.公布及进入实审通知书
     * 6.审查意见；答复文件
     * 7.授权办登通知书
     * 8.驳回通知书
     * 9.证书
     *
     * @param caseId 专利id
     * @param fileType  上传的文件类型
     * @param file   文件
     * @return -1 找不到id 1 操作成功 2文件上传失败
     */
    @SystemLog(methods = "文件上传", module = "上传文件")
    @PostMapping("/upload")
    public int uploadChangeStatus(@RequestParam("caseId") String caseId, @RequestParam("fileType") int fileType, @RequestParam("file") MultipartFile file) {
        CaseStatus caseStatus = caseStatusService.getById(caseId);
        if (caseStatus == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        return caseStatusService.upload(caseId, fileType, file);
    }

    /**
     * 显示流程历史对文件的操作
     */
    @SystemLog(methods = "历史列表", module = "显示历史流程")
    @PostMapping("/history")
    public List<CaseFile> history(@RequestBody CaseFile caseFile) {
        List<CaseFile> caseFileList = caseStatusService.getCaseFileByCaseId(caseFile.getCaseId());
        List<CaseFile> cl = new ArrayList<>();
        for (CaseFile cf : caseFileList) {
            if (cf.getFileType() == caseFile.getFileType()) {
                cl.add(cf);
            }
        }
        return cl;
    }

    /**
     * 交底书下载
     */
    @SystemLog(methods = "交底书下载", module = "交底书下载")
    @RequestMapping(value = "/download/{caseId}", method = RequestMethod.GET, produces ="application/json;charset=UTF-8")
    public Object downloadModel(@PathVariable String caseId){
        ResponseEntity<InputStreamResource> response = null;
        try {
            response = caseStatusService.download(caseId);
        } catch (Exception e) {
            log.error("下载模板失败");
        }
        return response;
    }

}
