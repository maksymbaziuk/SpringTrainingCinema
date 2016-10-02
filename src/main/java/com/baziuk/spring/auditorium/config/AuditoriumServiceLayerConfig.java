package com.baziuk.spring.auditorium.config;

import com.baziuk.spring.auditorium.config.inmemory.AuditoriumDataLayerConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Maks on 9/30/16.
 */
@Configuration
@ComponentScan("com.baziuk.spring.auditorium.service")
@Import({AuditoriumDataLayerConfig.class})
public class AuditoriumServiceLayerConfig {
}
