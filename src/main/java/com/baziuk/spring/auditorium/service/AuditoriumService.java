package com.baziuk.spring.auditorium.service;

import com.baziuk.spring.auditorium.bean.Auditorium;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Maks on 9/20/16.
 */
public interface AuditoriumService {

    Collection<Auditorium> getAllAuditoriums();
    Optional<Auditorium> getAuditoriumByName(String name);
    Optional<Auditorium> getAuditoriumById(long id);

}
