package com.baziuk.spring.events.dao;

import com.baziuk.spring.common.dao.CrudDAO;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.Show;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by Maks on 9/20/16.
 */
public interface EventDAO extends CrudDAO<Event> {

    Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to);
    Event getByName(String name);
    Show getShowById(Long id);

}
