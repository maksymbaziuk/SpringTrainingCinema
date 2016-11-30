package com.baziuk.spring.booking.service;


import com.baziuk.spring.auditorium.config.AuditoriumServiceLayerConfig;
import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.booking.config.BookingServiceLayerConfig;
import com.baziuk.spring.data.H2DBConfig;
import com.baziuk.spring.discount.config.DiscountServiceLayerConfig;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.Show;
import com.baziuk.spring.events.config.EventServiceLayerConfig;
import com.baziuk.spring.events.service.EventService;
import com.baziuk.spring.user.bean.User;
import com.baziuk.spring.user.config.UserServiceLayerConfig;
import com.baziuk.spring.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Maks on 9/27/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        H2DBConfig.class,
        AuditoriumServiceLayerConfig.class,
        EventServiceLayerConfig.class,
        UserServiceLayerConfig.class,
        BookingServiceLayerConfig.class,
        DiscountServiceLayerConfig.class})
@DirtiesContext
public class BookingServiceTest {

    private static final long VALID_USER_ID = 1;
    private static final long NITEM_DISCOUNT_USER_ID = 2;
    private static final long VALID_LOW_RANK_EVENT_ID = 1;
    private static final long VALID_HIGH_RANK_EVENT_ID = 2;

    @Autowired
    private BookingService service;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;

    private double vipSitMultiplier = 2;

    private double rankSitMultiplier = 1.2;

    private Event event;
    private Show show;
    private User user;

    @Before
    public void init(){
        user = userService.getUserById(VALID_USER_ID).get();
        event = eventService.getEventById(VALID_LOW_RANK_EVENT_ID).get();
        show = event.getSchedule().first();
    }

    @Test
    public void priceOneUsualSitNoDiscount(){
        double price = service.getTicketsPrice(event, show, user, 15);
        assertEquals(event.getPrice(), price, 0);
    }

    @Test
    public void priceTwoUsualSitsNoDiscount(){
        double price = service.getTicketsPrice(event, show, user, 15, 16);
        assertEquals(event.getPrice() * 2, price, 0);
    }

    @Test
    public void priceOneVipSitNoDiscount(){
        double price = service.getTicketsPrice(event, show, user, 1);
        assertEquals(event.getPrice() * 2, price, 0);
    }

    @Test
    public void priceTwoVipSitsNoDiscount(){
        double price = service.getTicketsPrice(event, show, user, 1 ,2);
        assertEquals(event.getPrice() * 2 * 2, price, 0);
    }

    @Test
    public void priceOneVipSitEachDiscount(){
        user = userService.getUserById(NITEM_DISCOUNT_USER_ID).get();
        double price = service.getTicketsPrice(event, show, user, 1);
        assertEquals(event.getPrice() * vipSitMultiplier * 0.5, price, 0);
    }

    @Test
    public void priceOneUsualSitEachDiscount(){
        user = userService.getUserById(NITEM_DISCOUNT_USER_ID).get();
        double price = service.getTicketsPrice(event, show, user, 29);
        assertEquals(event.getPrice() * 0.5, price, 0);
    }

    @Test
    public void priceOneUsualSitHighRankEachDiscount(){
        event = eventService.getEventById(VALID_HIGH_RANK_EVENT_ID).get();
        show = event.getSchedule().first();
        user = userService.getUserById(NITEM_DISCOUNT_USER_ID).get();
        double price = service.getTicketsPrice(event, show, user, 29);
        assertEquals(event.getPrice() * rankSitMultiplier * 0.5, price, 0);
    }

    @Test
    public void priceOneVipSitHighRankEachDiscount(){
        event = eventService.getEventById(VALID_HIGH_RANK_EVENT_ID).get();
        show = event.getSchedule().first();
        user = userService.getUserById(NITEM_DISCOUNT_USER_ID).get();
        double price = service.getTicketsPrice(event, show, user, 1);
        assertEquals(event.getPrice() * vipSitMultiplier * rankSitMultiplier * 0.5, price, 0);
    }

    @Test
    public void priceOneVipSitHighRankNoDiscount(){
        event = eventService.getEventById(VALID_HIGH_RANK_EVENT_ID).get();
        show = event.getSchedule().first();
        user = userService.getUserById(VALID_USER_ID).get();
        double price = service.getTicketsPrice(event, show, user, 1);
        assertEquals(event.getPrice() * vipSitMultiplier * rankSitMultiplier, price, 0);
    }

    @Test
    @DirtiesContext
    public void priceOneUsualSitLowRankBirthdayDiscount(){
        event = eventService.getEventById(VALID_LOW_RANK_EVENT_ID).get();
        show = event.getSchedule().first();
        user = userService.getUserById(VALID_USER_ID).get();
        user.setBirthday(LocalDate.now().minusYears(20));
        userService.saveUser(user);
        double price = service.getTicketsPrice(event, show, user, 29);
        assertEquals(event.getPrice() * 0.95, price, 0);
    }

    @Test
    @DirtiesContext
    public void priceOneUsualSitLowRankBirthdayDiscountNewUser(){
        event = eventService.getEventById(VALID_LOW_RANK_EVENT_ID).get();
        show = event.getSchedule().first();
        user = new User();
        user.setBirthday(LocalDate.now().minusYears(20));
        user.setEmail("qweasdzxc@gmail.com");
        user.setBoughtTickets(new ArrayList<>());
        userService.saveUser(user);
        double price = service.getTicketsPrice(event, show, user, 29);
        assertEquals(event.getPrice() * 0.95, price, 0);
    }

    @Test
    public void priceTenUsualSitLowRankBirthdayDiscountEachDiscountNewUser(){
        event = eventService.getEventById(VALID_LOW_RANK_EVENT_ID).get();
        show = event.getSchedule().first();
        user = new User();
        user.setBirthday(LocalDate.now().minusYears(20));
        user.setEmail("qweasdzxc@gmail.com");
        user.setBoughtTickets(new ArrayList<>());
        userService.saveUser(user);
        double price = service.getTicketsPrice(event, show, user, 16,17,18,19,20,21,22,23,24,25);
        // 10 tickets, 1 discounted for 50%
        assertEquals(event.getPrice() * 10 - event.getPrice() * 0.5, price, 0);
    }

    @Test
    public void notAvailableForPurchase(){
        boolean available = service.isAvailable(event, show, 10);
        assertFalse(available);
    }

    @Test
    public void availableForPurchase(){
        show.setStart(LocalDateTime.now().plusDays(2));
        show.setEnd(LocalDateTime.now().plusDays(5));
        boolean available = service.isAvailable(event, show, 15);
        assertTrue(available);
    }

    @Test
    @DirtiesContext
    public void bookOneTicket(){
        show.setStart(LocalDateTime.now().plusDays(2));
        show.setEnd(LocalDateTime.now().plusDays(5));
        Collection<Ticket> tickets = service.bookTickets(event, show, user, 15);
        assertEquals(1, tickets.size());
    }

    @Test
    @DirtiesContext
    public void bookUnavailableTicket(){
        Collection<Ticket> tickets = service.bookTickets(event, show, user, 10);
        assertEquals(0, tickets.size());
    }

    @Test
    @DirtiesContext
    public void bookOneTicketCheckUserTickets(){
        show.setStart(LocalDateTime.now().plusDays(2));
        show.setEnd(LocalDateTime.now().plusDays(5));
        Collection<Ticket> tickets = service.bookTickets(event, show, user, 15);
        assertEquals(1, tickets.size());
        User checkUser = userService.getUserById(user.getId()).get();
        assertEquals(1, checkUser.getBoughtTickets().size());
    }
}
