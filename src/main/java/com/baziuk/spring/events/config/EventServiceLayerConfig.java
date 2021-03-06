package com.baziuk.spring.events.config;

import com.baziuk.spring.events.config.db.EventH2DBConfig;
import com.baziuk.spring.events.config.inmemory.EventDataLayerConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Maks on 9/30/16.
 */
@Configuration
@ComponentScan("com.baziuk.spring.events.service")
@Import(EventH2DBConfig.class)
public class EventServiceLayerConfig {
}
