package com.baziuk.spring;

import com.baziuk.spring.web.SecurityConfig;
import com.baziuk.spring.web.WebAppConfig;
import org.springframework.boot.SpringApplication;

/**
 * Created by Maks on 11/6/16.
 */
public class AppRunner {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(new Object[]{WebAppConfig.class, SecurityConfig.class}, args);
    }
}
