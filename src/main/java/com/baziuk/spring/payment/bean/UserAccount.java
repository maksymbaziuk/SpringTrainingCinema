package com.baziuk.spring.payment.bean;

/**
 * Created by Maks on 11/20/16.
 */
public class UserAccount {

    private long id;
    private long userId;
    private double amountAvailable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(double amountAvailable) {
        this.amountAvailable = amountAvailable;
    }
}
