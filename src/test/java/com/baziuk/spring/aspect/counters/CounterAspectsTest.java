package com.baziuk.spring.aspect.counters;

import com.baziuk.spring.aspect.config.AspectCountersConfig;
import com.baziuk.spring.aspect.dao.CounterDAO;
import com.baziuk.spring.auditorium.config.AuditoriumServiceLayerConfig;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Maks on 10/2/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        AspectCountersConfig.class,
        AuditoriumServiceLayerConfig.class,
        EventServiceLayerConfig.class,
        UserServiceLayerConfig.class,
        BookingServiceLayerConfig.class,
        DiscountServiceLayerConfig.class
})
public class CounterAspectsTest {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private CounterDAO counterDAO;

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
    public void accessEventByNameCounter(){
        Map<String, Integer> accessEventByName = counterDAO.getAccessEventByName();
        assertTrue(accessEventByName.isEmpty());
        eventService.getEventByName(event.getName());
        eventService.getEventByName(event.getName());
        accessEventByName = counterDAO.getAccessEventByName();
        assertEquals(2, accessEventByName.get(event.getName()).intValue());
    }

    @Test
    @DirtiesContext
    public void bookingTicketsForEventCounter(){
        Map<Event, Integer> bookingTicketsCounter = counterDAO.getBookEventTicketsCounter();
        assertTrue(bookingTicketsCounter.isEmpty());
        bookingService.bookTickets(event, show, user, 20);
        bookingTicketsCounter = counterDAO.getBookEventTicketsCounter();
        assertFalse(bookingTicketsCounter.isEmpty());
        assertEquals(1, bookingTicketsCounter.get(event).intValue());
    }

    @Test
    @DirtiesContext
    public void priceForEventCounter(){
        Map<Event, Integer> bookingTicketsCounter = counterDAO.getPriceForEventCounter();
        assertTrue(bookingTicketsCounter.isEmpty());
        bookingService.getTicketsPrice(event, show, user, 20);
        bookingTicketsCounter = counterDAO.getPriceForEventCounter();
        assertFalse(bookingTicketsCounter.isEmpty());
        assertEquals(1, bookingTicketsCounter.get(event).intValue());
    }

    @Test
    @DirtiesContext
    public void discountAppliedCounter(){
        Map<String, Integer> bookingTicketsCounter = counterDAO.getDiscountNameCounter();
        assertTrue(bookingTicketsCounter.isEmpty());
        user = userService.getUserById(2).get();
        bookingService.bookTickets(event, show, user, 20,21,22,23);
        bookingTicketsCounter = counterDAO.getDiscountNameCounter();
        assertFalse(bookingTicketsCounter.isEmpty());
        assertEquals(1, bookingTicketsCounter.get("eachNTicketDiscount").intValue());
    }

    @Test
    @DirtiesContext
    public void userDiscountCounter(){
        Map<Long, Map<String, Integer>> userDiscountCounter = counterDAO.getUserDiscountCounter();
        assertTrue(userDiscountCounter.isEmpty());
        user = userService.getUserById(2).get();
        bookingService.bookTickets(event, show, user, 20,21,22,23);
        userDiscountCounter = counterDAO.getUserDiscountCounter();
        assertFalse(userDiscountCounter.isEmpty());
        assertFalse(userDiscountCounter.get(user.getId()) == null);
        assertEquals(1, userDiscountCounter.get(user.getId()).get("eachNTicketDiscount").intValue());
    }
}