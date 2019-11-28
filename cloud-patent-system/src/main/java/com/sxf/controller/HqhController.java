package com.sxf.controller;

import com.sxf.entity.*;
import com.sxf.exception.GlobalException;
import com.sxf.result.CodeMsg;
import com.sxf.result.Result;
import com.sxf.service.CaseInfoService;
import com.sxf.service.CaseStatusService;
import com.sxf.service.CaseTargetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/19 10:02
 * @description：实现撰写人认领以及管理员审核
 * @version: 0.0.1$
 */
@Slf4j
@RestController
public class HqhController {


    @Autowired
    CaseInfoService caseInfoService;

    @Autowired
    CaseStatusService caseStatusService;

    @Autowired
    CaseTargetService caseTargetService;

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    /**
     * 撰写人认领专利
     *
     * @param caseId  专利id
     * @return -1 失败 0 成功
     */
    @PostMapping("/claimCase")
    public Result<Integer> claimCase(HttpServletRequest request, @RequestParam("caseId") String caseId) {
        CaseAccount caseAccount = (CaseAccount) request.getSession().getAttribute("CA");
        if(caseAccount == null){
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        int flag = -1;
        CaseInformation caseInformation = caseInfoService.getByCaseId(caseId);
        if(caseInformation == null){
            throw new GlobalException(CodeMsg.REQUEST_ILLEGAL);
        }
        caseInformation.setModifierName(caseAccount.getModifierName());
        caseInformation.setAccountId(caseAccount.getAccountId());
        CaseStatus caseStatus = caseStatusService.getById(caseInformation.getCaseId());
        CaseTarget caseTarget = caseTargetService.getTarget(caseId);
        caseTarget.setModifierName(caseAccount.getModifierName());
        caseStatus.setModifierName(caseAccount.getModifierName());
        try {
            caseStatusService.updateStatusModifierName(caseStatus);
            caseInfoService.updateCaseInfo(caseInformation);
            caseTargetService.updateTarget(caseTarget);
            flag = 0;
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.CHANGE_CS_FAILED);
        }
        return Result.success(flag);
    }

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
    @PostMapping("/upload_status")
    public int uploadChangeStatus(@RequestParam("caseId") String caseId, @RequestParam("fileType") int fileType, @RequestParam("file") MultipartFile file) {        CaseStatus caseStatus = caseStatusService.getById(caseId);
        if (caseStatus == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        return caseStatusService.upload(caseId,fileType,file);
    }


    /**
     * 显示流程历史对文件的操作
     *
     * @param caseId 专利id
     * @param ftype  文件类型
     * @return
     */
    @PostMapping("/history")
    public List<CaseFile> history(@RequestParam("caseId") String caseId, @RequestParam("fileType") int ftype) {
        List<CaseFile> caseFileList = caseStatusService.getCaseFileByCaseId(caseId);
        List<CaseFile> cl = new ArrayList<>();
        for (CaseFile cf : caseFileList) {
            if (cf.getFileType() == ftype) {
                cl.add(cf);
            }
        }
        return cl;
    }

    /**
     * 专利更改
     * @param caseInformation 前端传回的专利信息
     * @return 更改成功  更改出错
     */
    @PostMapping("/change")
    public Result<String> changeCase(@RequestBody CaseInformation caseInformation){
        CaseInformation ci = caseInfoService.getByCaseId(caseInformation.getCaseId());
        log.info(caseInformation.toString());
        caseInformation.setId(ci.getId());
        caseInformation.setIsCheck(ci.getIsCheck());
        caseInformation.setIsNew(ci.getIsUse());
        caseInformation.setIsUse(ci.getIsUse());
        caseInformation.setPatentType(ci.getPatentType());
        caseInformation.setAccountId(ci.getAccountId());
        caseInformation.setModifierName(ci.getModifierName());
        caseInformation.setApplyName(ci.getApplyName());
        caseInformation.setBatch(ci.getBatch());

        caseInformation.setCreateTime(ci.getCreateTime());
        caseInformation.setUpdateTime(new Date());
        try{
            caseInfoService.changeCaseInfo(caseInformation);
        }catch (Exception e){
            log.info("更改出错");
            throw new GlobalException(CodeMsg.CHANGE_CI_FAILED);
        }
        return Result.success("更改成功");
    }
    @GetMapping("/download/{caseId}")
    public Result<String> download(HttpServletResponse response, @PathVariable String caseId) throws IOException {
        if("".equals(caseId)){
            throw new GlobalException(CodeMsg.REQUEST_ILLEGAL);
        }
        CaseFile caseFile = caseStatusService.getSecondCheckFile(caseId);
        if(caseFile == null){
            throw new GlobalException(CodeMsg.DOWNLOAD_FAILED);
        }
        String[] fileNames = caseFile.getFilePath().split("\\.");
        String name = "交底书."+fileNames[1];
        String fileName = URLEncoder.encode(name, "utf-8");
        FileInputStream fis = new FileInputStream(new File(caseFile.getFilePath()));
        // 设置相关格式
        response.setContentType("application/force-download");
        // 设置下载后的文件名以及header
        response.addHeader("Content-disposition", "attachment;fileName=" + fileName);
        // 创建输出对象
        OutputStream os = response.getOutputStream();
        // 常规操作
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();
        return Result.success("文件："+fileName+"下载成功");
    }

}
