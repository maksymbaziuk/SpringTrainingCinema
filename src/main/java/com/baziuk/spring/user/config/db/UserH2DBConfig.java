package com.baziuk.spring.user.config.db;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Maks on 10/12/16.
 */
@Configuration
@ComponentScan("com.baziuk.spring.user.data.db.dao")
public class UserH2DBConfig {
}
