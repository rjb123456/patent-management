package com.sxf.service;

import com.sxf.dao.AccountDao;
import com.sxf.entity.CaseAccount;
import com.sxf.exception.GlobalException;
import com.sxf.result.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @author huang_qh@suixingpay.com
 */
@Service
public class LoginService {

    @Autowired
    private AccountDao accountDao;

    public CaseAccount login(HttpServletRequest request, String accountId, String password) {

        if (StringUtils.isBlank(accountId)) {
            throw new GlobalException(CodeMsg.MOBILE_EMPTY);
        }

        if (StringUtils.isBlank(password)) {
            throw new GlobalException(CodeMsg.PASSWORD_EMPTY);
        }

        CaseAccount caseAccount = accountDao.getByAccountId(accountId);
        if (caseAccount == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        if (!password.equals(caseAccount.getPassword())) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        request.getSession(true).setAttribute("CA",caseAccount);

        return caseAccount;
    }


    public String register(String accountId, String password, String modifierName) {
        if (StringUtils.isBlank(accountId)) {
            throw new GlobalException(CodeMsg.MOBILE_EMPTY);
        }
        if (StringUtils.isBlank(password)) {
            throw new GlobalException(CodeMsg.PASSWORD_EMPTY);
        }
        if("admin".equals(accountId)){
            throw new GlobalException(CodeMsg.IS_NOT_ADMIN);
        }
        CaseAccount caseAccount = null;
        caseAccount = accountDao.getByAccountId(accountId);
        if (caseAccount == null) {
            System.out.println("用户不存在，可以创建");
            caseAccount = new CaseAccount();
            caseAccount.setAccountId(accountId);
            caseAccount.setPassword(password);
            caseAccount.setCreateTime(new Date());
            caseAccount.setType(1);
            caseAccount.setModifierName(modifierName);
            accountDao.insertCaseAccount(caseAccount);
            return CodeMsg.REGISTER_SUCCESS.getMsg();
        } else {
            throw new GlobalException(CodeMsg.MOBILE_EXISTED);
        }
    }
}

