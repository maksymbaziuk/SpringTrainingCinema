package com.baziuk.spring.aspect.dao;

import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.user.bean.User;

import java.util.Map;

/**
 * Created by Maks on 10/2/16.
 */
public interface CounterDAO {

    Map<String, Integer> getAccessEventByName();
    void increaseAccessEventByName(String key);
    Map<Event, Integer> getPriceForEventCounter();
    void increasePriceForEventCounter(Event key);
    Map<Event, Integer> getBookEventTicketsCounter();
    void increaseBookEventTicketsCounter(Event key);
    Map<String, Integer> getDiscountNameCounter();
    void increaseDiscountNameCounter(String key);
    Map<Long, Map<String, Integer>> getUserDiscountCounter();
    void increaseUserDiscountCounter(User user, String key);

}
