package com.baziuk.spring.user.dao;

import com.baziuk.spring.common.dao.CrudDAO;
import com.baziuk.spring.user.bean.User;

/**
 * Created by Maks on 9/20/16.
 */
public interface UserDAO extends CrudDAO<User> {

    User getByEmail(String email);

}
