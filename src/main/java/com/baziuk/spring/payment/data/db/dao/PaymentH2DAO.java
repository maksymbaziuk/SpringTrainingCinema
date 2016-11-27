package com.baziuk.spring.payment.data.db.dao;

import com.baziuk.spring.payment.bean.UserAccount;
import com.baziuk.spring.payment.dao.PaymentDAO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maks on 11/20/16.
 */
@Repository
public class PaymentH2DAO implements PaymentDAO {

    private static final Logger log = Logger.getLogger(PaymentH2DAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserAccount create(UserAccount item) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("USER_ACCOUNT").usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", item.getUserId());
        params.put("amount_available", item.getAmountAvailable());
        Number newUserAccountId = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        item.setId(newUserAccountId.longValue());
        return item;
    }

    @Override
    public UserAccount update(UserAccount item) {
        jdbcTemplate.update("update USER_ACCOUNT set user_id=?, amount_available=? where id=?",
                new Object[]{item.getUserId(), item.getAmountAvailable(), item.getId()});
        return item;
    }

    @Override
    public boolean remove(UserAccount item) {
        int rowsAffected = jdbcTemplate.update("delete from USER_ACCOUNT where id=?", new Object[]{item.getId()});
        return rowsAffected > 0;
    }

    @Override
    public UserAccount get(long id) {
        UserAccount userAccount;
        try {
            userAccount = jdbcTemplate.queryForObject("select * from USER_ACCOUNT where id=?",
                    new Object[]{id}, USER_ACCOUNT_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            log.info("EmptyResultDataAccessException for UserAccount with id " + id);
            userAccount = null;
        }
        return userAccount;
    }

    @Override
    public Collection<UserAccount> getAll() {
        List<UserAccount> userAccounts = jdbcTemplate.query("select * from USER_ACCOUNT", USER_ACCOUNT_ROW_MAPPER);
        return userAccounts;
    }

    @Override
    public UserAccount getUserAccountForUser(long userId) {
        List<UserAccount> userAccounts = jdbcTemplate.query("select * from USER_ACCOUNT where user_id=?",
                new Object[]{userId}, USER_ACCOUNT_ROW_MAPPER);
        return CollectionUtils.isNotEmpty(userAccounts) ? userAccounts.get(0) : null;
    }

    private static final UserAccountRowMapper USER_ACCOUNT_ROW_MAPPER = new UserAccountRowMapper();

    public static class UserAccountRowMapper implements RowMapper<UserAccount>{
        @Override
        public UserAccount mapRow(ResultSet resultSet, int i) throws SQLException {
            UserAccount userAccount = new UserAccount();
            userAccount.setId(resultSet.getLong("id"));
            userAccount.setUserId(resultSet.getLong("user_id"));
            userAccount.setAmountAvailable(resultSet.getDouble("amount_available"));
            return userAccount;
        }
    }
}
