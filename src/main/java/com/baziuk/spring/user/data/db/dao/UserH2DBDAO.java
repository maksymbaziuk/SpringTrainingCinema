package com.baziuk.spring.user.data.db.dao;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.booking.dao.BookingDAO;
import com.baziuk.spring.user.bean.User;
import com.baziuk.spring.user.bean.UserRole;
import com.baziuk.spring.user.dao.UserDAO;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by Maks on 10/12/16.
 */
@Repository
public class UserH2DBDAO implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BookingDAO bookingDAO;

    @Override
    public User create(User item) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("USERS").usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<>();
        params.put("birthday", fromDateTime(item.getBirthday()));
        params.put("email", item.getEmail());
        params.put("user_role", item.getUserRole().toString());
        Number newUserId = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        item.setId(newUserId.longValue());
        return item;
    }

    @Override
    public User update(User item) {
        jdbcTemplate.update("update USERS set email=?, birthday=?, user_role=? where id=?",
                new Object[]{item.getEmail(), fromDateTime(item.getBirthday()), item.getUserRole().toString(), item.getId()});
        clearUserTicketRelations(item);
        createUserTicketRelations(item);
        return item;
    }

    @Override
    public boolean remove(User item) {
        int rows = jdbcTemplate.update("delete from USER_TICKETS where user_id=?", new Object[]{item.getId()});
        if (rows > 0){
            rows = jdbcTemplate.update("delete from USERS where id=?", new Object[]{item.getId()});
            return rows > 0;
        }
        return false;
    }

    @Override
    public User get(long id) {
        User item;
        try {
            item = jdbcTemplate.queryForObject("select * from USERS where id=?", new Object[]{id}, USER_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e){
            item = null;
        }
        return item;
    }

    @Override
    public User getByEmail(String email) {
        User item;
        try {
            item = jdbcTemplate.queryForObject("select * from USERS where email=?", new Object[]{email}, USER_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e){
            item = null;
        }
        return item;
    }

    @Override
    public Collection<User> getAll() {
        Collection<User> users = jdbcTemplate.query("select * from USERS", USER_ROW_MAPPER);
        return users;
    }

    private final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setBirthday(toDateTime(resultSet.getLong("birthday")));
            user.setEmail(resultSet.getString("email"));
            user.setUserRole(UserRole.valueOf(resultSet.getString("user_role")));
            List<Integer> ticketIds = jdbcTemplate.query("SELECT ticket_id FROM USER_TICKETS WHERE user_id = ?",
                    new Object[]{user.getId()},
                    ((resultSet1, i1) -> resultSet1.getInt("ticket_id")));
            List<Ticket> tickets = new ArrayList<>();
            ticketIds.forEach(id -> tickets.add(bookingDAO.get(id)));
            user.setBoughtTickets(tickets);
            return user;
        }
    }

    private void createUserTicketRelations(User user){
        List<Ticket> ticketList = new ArrayList<>(user.getBoughtTickets());
        jdbcTemplate.batchUpdate("insert into USER_TICKETS values(?, ?) ", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setLong(2, ticketList.get(i).getId());
            }
            @Override
            public int getBatchSize() {
                return ticketList.size();
            }
        });
    }

    private void clearUserTicketRelations(User user){
        jdbcTemplate.update("delete from USER_TICKETS where user_id=?", new Object[]{user.getId()});
    }

    private LocalDate toDateTime(long milis){
        return Instant.ofEpochMilli(milis).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private long fromDateTime(LocalDate milis){
        return milis.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BookingDAO getBookingDAO() {
        return bookingDAO;
    }

    public void setBookingDAO(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }
}
