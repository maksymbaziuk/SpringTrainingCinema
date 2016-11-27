package com.baziuk.spring.payment.service;

import com.baziuk.spring.payment.bean.UserAccount;
import com.baziuk.spring.payment.dao.PaymentDAO;
import com.baziuk.spring.payment.exception.PaymentOperationFailed;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Maks on 11/20/16.
 */
@Service
public class PaymentCinemaService implements PaymentService {

    private static final Logger log = Logger.getLogger(PaymentCinemaService.class);

    @Autowired
    private PaymentDAO paymentH2DAO;

    @Override
    public Optional<UserAccount> getUserAccount(long userId) {
        return Optional.ofNullable(paymentH2DAO.getUserAccountForUser(userId));
    }

    @Override
    public boolean debit(long userId, double amountToDebit) {
        UserAccount userAccount = paymentH2DAO.getUserAccountForUser(userId);
        if (userAccount == null){
            log.error("Debiting empty account: user_id=" + userId);
            throw new PaymentOperationFailed("Unable to debit founds: User account not found");
        }
        if (userAccount.getAmountAvailable() < amountToDebit){
            log.info("Insufficient founds for user:" + userId + ", amountToDebit:" + amountToDebit);
            throw new PaymentOperationFailed("Unable to debit founds: Insufficient founds");
        }
        userAccount.setAmountAvailable(userAccount.getAmountAvailable() - amountToDebit);
        paymentH2DAO.update(userAccount);
        return true;
    }

    @Override
    public boolean addFunds(long userId, double amountToAdd) {
        UserAccount userAccount = paymentH2DAO.getUserAccountForUser(userId);
        if (userAccount == null){
            // If user account not found - lets create it
            userAccount = new UserAccount();
            userAccount.setUserId(userId);
            userAccount.setAmountAvailable(amountToAdd);
            paymentH2DAO.create(userAccount);
        } else {
            // Just adding founds
            userAccount.setAmountAvailable(userAccount.getAmountAvailable() + amountToAdd);
            paymentH2DAO.update(userAccount);
        }
        return true;
    }
}
