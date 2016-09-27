package com.baziuk.spring;

import com.baziuk.spring.auditorium.bean.Auditorium;
import com.baziuk.spring.auditorium.service.AuditoriumService;
import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.booking.dao.BookingDAO;
import com.baziuk.spring.booking.service.BookingService;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.Show;
import com.baziuk.spring.events.dao.EventDAO;
import com.baziuk.spring.events.dao.EventInMemoryDAO;
import com.baziuk.spring.user.bean.User;
import com.baziuk.spring.user.service.UserService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Maks on 9/19/16.
 */
public class Launcher {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("config/application-config.xml");
        AuditoriumService auditoriumService = ctx.getBean(AuditoriumService.class);
        Collection<Auditorium> auditoriums = auditoriumService.getAllAuditoriums();

        UserService userService = ctx.getBean(UserService.class);
        User user = userService.getUserById(1).get();

        EventInMemoryDAO eventInMemoryDAO = (EventInMemoryDAO) ctx.getBean("eventDAO");
        BookingService bookingService = ctx.getBean(BookingService.class);
        Event event = eventInMemoryDAO.get(1);
        Show show =  eventInMemoryDAO.getShowById(1l);

        BookingDAO bookingDAO = ctx.getBean(BookingDAO.class);
        Collection<Ticket> bookedTickets = bookingDAO.getAll();
        System.out.println("Booked");
        for (Ticket ticket : bookedTickets) {
            System.out.println(ticket);
        }
        System.out.println("Created");
        LocalDate day = LocalDate.now();
        System.out.println("-----------------");
        System.out.println(day.getDayOfYear());
        System.out.println("-----------------");
        double price = bookingService.getTicketsPrice(event, show, user, 1, 2, 3, 4,5, 6, 7,8,9,11,12,13,14,15);
        Collection<Ticket> tickets = bookingService.bookTickets(event, show, user, 1, 2, 3, 4,5, 6, 7,8,9,11,12,13,14,15);
        double priceAfterBooking = 0;
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
            priceAfterBooking += ticket.getPrice();
        }

        System.out.println("calculatedPrice=" + price);
        System.out.println("priceAfterBooking=" + priceAfterBooking);

        bookedTickets = bookingDAO.getAll();
        System.out.println("After booking");
        for (Ticket ticket : bookedTickets) {
            System.out.println(ticket);
        }
    }

}
