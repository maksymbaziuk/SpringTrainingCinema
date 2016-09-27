package com.baziuk.spring.auditorium.dao;

import com.baziuk.spring.auditorium.bean.Auditorium;
import com.baziuk.spring.common.dao.CrudDAO;

/**
 * Created by Maks on 9/20/16.
 */
public interface AuditoriumDAO extends CrudDAO<Auditorium> {

    Auditorium getByName(String name);

}
