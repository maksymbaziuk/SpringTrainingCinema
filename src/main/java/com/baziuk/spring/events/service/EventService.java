package com.baziuk.spring.events.service;

import com.baziuk.spring.events.bean.Event;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Maks on 9/20/16.
 */
public interface EventService {

    Collection<Event> getNextEvents(LocalDateTime to);
    Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to);
    Event saveEvent(Event item);
    Event createEvent(Event item);
    boolean removeEvent(Event item);
    Optional<Event> getEventById(long id);
    Optional<Event> getEventByName(String name);
    Collection<Event> getAllEvents();

}
