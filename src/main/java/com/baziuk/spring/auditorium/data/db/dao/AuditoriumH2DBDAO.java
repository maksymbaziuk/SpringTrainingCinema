package com.baziuk.spring.auditorium.data.db.dao;

import com.baziuk.spring.auditorium.bean.Auditorium;
import com.baziuk.spring.auditorium.dao.AuditoriumDAO;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by Maks on 10/10/16.
 */
@Repository
public class AuditoriumH2DBDAO implements AuditoriumDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Auditorium create(Auditorium item) {
        jdbcTemplate.update("insert into AUDITORIUM(name, sits) values(?, ?)", new Object[]{item.getName(), item.getSits()});
        Auditorium newAud = getByName(item.getName());
        jdbcTemplate.batchUpdate("insert into AUDITORIUM_VIP_SITS values(?, ?) ", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, newAud.getId());
                preparedStatement.setInt(2, item.getVipSits().get(i));
            }
            @Override
            public int getBatchSize() {
                return item.getVipSits().size();
            }
        });
        return get(newAud.getId());
    }

    @Override
    public Auditorium update(Auditorium item) {
        Auditorium current = get(item.getId());
        clearVips(item.getId());
        throw new NotImplementedException("");
    }

    @Override
    public boolean remove(Auditorium item) {
        clearVips(item.getId());
        int rows = jdbcTemplate.update("delete from AUDITORIUM where id=?", new Object[]{item.getId()});
        return rows > 0;
    }

    @Override
    public Auditorium get(long id) {
        Auditorium auditorium;
        try{
            auditorium = jdbcTemplate.queryForObject("select * from AUDITORIUM where id=?", new Object[]{id}, AUDITORIUM_ROW_MAPPER);
            auditorium.setVipSits(queryVipSitsByAuditoriumId(id));
        } catch (EmptyResultDataAccessException e) {
            auditorium = null;
        }
        return auditorium;
    }

    @Override
    public Collection<Auditorium> getAll() {
        List<Auditorium> auditoriumList = jdbcTemplate.query("select * from AUDITORIUM", AUDITORIUM_ROW_MAPPER);
        auditoriumList.forEach(auditorium -> {
            auditorium.setVipSits(queryVipSitsByAuditoriumId(auditorium.getId()));
        });
        return auditoriumList;
    }

    @Override
    public Auditorium getByName(String name) {
        Auditorium auditorium;
        try{
            auditorium = jdbcTemplate.queryForObject("select * from AUDITORIUM where name=?", new Object[]{name}, AUDITORIUM_ROW_MAPPER);
            auditorium.setVipSits(queryVipSitsByAuditoriumId(auditorium.getId()));
        } catch (EmptyResultDataAccessException e) {
            auditorium = null;
        }
        return auditorium;
    }

    private List<Integer> queryVipSitsByAuditoriumId(long id){
        List auditoriumVipSitsList = jdbcTemplate.query("select sit_number from AUDITORIUM_VIP_SITS where auditorium_id=?",
                new Object[] {id},
                AUDITORIUM_VIP_SIT_ROW_MAPPER);
        return auditoriumVipSitsList;
    }

    private void clearVips(long id){
        jdbcTemplate.update("delete from AUDITORIUM_VIP_SITS where auditorium_id=?", new Object[]{id});
    }

    private static final AuditoriumRowMapper AUDITORIUM_ROW_MAPPER = new AuditoriumRowMapper();
    private static final AuditoriumVipSitRowMapper AUDITORIUM_VIP_SIT_ROW_MAPPER = new AuditoriumVipSitRowMapper();

    private static class AuditoriumRowMapper implements RowMapper<Auditorium>{

        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String SITS = "sits";

        @Override
        public Auditorium mapRow(ResultSet resultSet, int i) throws SQLException {
            Auditorium auditorium = new Auditorium();
            auditorium.setId(resultSet.getLong(ID));
            auditorium.setName(resultSet.getString(NAME));
            auditorium.setSits(resultSet.getInt(SITS));
            return auditorium;
        }
    }

    private static class AuditoriumVipSitRowMapper implements RowMapper<Integer>{

        private static final String SIT_NUMBER = "sit_number";

        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt(SIT_NUMBER);
        }
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
