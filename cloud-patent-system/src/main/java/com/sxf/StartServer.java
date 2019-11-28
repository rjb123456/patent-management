package com.sxf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author ：huang_qh@suixingpay.com
 * @date ：Created in 2019/11/18 16:04
 */

@SpringBootApplication
//@EnableAspectJAutoProxy
public class StartServer {
    public static void main(String[] args) {
        SpringApplication.run(StartServer.class);
    }
}
