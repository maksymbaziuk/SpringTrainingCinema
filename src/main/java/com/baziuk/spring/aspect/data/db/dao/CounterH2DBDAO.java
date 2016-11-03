package com.baziuk.spring.aspect.data.db.dao;

import com.baziuk.spring.aspect.dao.CounterDAO;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.dao.EventDAO;
import com.baziuk.spring.user.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maks on 10/12/16.
 */
@Repository
public class CounterH2DBDAO implements CounterDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EventDAO eventDAO;

    @Override
    public Map<String, Integer> getAccessEventByName() {
        Map<String, Integer> accessByName = new HashMap<>();
        try {
            jdbcTemplate.query("select * from COUNTER where counter_name='byName'", ((resultSet, i) -> accessByName.put(resultSet.getString("key"), resultSet.getInt("counter"))));
        } catch (EmptyResultDataAccessException e) {
            // No counter set up for combination counter_name/key
        }
        return accessByName;
    }

    @Override
    public void increaseAccessEventByName(String key) {
        try {
            jdbcTemplate.queryForObject("select counter from COUNTER where counter_name='byName' and key =?", new Object[]{key}, ((resultSet, i) -> Integer.MAX_VALUE));
            jdbcTemplate.update("UPDATE COUNTER set counter = counter + 1 where counter_name='byName' and key =?", new Object[]{key});
        } catch (EmptyResultDataAccessException e) {
            // Counter doesn't exist, creating new
            jdbcTemplate.update("insert into COUNTER (counter_name, key, counter) values(?, ?, ?)", new Object[]{"byName", key, 1});
        }

    }

    @Override
    public Map<Event, Integer> getPriceForEventCounter() {
        Map<Event, Integer> priceByEvent = new HashMap<>();
        try {
            jdbcTemplate.query("select * from COUNTER where counter_name='priceByEvent'",
                    ((resultSet, i) -> priceByEvent.put(eventDAO.get(Long.parseLong(resultSet.getString("key"))), resultSet.getInt("counter"))));
        } catch (EmptyResultDataAccessException e) {
            // No counter set up for combination counter_name/key
        }
        return priceByEvent;
    }

    @Override
    public void increasePriceForEventCounter(Event key) {
        try {
            jdbcTemplate.queryForObject("select counter from COUNTER where counter_name='priceByEvent' and key =?", new Object[]{Long.toString(key.getId())}, ((resultSet, i) -> Integer.MAX_VALUE));
            jdbcTemplate.update("UPDATE COUNTER set counter = counter + 1 where counter_name='priceByEvent' and key =?", new Object[]{Long.toString(key.getId())});
        } catch (EmptyResultDataAccessException e) {
            // Counter doesn't exist, creating new
            jdbcTemplate.update("insert into COUNTER (counter_name, key, counter) values(?, ?, ?)", new Object[]{"priceByEvent", Long.toString(key.getId()), 1});
        }
    }

    @Override
    public Map<Event, Integer> getBookEventTicketsCounter() {
        Map<Event, Integer> bookEventTickets = new HashMap<>();
        try {
            jdbcTemplate.query("select * from COUNTER where counter_name='bookEventTickets'",
                    ((resultSet, i) -> bookEventTickets.put(eventDAO.get(Long.parseLong(resultSet.getString("key"))), resultSet.getInt("counter"))));
        } catch (EmptyResultDataAccessException e) {
            // No counter set up for combination counter_name/key
        }
        return bookEventTickets;
    }

    @Override
    public void increaseBookEventTicketsCounter(Event key) {
        try {
            jdbcTemplate.queryForObject("select counter from COUNTER where counter_name='bookEventTickets' and key =?", new Object[]{Long.toString(key.getId())}, ((resultSet, i) -> Integer.MAX_VALUE));
            jdbcTemplate.update("UPDATE COUNTER set counter = counter + 1 where counter_name='bookEventTickets' and key =?", new Object[]{Long.toString(key.getId())});
        } catch (EmptyResultDataAccessException e) {
            // Counter doesn't exist, creating new
            jdbcTemplate.update("insert into COUNTER (counter_name, key, counter) values(?, ?, ?)", new Object[]{"bookEventTickets", Long.toString(key.getId()), 1});
        }
    }

    @Override
    public Map<String, Integer> getDiscountNameCounter() {
        Map<String, Integer> discountByName = new HashMap<>();
        try {
            jdbcTemplate.query("select * from COUNTER where counter_name='discountByName'", ((resultSet, i) -> discountByName.put(resultSet.getString("key"), resultSet.getInt("counter"))));
        } catch (EmptyResultDataAccessException e) {
            // No counter set up for combination counter_name/key
        }
        return discountByName;
    }

    @Override
    public void increaseDiscountNameCounter(String key) {
        try {
            jdbcTemplate.queryForObject("select counter from COUNTER where counter_name='discountByName' and key =?", new Object[]{key}, ((resultSet, i) -> Integer.MAX_VALUE));
            jdbcTemplate.update("UPDATE COUNTER set counter = counter + 1 where counter_name='discountByName' and key =?", new Object[]{key});
        } catch (EmptyResultDataAccessException e) {
            // Counter doesn't exist, creating new
            jdbcTemplate.update("insert into COUNTER (counter_name, key, counter) values(?, ?, ?)", new Object[]{"discountByName", key, 1});
        }
    }

    private static final String SEPARATOR = ":";

    @Override
    public Map<Long, Map<String, Integer>> getUserDiscountCounter() {
        Map<Long, Map<String, Integer>> userDiscountCounter = new HashMap<>();
        try {
            jdbcTemplate.query("select * from COUNTER where counter_name='userDiscountCounter'", (RowMapper<? extends Object>) ((resultSet, i) ->
                {
                    String key = resultSet.getString("key");
                    String[] values = key.split(SEPARATOR);
                    Long userId = Long.parseLong(values[0]);
                    Map<String, Integer> userDiscounts = userDiscountCounter.get(userId);
                    if (userDiscounts == null){
                        userDiscounts = new HashMap<>();
                    }
                    userDiscounts.put(values[1], resultSet.getInt("counter"));
                    userDiscountCounter.put(userId, userDiscounts);
                    return Integer.MAX_VALUE;
                }));
        } catch (EmptyResultDataAccessException e) {
            // No counter set up for combination counter_name/key
        }
        return userDiscountCounter;
    }

    @Override
    public void increaseUserDiscountCounter(User user, String key) {
        try {
            jdbcTemplate.queryForObject("select counter from COUNTER where counter_name='userDiscountCounter' and key =?", new Object[]{user.getId() + SEPARATOR + key}, ((resultSet, i) -> Integer.MAX_VALUE));
            jdbcTemplate.update("UPDATE COUNTER set counter = counter + 1 where counter_name='userDiscountCounter' and key =?", new Object[]{user.getId() + SEPARATOR + key});
        } catch (EmptyResultDataAccessException e) {
            // Counter doesn't exist, creating new
            jdbcTemplate.update("insert into COUNTER (counter_name, key, counter) values(?, ?, ?)", new Object[]{"userDiscountCounter", user.getId() + SEPARATOR + key, 1});
        }
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }
}
