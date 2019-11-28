package com.sxf.intercepter;

import com.sxf.entity.CaseAccount;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/25 14:10
 * @description：登录拦截器
 */
public class WebInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag =true;
        CaseAccount caseAccount=(CaseAccount) request.getSession().getAttribute("CA");
        if(caseAccount == null){
            response.sendRedirect("login");
            System.out.println("还未登录");
            flag = false;
        }else{
            System.out.println("已经登录");
            flag = true;
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
