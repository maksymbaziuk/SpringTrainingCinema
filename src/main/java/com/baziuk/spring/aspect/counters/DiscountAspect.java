package com.baziuk.spring.aspect.counters;

import com.baziuk.spring.aspect.dao.CounterDAO;
import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.discount.bean.Discount;
import com.baziuk.spring.user.bean.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Maks on 10/2/16.
 */
@Aspect
@Component
public class DiscountAspect {

    @Autowired
    private CounterDAO counterDAO;

    @AfterReturning(value = "execution(* com.baziuk.spring.booking.service.BookingService.bookTickets(..))", returning = "returnValue")
    public void countDiscountGiven(JoinPoint joinPoint, Object returnValue){
        ArrayList<Ticket> tickets = new ArrayList<>((Collection<Ticket>) returnValue);
        User user = (User)joinPoint.getArgs()[2];
        for (Ticket ticket : tickets){
            if (ticket.getDiscount() != null) {
                Discount discount = ticket.getDiscount();
                String name = discount.getDiscountStrategy().getName();
                counterDAO.increaseDiscountNameCounter(name);
                counterDAO.increaseUserDiscountCounter(user, name);
            }
        }
    }

    public CounterDAO getCounterDAO() {
        return counterDAO;
    }

    public void setCounterDAO(CounterDAO counterDAO) {
        this.counterDAO = counterDAO;
    }
}
