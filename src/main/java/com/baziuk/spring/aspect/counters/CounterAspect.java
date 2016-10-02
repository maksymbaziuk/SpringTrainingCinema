package com.baziuk.spring.aspect.counters;

import com.baziuk.spring.aspect.dao.CounterDAO;
import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.events.bean.Event;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Maks on 10/2/16.
 */
@Aspect
@Component
public class CounterAspect {

    @Autowired
    private CounterDAO counterDAO;

    @Before("execution(* com.baziuk.spring.events.service.EventService.getEventByName(..))")
    public void accessEventByName(JoinPoint joinPoint){
        String eventName = (String) joinPoint.getArgs()[0];
        counterDAO.increaseAccessEventByName(eventName);
    }

    @Before("execution(* com.baziuk.spring.booking.service.BookingService.getTicketsPrice(..))")
    public void priceForEventQueried(JoinPoint joinPoint){
        Event event = (Event) joinPoint.getArgs()[0];
        counterDAO.increasePriceForEventCounter(event);
    }

    @AfterReturning(value = "execution(* com.baziuk.spring.booking.service.BookingService.bookTickets(..))", returning = "returnValue")
    public void bookEventTicketsCounter(JoinPoint joinPoint, Object returnValue){
        ArrayList<Ticket> tickets = new ArrayList<>((Collection<Ticket>) returnValue);
        if (!tickets.isEmpty()){
            Event event = tickets.get(0).getEvent();
            tickets.forEach(ticket -> {
                counterDAO.increaseBookEventTicketsCounter(event);
            });
        }
    }

    public CounterDAO getCounterDAO() {
        return counterDAO;
    }

    public void setCounterDAO(CounterDAO counterDAO) {
        this.counterDAO = counterDAO;
    }
}
