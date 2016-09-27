package com.baziuk.spring.auditorium.service;

import com.baziuk.spring.auditorium.bean.Auditorium;
import com.baziuk.spring.auditorium.dao.AuditoriumDAO;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Maks on 9/20/16.
 */
public class AuditoriumCinemaService implements AuditoriumService {

    private AuditoriumDAO auditoriumDAO;

    @Override
    public Collection<Auditorium> getAllAuditoriums() {
        return auditoriumDAO.getAll();
    }

    @Override
    public Optional<Auditorium> getAuditoriumByName(String name) {
        return StringUtils.isNoneBlank(name) ? Optional.ofNullable(auditoriumDAO.getByName(name)) : Optional.empty();
    }

    @Override
    public Optional<Auditorium> getAuditoriumById(long id) {
        return Optional.ofNullable(auditoriumDAO.get(id));
    }

    public AuditoriumDAO getAuditoriumDAO() {
        return auditoriumDAO;
    }

    public void setAuditoriumDAO(AuditoriumDAO auditoriumDAO) {
        this.auditoriumDAO = auditoriumDAO;
    }
}
