package com.baziuk.spring.booking.data.inmemory.dao;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.booking.dao.BookingDAO;
import com.baziuk.spring.data.JSONDataPopulator;
import com.baziuk.spring.events.bean.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maks on 9/25/16.
 */
@Repository("bookingDAO")
public class BookingInMemoryDAO implements BookingDAO {

    private static long curMaxTicketId = 1;

    @Autowired
    @Qualifier("ticketJSONDataPopulator")
    private JSONDataPopulator dataPopulator;

    private List<Ticket> tickets;

    @Override
    public Ticket create(Ticket item) {
        item.setId(curMaxTicketId++);
        tickets.add(item);
        return item;
    }

    @Override
    public Ticket update(Ticket item) {
        // Do nothing, it's in memory
        return item;
    }

    @Override
    public boolean remove(Ticket item) {
        return tickets.remove(item);
    }

    @Override
    public Ticket get(long id) {
        return tickets.stream().filter(ticket -> ticket.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Collection<Ticket> getAll() {
        return new ArrayList<>(tickets);
    }

    @Override
    public Collection<Ticket> getTicketsForEvent(Event event, LocalDateTime from, LocalDateTime to) {
        return tickets.stream().filter(ticket -> ticket.getEvent().getId() == event.getId()
                && ticket.getShow().getStart().isAfter(from)
                && ticket.getShow().getEnd().isBefore(to)).collect(Collectors.toList());
    }

    @PostConstruct
    public void initWithData() throws IOException {
        Collection<Ticket> dataTickets = dataPopulator.getData(new Ticket[0].getClass());
        dataTickets.forEach(ticket -> {
            if (curMaxTicketId < ticket.getId()){
                curMaxTicketId = ticket.getId();
            }
        });
        tickets = new ArrayList<>(dataTickets);
    }

    public JSONDataPopulator getDataPopulator() {
        return dataPopulator;
    }

    public void setDataPopulator(JSONDataPopulator dataPopulator) {
        this.dataPopulator = dataPopulator;
    }
}
