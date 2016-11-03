package com.baziuk.spring.auditorium.config.db;

import com.baziuk.spring.data.H2DBConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Maks on 10/10/16.
 */
@Configuration
@ComponentScan("com.baziuk.spring.auditorium.data.db.dao")
public class AuditoriumDBConfig {
}
