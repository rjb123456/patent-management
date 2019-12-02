package com.sxf.controller;

import com.sxf.entity.CaseAccount;
import com.sxf.exception.GlobalException;
import com.sxf.result.CodeMsg;
import com.sxf.result.Result;
import com.sxf.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/18 17:11
 * @description：登录操作
 * @version: 0.0.1
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

//    @SystemLog(methods = "登陆", module = "登陆")
    @PostMapping("/login")
    public Result<CaseAccount> login(HttpServletRequest request, @RequestBody CaseAccount caseAccount, HttpServletResponse response) {
        if (caseAccount == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        log.info(caseAccount.getAccountId()+"############"+caseAccount.getPassword());
        CaseAccount token = loginService.login(request, caseAccount.getAccountId(), caseAccount.getPassword());
        return Result.success(token);
    }

//    @SystemLog(methods = "注册", module = "用户注册")
    @PostMapping("/register")
    public Result<String> register(@RequestBody CaseAccount caseAccount) {
        if (caseAccount == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String token = loginService.register(caseAccount.getAccountId(), caseAccount.getPassword(), caseAccount.getModifierName());
        return Result.success(token);
    }

//    @SystemLog(methods = "安全退出", module = "安全退出")
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Enumeration em = request.getSession().getAttributeNames();
        while(em.hasMoreElements()){
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        System.out.println("登出成功");
        return "logout";
    }
}