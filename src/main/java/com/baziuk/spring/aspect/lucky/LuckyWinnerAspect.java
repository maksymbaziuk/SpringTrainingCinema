package com.baziuk.spring.aspect.lucky;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.events.bean.Event;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by Maks on 10/2/16.
 */
@Aspect
@Component
public class LuckyWinnerAspect {

    @Around("execution(* com.baziuk.spring.booking.service.BookingService.bookTickets(..))")
    public Object checkYourLuck(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        Collection<Ticket> tickets = new ArrayList<>();
        int[] sits = (int[]) arguments[3];
        List<Integer> sitsToPrice = new ArrayList<>();
        List<Integer> luckySits = new ArrayList<>();
        for (Integer sit : sits){

            if (checkForLuck())     luckySits.add(sit);
            else                    sitsToPrice.add(sit);
        }
        if (luckySits.isEmpty()){
            return joinPoint.proceed();
        } else {
            // processing usual tickets first
            arguments[3] = sitsToPrice.stream().mapToInt(i -> i).toArray();
            tickets.addAll((Collection<Ticket>) joinPoint.proceed(arguments));
            // creating fake event with zero price
            Event event = (Event) arguments[0];
            // TODO change this - we don't need fake with JDBC
            Event freeEvent = constructFakeFreeEvent(event);
            // changing args to process free tickets
            arguments[0] = freeEvent;
            arguments[3] = luckySits.stream().mapToInt(i -> i).toArray();
            tickets.addAll((Collection<Ticket>) joinPoint.proceed(arguments));
            return tickets;
        }
    }

    private Event constructFakeFreeEvent(Event event) {
        Event freeEvent = new Event();
        freeEvent.setSchedule(event.getSchedule());
        freeEvent.setId(event.getId());
        freeEvent.setPrice(0d);
        freeEvent.setEventRating(event.getEventRating());
        freeEvent.setName(event.getName());
        return freeEvent;
    }

    protected boolean checkForLuck(){
        return new Random().nextBoolean();
    }

}
