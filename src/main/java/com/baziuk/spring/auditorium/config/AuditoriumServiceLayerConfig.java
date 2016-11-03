package com.baziuk.spring.auditorium.config;

import com.baziuk.spring.auditorium.config.db.AuditoriumDBConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Maks on 9/30/16.
 */
@Configuration
@ComponentScan("com.baziuk.spring.auditorium.service")
@Import({AuditoriumDBConfig.class})
public class AuditoriumServiceLayerConfig {
}
