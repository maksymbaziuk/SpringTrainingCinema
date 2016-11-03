package com.baziuk.spring.aspect.config;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by Maks on 10/2/16.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.baziuk.spring.aspect.counters", "com.baziuk.spring.aspect.data.db.dao"})
public class AspectCountersConfig {

    public static void main(String[] args) {
        System.out.println(BooleanUtils.toBoolean("yes"));
        System.out.println(BooleanUtils.toBoolean("YES"));
        System.out.println(BooleanUtils.toBoolean("no"));
        System.out.println(BooleanUtils.toBoolean("NO"));
        System.out.println(BooleanUtils.toBoolean((String)null));
    }
}
