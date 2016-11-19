package com.baziuk.spring.events.web;

import com.baziuk.spring.common.web.bean.DateTimeRange;
import com.baziuk.spring.common.web.binding.LocalDateTimeDataBinder;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.service.EventService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Maks on 11/10/16.
 */
@Controller
@RequestMapping("/event")
public class EventController {

    private static final Logger log = Logger.getLogger(EventController.class);

    @Autowired
    private EventService eventService;
    @Autowired
    private LocalDateTimeDataBinder localDateTimeDataBinder;

    private static final Gson GSON = new Gson();

    @ResponseBody
    @RequestMapping("/get/{eventId}")
    public String getEventById(@PathVariable long eventId){
        log.info("Getting info about event with id:" + eventId);
        Event event = eventService.getEventById(eventId).get();
        log.info("Result of getting event info for event:" + eventId + " -> " + event.toString());
        return GSON.toJson(event);
    }

    @ResponseBody
    @RequestMapping("/all")
    public String getAllEvents() {
        log.info("Getting all events");
        Collection<Event> events = eventService.getAllEvents();
        log.info("Result of getting all events: " + Arrays.toString(events.toArray()));
        return GSON.toJson(events);
    }

    @ResponseBody
    @RequestMapping("/daterange")
    public String getForDateRange(@ModelAttribute DateTimeRange dateRange){
        log.info("Getting events for date range: " + dateRange.toString());
        Collection<Event> events;
        if (dateRange.getTo() != null) {
            log.info("Get for date range");
            events = eventService.getForDateRange(dateRange.getFrom(), dateRange.getTo());
        } else {
            log.info("Get next events");
            events = eventService.getNextEvents(dateRange.getFrom());
        }
        log.info("Result of getting events for date range: " + Arrays.toString(events.toArray()));
        return GSON.toJson(events);
    }

    @InitBinder
    public void initDataBinders(WebDataBinder dataBinder){
        dataBinder.registerCustomEditor(LocalDateTime.class, localDateTimeDataBinder);
    }
}
