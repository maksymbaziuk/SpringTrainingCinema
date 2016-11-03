package com.baziuk.spring.events.config.db;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Maks on 10/11/16.
 */
@Configuration
@ComponentScan("com.baziuk.spring.events.data.db.dao")
public class EventH2DBConfig {
}
