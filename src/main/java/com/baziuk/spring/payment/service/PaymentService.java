package com.baziuk.spring.payment.service;

import com.baziuk.spring.payment.bean.UserAccount;

import java.util.Optional;

/**
 * Created by Maks on 11/20/16.
 */
public interface PaymentService {

    Optional<UserAccount> getUserAccount(long userId);
    boolean debit(long userId, double amountToDebit);
    boolean addFunds(long userId, double amountToAdd);

}
