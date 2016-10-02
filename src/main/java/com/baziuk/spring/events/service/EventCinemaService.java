package com.baziuk.spring.events.service;

import com.baziuk.spring.auditorium.service.AuditoriumService;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.Show;
import com.baziuk.spring.events.dao.EventDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by Maks on 9/25/16.
 */
@Service("eventService")
public class EventCinemaService implements EventService {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private AuditoriumService auditoriumService;

    @Override
    public Collection<Event> getNextEvents(LocalDateTime to) {
        if (to != null && to.isAfter(LocalDateTime.now())){
            Collection<Event> betweenDates = getForDateRange(LocalDateTime.now(), to);
            return !CollectionUtils.isEmpty(betweenDates) ? betweenDates : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to) {
        return eventDAO.getForDateRange(from, to);
    }

    @Override
    public Event saveEvent(Event item) {
        if (!isEventValid(item)){
            throw new IllegalArgumentException("Event is invalid!");
        }
        if (item.getId() == 0){
            return eventDAO.create(item);
        }
        return eventDAO.update(item);
    }

    @Override
    public Event createEvent(Event item) {
        if (!isEventValid(item)){
            throw new IllegalArgumentException("Event is invalid!");
        }
        return eventDAO.create(item);
    }

    @Override
    public boolean removeEvent(Event item) {
        return eventDAO.remove(item);
    }

    @Override
    public Optional<Event> getEventById(long id) {
        return Optional.ofNullable(eventDAO.get(id));
    }

    @Override
    public Optional<Event> getEventByName(String name) {
        return Optional.ofNullable(eventDAO.getByName(name));
    }

    @Override
    public Collection<Event> getAllEvents() {
        return eventDAO.getAll();
    }

    private boolean isEventValid(Event event){
        return event.getPrice() > 0
                && event.getSchedule() != null && !event.getSchedule().isEmpty()
                && StringUtils.isNoneBlank(event.getName())
                && event.getSchedule().stream().allMatch(show -> isShowValid(show));
    }

    private boolean isShowValid(Show show){
        return show.getAuditorium() != null
                && auditoriumService.getAuditoriumById(show.getAuditorium().getId()).isPresent()
                && show.getStart() != null && show.getEnd() != null && show.getEnd().isAfter(show.getStart());
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public AuditoriumService getAuditoriumService() {
        return auditoriumService;
    }

    public void setAuditoriumService(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
    }
}
