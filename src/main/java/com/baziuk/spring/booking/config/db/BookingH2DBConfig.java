package com.baziuk.spring.booking.config.db;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Maks on 10/12/16.
 */
@Configuration
@ComponentScan("com.baziuk.spring.booking.data.db.dao")
public class BookingH2DBConfig {
}
