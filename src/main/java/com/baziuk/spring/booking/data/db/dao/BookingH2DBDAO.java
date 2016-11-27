package com.baziuk.spring.booking.data.db.dao;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.booking.dao.BookingDAO;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.dao.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Maks on 10/12/16.
 */
@Repository
public class BookingH2DBDAO implements BookingDAO {

    @Autowired
    private EventDAO eventDAO;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Ticket create(Ticket item) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("TICKET").usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<>();
        params.put("price", item.getPrice());
        params.put("sit_number", item.getSitNumber());
        params.put("event_id", item.getEvent().getId());
        params.put("show_id", item.getShow().getId());
        Number newShowId = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        item.setId(newShowId.longValue());
        return item;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Ticket update(Ticket item) {
        jdbcTemplate.update("update TICKET set price=?, sit_number=?, event_id=?, show_id=? where id=?",
                new Object[]{item.getPrice(), item.getSitNumber(), item.getEvent().getId(), item.getShow().getId(), item.getId()});
        return item;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean remove(Ticket item) {
        int rowsAffected = jdbcTemplate.update("delete from TICKET where id=?", new Object[]{item.getId()});
        return rowsAffected > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Ticket get(long id) {
        Ticket ticket;
        try {
            ticket = jdbcTemplate.queryForObject("select * from TICKET where id=?", new Object[]{id}, TICKET_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            ticket = null;
        }
        return ticket;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Ticket> getAll() {
        List<Ticket> tickets = jdbcTemplate.query("select * from TICKET", TICKET_ROW_MAPPER);
        return tickets;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Ticket> getTicketsForEvent(Event event, LocalDateTime from, LocalDateTime to) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("eventId", event.getId());
        Collection<Ticket> tickets = template.query("SELECT * FROM TICKET WHERE event_id IN (:eventId)", parameters, TICKET_ROW_MAPPER);
        return tickets;
    }

    private final TicketRowMapper TICKET_ROW_MAPPER = new TicketRowMapper();
    private class TicketRowMapper implements RowMapper<Ticket>{
        @Override
        public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setId(resultSet.getLong("id"));
            ticket.setPrice(resultSet.getDouble("price"));
            ticket.setSitNumber(resultSet.getInt("sit_number"));
            Long eventId = resultSet.getLong("event_id");
            Long showId = resultSet.getLong("show_id");
            ticket.setEvent(eventDAO.get(eventId));
            ticket.setShow(eventDAO.getShowById(showId));
            return ticket;
        }
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
