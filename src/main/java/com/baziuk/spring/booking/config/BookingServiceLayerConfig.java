package com.baziuk.spring.booking.config;

import com.baziuk.spring.booking.config.db.BookingH2DBConfig;
import com.baziuk.spring.booking.config.inmemory.BookingDataLayerConfig;
import com.baziuk.spring.booking.dao.BookingDAO;
import com.baziuk.spring.booking.service.BookingCinemaService;
import com.baziuk.spring.booking.service.BookingService;
import com.baziuk.spring.data.H2DBConfig;
import com.baziuk.spring.discount.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

/**
 * Created by Maks on 10/2/16.
 */
@Configuration
@ComponentScan("com.baziuk.spring.booking.service")
@PropertySource("classpath:config/booking-config.properties")
@Import(BookingH2DBConfig.class)
public class BookingServiceLayerConfig {

    @Autowired
    public Environment env;

    @Bean
    public BookingService bookingService(DiscountService discountService, BookingDAO bookingDAO){
        BookingCinemaService cinemaService = new BookingCinemaService();
        cinemaService.setDiscountService(discountService);
        cinemaService.setBookingDAO(bookingDAO);
        cinemaService.setVipMultiplier(Double.parseDouble(env.getRequiredProperty("vipMultiplier")));
        cinemaService.setRankMultiplier(Double.parseDouble(env.getRequiredProperty("rankMultiplier")));
        return cinemaService;
    }

}
