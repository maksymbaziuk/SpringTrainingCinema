package com.baziuk.spring.payment.dao;

import com.baziuk.spring.common.dao.CrudDAO;
import com.baziuk.spring.payment.bean.UserAccount;

/**
 * Created by Maks on 11/20/16.
 */
public interface PaymentDAO extends CrudDAO<UserAccount> {

    UserAccount getUserAccountForUser(long userId);

}
