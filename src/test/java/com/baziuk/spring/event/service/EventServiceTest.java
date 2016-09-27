package com.baziuk.spring.event.service;

import com.baziuk.spring.auditorium.bean.Auditorium;
import com.baziuk.spring.auditorium.service.AuditoriumService;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.Show;
import com.baziuk.spring.events.service.EventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * Created by Maks on 9/27/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/test-application-config.xml"})
public class EventServiceTest {

    private static final String EXISTEN_EVENT_NAME = "Ghosts";
    private static final String NON_EXISTEN_EVENT_NAME = "testEvent";
    private static final String VALID_EVENT_NAME = "validEvent";
    private static final Long EXISTEN_EVENT_ID = 1l;
    private static final Long NON_EXISTEN_EVENT_ID = 111111l;

    @Autowired
    public EventService service;

    @Autowired
    private AuditoriumService auditoriumService;

    @Test
    public void getEventById(){
        Optional<Event> event = service.getEventById(EXISTEN_EVENT_ID);
        assertTrue(event.isPresent());
        assertEquals(EXISTEN_EVENT_ID.longValue(), event.get().getId());
    }

    @Test
    public void getEventByFakeId(){
        Optional<Event> event = service.getEventById(NON_EXISTEN_EVENT_ID);
        assertFalse(event.isPresent());
    }

    @Test
    public void getEventByZeroId(){
        Optional<Event> event = service.getEventById(Long.valueOf(0));
        assertFalse(event.isPresent());
    }

    @Test
    public void getEventByName(){
        Optional<Event> event = service.getEventByName(EXISTEN_EVENT_NAME);
        assertTrue(event.isPresent());
        assertEquals(EXISTEN_EVENT_NAME, event.get().getName());
    }

    @Test
    public void getEventByNullName(){
        Optional<Event> event = service.getEventByName(null);
        assertFalse(event.isPresent());
    }

    @Test
    public void getEventByEmptyName(){
        Optional<Event> event = service.getEventByName("");
        assertFalse(event.isPresent());
    }

    @Test
    public void getEventByNonExistingName(){
        Optional<Event> event = service.getEventByName(NON_EXISTEN_EVENT_NAME);
        assertFalse(event.isPresent());
    }

    @Test
    public void getAllEvents(){
        Collection<Event> events = service.getAllEvents();
        assertEquals(4, events.size());
    }

    @Test
    @DirtiesContext
    public void removeEvent(){
        Optional<Event> event = service.getEventById(EXISTEN_EVENT_ID);
        boolean removed = service.removeEvent(event.get());
        assertTrue(removed);
        Collection<Event> events = service.getAllEvents();
        assertFalse(events.contains(event));
    }

    @Test
    public void removeNewEvent(){
        int legthBefore = service.getAllEvents().size();
        Event event = createValidEvent();
        boolean result = service.removeEvent(event);
        assertTrue(result);
        int lengthAfter = service.getAllEvents().size();
        assertEquals(legthBefore, lengthAfter);
    }

    @Test
    public void removeNull(){
        // Removing nothing - ok, "nothing" removed successfully :)
        int legthBefore = service.getAllEvents().size();
        boolean result = service.removeEvent(null);
        assertTrue(result);
        int lengthAfter = service.getAllEvents().size();
        assertEquals(legthBefore, lengthAfter);
    }

    @Test
    @DirtiesContext
    public void createEvent(){
        Event event = createValidEvent();
        // new id would be set here
        event = service.createEvent(event);
        Optional<Event> result = service.getEventById(event.getId());
        assertTrue(result.isPresent());
        assertEquals(event.getName(), result.get().getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInvalidEventNullName(){
        Event event = createValidEvent();
        event.setName(null);
        service.createEvent(event);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInvalidEventEmptyName(){
        Event event = createValidEvent();
        event.setName("");
        service.createEvent(event);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInvalidEventEmptyPrice(){
        Event event = createValidEvent();
        event.setPrice(0);
        service.createEvent(event);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInvalidEventNullSchedule(){
        Event event = createValidEvent();
        event.setSchedule(null);
        service.createEvent(event);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInvalidEventEmptySchedule(){
        Event event = createValidEvent();
        event.setSchedule(new TreeSet<>());
        service.createEvent(event);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInvalidEventBadSchedule(){
        Event event = createValidEvent();
        TreeSet<Show> schedule = new TreeSet<>();
        schedule.add(new Show());
        event.setSchedule(schedule);
        service.createEvent(event);
    }

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void createInvalidEventBadAuditorium(){
        Event event = createValidEvent();
        Auditorium testAuditorium = new Auditorium();
        testAuditorium.setId(123);
        event.getSchedule().first().setAuditorium(testAuditorium);
        service.createEvent(event);
    }

    @Test
    public void getEventsForDateRange(){
        Collection<Event> eventsFor2Days = service.getForDateRange(
                LocalDateTime.parse("2016-05-05T14:00"), LocalDateTime.parse("2016-05-07T14:00"));
        assertEquals(2, eventsFor2Days.size());
    }

    @Test
    public void getEventsForDateRangeNothingFound(){
        Collection<Event> eventsFor2Days = service.getForDateRange(
                LocalDateTime.parse("2010-05-05T14:00"), LocalDateTime.parse("2010-05-07T14:00"));
        assertEquals(0, eventsFor2Days.size());
    }

    @Test
    @DirtiesContext
    public void getNextEvents(){
        Optional<Event> event = service.getEventById(4);
        Show show = event.get().getSchedule().first();
        show.setStart(LocalDateTime.now().plusDays(1));
        show.setEnd(LocalDateTime.now().plusDays(2));
        service.saveEvent(event.get());
        Collection<Event> nextEvents = service.getNextEvents(LocalDateTime.now().plusDays(4));
        assertEquals(1, nextEvents.size());
        assertEquals(event.get().getId(), nextEvents.iterator().next().getId());
    }

    @Test
    public void getNextEventsEmptyResult(){
        Collection<Event> nextEvents = service.getNextEvents(LocalDateTime.now().plusDays(4));
        assertTrue(nextEvents.isEmpty());
    }

    @Test
    public void getNextEventsDateToInPast(){
        Collection<Event> nextEvents = service.getNextEvents(LocalDateTime.now().minusDays(4));
        assertTrue(nextEvents.isEmpty());
    }

    // ------------------------ Inner methods ------------------------

    private Event createValidEvent(){
        Event event = new Event();
        event.setId(1234);
        event.setName(VALID_EVENT_NAME);
        event.setPrice(12.2);
        TreeSet<Show> schedule = new TreeSet<>();
        Show show = new Show();
        show.setId(123);
        show.setStart(LocalDateTime.now().minusDays(1));
        show.setEnd(LocalDateTime.now());
        show.setAuditorium(auditoriumService.getAuditoriumById(1).get());
        schedule.add(show);
        event.setSchedule(schedule);
        return event;
    }
}
