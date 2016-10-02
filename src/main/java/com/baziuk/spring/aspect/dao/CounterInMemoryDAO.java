package com.baziuk.spring.aspect.dao;

import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.user.bean.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maks on 10/2/16.
 */
@Repository
public class CounterInMemoryDAO implements CounterDAO {

    public Map<String, Integer> accessEventByName = new HashMap<>();
    public Map<Event, Integer> priceForEventCounter = new HashMap<>();
    public Map<Event, Integer> bookEventTicketsCounter = new HashMap<>();
    public Map<String, Integer> discountNameCounter = new HashMap<>();
    public Map<Long, Map<String, Integer>> userDiscountCounter = new HashMap<>();

    @Override
    public Map<String, Integer> getAccessEventByName() {
        return new HashMap<>(accessEventByName);
    }

    @Override
    public Map<Event, Integer> getPriceForEventCounter() {
        return new HashMap<>(priceForEventCounter);
    }

    @Override
    public Map<Event, Integer> getBookEventTicketsCounter() {
        return new HashMap<>(bookEventTicketsCounter);
    }

    @Override
    public Map<String, Integer> getDiscountNameCounter() {
        return new HashMap<>(discountNameCounter);
    }

    @Override
    public Map<Long, Map<String, Integer>> getUserDiscountCounter() {
        HashMap<Long, Map<String, Integer>> result = new HashMap<>();
        for (Map.Entry<Long, Map<String, Integer>> entry : userDiscountCounter.entrySet()){
            result.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }
        return result;
    }

    @Override
    public void increaseAccessEventByName(String key) {
        int counter = accessEventByName.containsKey(key) ? accessEventByName.get(key) : 0;
        accessEventByName.put(key, counter + 1);
    }

    @Override
    public void increasePriceForEventCounter(Event key) {
        int counter = priceForEventCounter.containsKey(key) ? priceForEventCounter.get(key) : 0;
        priceForEventCounter.put(key, counter + 1);
    }

    @Override
    public void increaseBookEventTicketsCounter(Event key) {
        int counter = bookEventTicketsCounter.containsKey(key) ? bookEventTicketsCounter.get(key) : 0;
        bookEventTicketsCounter.put(key, counter + 1);
    }

    @Override
    public void increaseDiscountNameCounter(String key) {
        int counter = discountNameCounter.containsKey(key) ? discountNameCounter.get(key) : 0;
        discountNameCounter.put(key, counter + 1);
    }

    @Override
    public void increaseUserDiscountCounter(User user, String key) {
        Map<String, Integer> counter = userDiscountCounter.get(user.getId());
        if (counter == null) {
            counter = new HashMap<>();
            userDiscountCounter.put(user.getId(), counter);
        }
        Integer discountAppliedToUser = counter.get(key);
        if (discountAppliedToUser == null)
            discountAppliedToUser = 0;
        counter.put(key, discountAppliedToUser + 1);
    }
}
