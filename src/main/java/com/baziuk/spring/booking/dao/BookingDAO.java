package com.baziuk.spring.booking.dao;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.common.dao.CrudDAO;
import com.baziuk.spring.events.bean.Event;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by Maks on 9/25/16.
 */
public interface BookingDAO extends CrudDAO<Ticket>{

    Collection<Ticket> getTicketsForEvent(Event event, LocalDateTime from, LocalDateTime to);

}
