package com.baziuk.spring.user.config;

import com.baziuk.spring.user.config.db.UserH2DBConfig;
import com.baziuk.spring.user.config.inmemory.UserDataLayerConfig;
import com.baziuk.spring.user.service.CinemaUserService;
import com.baziuk.spring.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Maks on 9/30/16.
 */
@Configuration
@ComponentScan("com.baziuk.spring.user.service")
@Import(UserH2DBConfig.class)
public class UserServiceLayerConfig {
}
