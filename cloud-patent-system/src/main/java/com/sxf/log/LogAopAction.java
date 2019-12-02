package com.sxf.log;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAopAction {

    // 获取开始时间
    private long BEGIN_TIME;

    // 获取结束时间
    private long END_TIME;

    // 定义本次log实体
    private LogModel logModel = new LogModel();

    @Pointcut("execution(* com.sxf.controller.*Controller.*(..))")
    private void controllerMethodAspect() {
    }

    /**
     * 方法开始执行
     */
    @Before("controllerMethodAspect()")
    public void doBefore() {
        BEGIN_TIME = new Date().getTime();
        System.out.println("aop--开始");
    }

    /**
     * 方法结束执行
     */
    @After("controllerMethodAspect()")
    public void after() {
        END_TIME = new Date().getTime();
        System.out.println("aop--结束");
        System.out.println("写入日志到文件中");
        WriteLog writeLog = new WriteLog();
        writeLog.WriteLogByDate(logModel.toString());
    }


    /**
     * 方法结束执行后的操作
     */
    @AfterReturning("controllerMethodAspect()")
    public void doAfter() {

        if (logModel.getState() == 1 || logModel.getState() == -1) {
            logModel.setActionTime(END_TIME - BEGIN_TIME);
            logModel.setGmtCreate(new Date(BEGIN_TIME));
            System.out.println("aop--将logModel="+logModel +"执行成功");
        } else {
            System.out.println(logModel);
            System.out.println("aop-->>>>>>>>执行失败");
        }
    }

    /**
     * 方法有异常时的操作
     */
    @AfterThrowing("controllerMethodAspect()")
    public void doAfterThrow() {
        System.out.println("aop--例外通知-----------------------------------");
    }

    /**
     * 方法执行
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("controllerMethodAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 日志实体对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        // 获取当前登陆用户信息
        String uid = request.getParameter("uid");
        if (uid == null) {
            logModel.setLoginAccount("—— ——");
        } else {
            logModel.setLoginAccount(uid);
        }

        // 拦截的实体类，就是当前正在执行的controller
        Object target = pjp.getTarget();
        // 拦截的方法名称。当前正在执行的方法
        String methodName = pjp.getSignature().getName();
        logModel.setMethodJuti(methodName);

        // 拦截的方法参数
        Object[] args = pjp.getArgs();
        logModel.setParameter(args);
        // 拦截的放参数类型
        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Class[] parameterTypes = msig.getMethod().getParameterTypes();

        Object object = null;

        Method method = null;
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);
            System.out.println(method);
        } catch (NoSuchMethodException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SecurityException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        if (null != method) {
            // 判断是否包含自定义的注解，说明一下这里的SystemLog就是我自己自定义的注解
            if (method.isAnnotationPresent(SystemLog.class)) {
                SystemLog systemlog = method.getAnnotation(SystemLog.class);
                logModel.setModule(systemlog.module());
                logModel.setMethod(systemlog.methods());
                logModel.setLoginIp(getIp(request));
                logModel.setActionUrl(request.getRequestURI());

                try {
                    object = pjp.proceed();
                    logModel.setDescription("执行成功");
                    logModel.setState((short) 1);
                } catch (Throwable e) {
                    // TODO Auto-generated catch block
                    logModel.setDescription("执行失败");
                    logModel.setState((short) -1);
                }
            } else {// 没有包含注解
                object = pjp.proceed();
                logModel.setDescription("此操作不包含注解");
                logModel.setState((short) 0);
            }
        } else { // 不需要拦截直接执行

            object = pjp.proceed();
            logModel.setDescription("不需要拦截直接执行");
            logModel.setState((short) 0);
            //logModel.setObject(object);
        }

        return object;
    }

    /**
     * 获取ip地址
     *
     * @param request
     * @return
     */
    private String getIp(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }



}
