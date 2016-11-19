package com.baziuk.spring.booking.service;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.Show;
import com.baziuk.spring.user.bean.User;

import java.util.Collection;

/**
 * Created by Maks on 9/20/16.
 */
public interface BookingService {

    double getTicketsPrice(Event event, Show show, User user, int... seats);
    Collection<Ticket> bookTickets(Event event, Show show, User user, int... seats);
    boolean isAvailable(Event event, Show show, int... seats);
    Ticket getTicketById(long ticketId);

}
