package com.sxf.utils;

import java.util.UUID;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/20 11:55
 * @description：唯一身份标识
 * @version: $
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
