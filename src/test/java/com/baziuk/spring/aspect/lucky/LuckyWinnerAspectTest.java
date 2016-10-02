package com.baziuk.spring.aspect.lucky;

import com.baziuk.spring.auditorium.config.AuditoriumServiceLayerConfig;
import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.booking.config.BookingServiceLayerConfig;
import com.baziuk.spring.booking.service.BookingService;
import com.baziuk.spring.discount.config.DiscountServiceLayerConfig;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.Show;
import com.baziuk.spring.events.config.EventServiceLayerConfig;
import com.baziuk.spring.events.service.EventService;
import com.baziuk.spring.user.bean.User;
import com.baziuk.spring.user.config.UserServiceLayerConfig;
import com.baziuk.spring.user.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

/**
 * Created by Maks on 10/2/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LuckyWinnerAspectTestConfig.class})
public class LuckyWinnerAspectTest {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;

    private Event event;
    private Show show;
    private User user;

    @Before
    public void init(){
        user = userService.getUserById(1).get();
        event = eventService.getEventById(1).get();
        show = event.getSchedule().first();
    }

    @Test
    @DirtiesContext
    public void luckyBastard(){
        Collection<Ticket> tickets = bookingService.bookTickets(event, show, user, 15,16,17,18,19);
        tickets.forEach(ticket -> Assert.assertTrue(ticket.getPrice() == 0));
    }

}

@Configuration
@EnableAspectJAutoProxy
@Import({AuditoriumServiceLayerConfig.class,
        EventServiceLayerConfig.class,
        UserServiceLayerConfig.class,
        BookingServiceLayerConfig.class,
        DiscountServiceLayerConfig.class})
class LuckyWinnerAspectTestConfig {

    @Bean
    public LuckyWinnerAspect luckyWinnerAspect(){
        LuckyWinnerAspect luckyWinnerAspect = new LuckyWinnerAspect(){
            @Override
            protected boolean checkForLuck() {
                return true;
            }
        };
        return luckyWinnerAspect;
    }
}
