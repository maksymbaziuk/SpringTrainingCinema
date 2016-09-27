package com.baziuk.spring.discount.bean;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.discount.strategy.DiscountStrategy;

import java.util.List;

/**
 * Created by Maks on 9/25/16.
 */
public class Discount implements Comparable<Discount> {

    private DiscountStrategy discountStrategy;
    private double discountAmount;
    private List<Ticket> discountableItems;

    @Override
    public int compareTo(Discount o) {
        return Double.compare(discountAmount, o.discountAmount);
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public List<Ticket> getDiscountableItems() {
        return discountableItems;
    }

    public void setDiscountableItems(List<Ticket> discountableItems) {
        this.discountableItems = discountableItems;
    }
}
