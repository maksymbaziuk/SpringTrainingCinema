package com.baziuk.spring.events.data.db.dao;

import com.baziuk.spring.auditorium.bean.Auditorium;
import com.baziuk.spring.auditorium.dao.AuditoriumDAO;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.EventRating;
import com.baziuk.spring.events.bean.Show;
import com.baziuk.spring.events.dao.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by Maks on 10/11/16.
 */
@Repository
public class EventH2DBDAO implements EventDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AuditoriumDAO auditoriumDAO;

    @Override
    public Event create(Event item) {
        createEvent(item);

        Collection<Show> schedule = item.getSchedule();
        schedule.forEach(curShow -> {
            createShow(curShow);
        });

        createEventShowRelations(item);
        return item;
    }

    @Override
    public Event update(Event item) {
        jdbcTemplate.update("update EVENT set name=? , eventRating=? , price=? where id=?",
                new Object[]{
                        item.getName(),
                        item.getEventRating().toString(),
                        item.getPrice(),
                        item.getId()});
        item.getSchedule().forEach(curShow -> {
            Show byId = getShowById(curShow.getId());
            if (byId == null){
                Show created = createShow(curShow);
                curShow.setId(created.getId());
            } else {
                updateShow(curShow);
            }
        });
        clearEventShowRelations(item);
        createEventShowRelations(item);
        return item;
    }

    @Override
    public boolean remove(Event item) {
        // Events and shows could be linked to tickets, lets skipp it because it's training project
        throw new UnsupportedOperationException();
    }

    @Override
    public Event get(long id) {
        Event event;
        try {
            event = jdbcTemplate.queryForObject("select * from EVENT where id=?", new Object[]{id}, EVENT_ROW_DATA_MAPPER);
        } catch (EmptyResultDataAccessException e){
            event = null;
        }
        return event;
    }

    @Override
    public Collection<Event> getAll() {
        List<Event> events = jdbcTemplate.query("select * from EVENT", EVENT_ROW_DATA_MAPPER);
        return events;
    }

    @Override
    public Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to) {
        List<Event> events = jdbcTemplate.query("select * from EVENT where id in (select event_id from EVENT_SHOW es join SHOW sh on es.show_id=sh.id where sh.start > ? and sh.end< ?)", new Object[]{fromDateTime(from), fromDateTime(to)}, EVENT_ROW_DATA_MAPPER);
        return events;
    }

    @Override
    public Event getByName(String name) {
        Event event;
        try {
            event = jdbcTemplate.queryForObject("select * from EVENT where name=?", new Object[]{name}, EVENT_ROW_DATA_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            event = null;
        }
        return event;
    }

    @Override
    public Show getShowById(Long id) {
        Show show;
        try {
            show = jdbcTemplate.queryForObject("select * from SHOW where id=?", new Object[]{id}, SHOW_ROW_MAPPER);
        }catch (EmptyResultDataAccessException e){
            show = null;
        }
        return show;
    }

    private Show updateShow(Show show){
        jdbcTemplate.update("update SHOW set auditorium=? , start=? , end=? where id=?",
                new Object[]{
                    show.getAuditorium().getId(),
                        fromDateTime(show.getStart()),
                        fromDateTime(show.getEnd()),
                        show.getId()});
        return show;
    }

    private void clearEventShowRelations(Event event){
        jdbcTemplate.update("delete from EVENT_SHOW where event_id=?",
                new Object[]{event.getId()});
    }

    private void createEventShowRelations(Event item){
        List<Show> showList = new ArrayList<>(item.getSchedule());
        jdbcTemplate.batchUpdate("insert into EVENT_SHOW values(?, ?) ", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, item.getId());
                preparedStatement.setLong(2, showList.get(i).getId());
            }
            @Override
            public int getBatchSize() {
                return showList.size();
            }
        });
    }

    private Show createShow(Show show){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("SHOW").usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<>();
        params.put("auditorium", show.getAuditorium().getId());
        params.put("start", fromDateTime(show.getStart()));
        params.put("end", fromDateTime(show.getEnd()));
        Number newShowId = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        show.setId(newShowId.longValue());
        return show;
    }

    private Event createEvent(Event event){
        SimpleJdbcInsert eventJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        eventJdbcInsert.withTableName("EVENT").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", event.getName());
        parameters.put("eventRating", event.getEventRating().toString());
        parameters.put("price", event.getPrice());
        Number newEventId = eventJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        event.setId(newEventId.longValue());
        return event;
    }

    private final EventRowDataMapper EVENT_ROW_DATA_MAPPER = new EventRowDataMapper();

    private class EventRowDataMapper implements RowMapper<Event>{

        @Override
        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
            Event event = new Event();
            event.setId(resultSet.getLong("id"));
            event.setName(resultSet.getString("name"));
            event.setEventRating(EventRating.valueOf(resultSet.getString("eventRating")));
            event.setPrice(resultSet.getDouble("price"));
            List<Long> showIds = jdbcTemplate.query("select show_id from EVENT_SHOW where event_id=?",
                    new Object[]{event.getId()},
                    (rs, iteration) -> rs.getLong("show_id")
            );
            TreeSet<Show> shows = new TreeSet<>();
            showIds.forEach(id -> shows.add(getShowById(id)));
            event.setSchedule(shows);
            return event;
        }
    }

    private final ShowRowMapper SHOW_ROW_MAPPER = new ShowRowMapper();

    private class ShowRowMapper implements RowMapper<Show>{
        @Override
        public Show mapRow(ResultSet resultSet, int i) throws SQLException {
            Show show = new Show();
            show.setId(resultSet.getLong("id"));
            show.setStart(toDateTime(resultSet.getLong("start")));
            show.setEnd(toDateTime(resultSet.getLong("end")));
            long auditoriumId = resultSet.getLong("auditorium");
            Auditorium auditorium = auditoriumDAO.get(auditoriumId);
            show.setAuditorium(auditorium);
            return show;
        }
    }

    private LocalDateTime toDateTime(long milis){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milis), ZoneId.systemDefault());
    }

    private long fromDateTime(LocalDateTime milis){
        return milis.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AuditoriumDAO getAuditoriumDAO() {
        return auditoriumDAO;
    }

    public void setAuditoriumDAO(AuditoriumDAO auditoriumDAO) {
        this.auditoriumDAO = auditoriumDAO;
    }
}
