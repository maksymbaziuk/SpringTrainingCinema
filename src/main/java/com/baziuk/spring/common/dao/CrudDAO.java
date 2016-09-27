package com.baziuk.spring.common.dao;

import java.util.Collection;

/**
 * Created by Maks on 9/20/16.
 */
public interface CrudDAO<T> {

    T create(T item);
    T update(T item);
    boolean remove(T item);
    T get(long id);
    Collection<T> getAll();

}
