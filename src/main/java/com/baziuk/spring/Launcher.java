package com.baziuk.spring;

import com.baziuk.spring.auditorium.config.AuditoriumServiceLayerConfig;
import com.baziuk.spring.auditorium.service.AuditoriumService;
import com.baziuk.spring.booking.config.BookingServiceLayerConfig;
import com.baziuk.spring.discount.config.DiscountServiceLayerConfig;
import com.baziuk.spring.events.config.EventServiceLayerConfig;
import com.baziuk.spring.events.service.EventService;
import com.baziuk.spring.user.config.UserServiceLayerConfig;
import com.baziuk.spring.user.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Maks on 9/30/16.
 */
public class Launcher {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                AuditoriumServiceLayerConfig.class,
                EventServiceLayerConfig.class,
                UserServiceLayerConfig.class,
                BookingServiceLayerConfig.class,
                DiscountServiceLayerConfig.class);
        AuditoriumService auditoriumService = ctx.getBean(AuditoriumService.class);
        System.out.println(auditoriumService.getAllAuditoriums());
        EventService eventService = ctx.getBean(EventService.class);
        System.out.println(eventService.getAllEvents());
        UserService userService = ctx.getBean(UserService.class);
        System.out.println(userService.getAllRegisteredUsers());
    }
}
