package com.sxf.utils;

import com.sxf.entity.CaseAccount;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/21 11:54
 * @description：ThreadLocal工具类
 * @version: $
 */
public class ThreadUtils {

    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    private static final ThreadLocal<CaseAccount> userHolder = new ThreadLocal<>();

    public static void setToken(String token){
        tokenHolder.set(token);
    }

    public static String getToken(){
        return tokenHolder.get();
    }

    public static void setUserHolder(CaseAccount user){
        userHolder.set(user);
    }

    public static CaseAccount getUserHolder(){
        return userHolder.get();
    }

    public static void remove(){
        tokenHolder.remove();
        userHolder.remove();
    }

}