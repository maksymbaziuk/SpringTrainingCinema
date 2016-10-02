package com.baziuk.spring.discount.service;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.discount.bean.Discount;
import com.baziuk.spring.discount.strategy.DiscountStrategy;
import com.baziuk.spring.user.bean.User;

import java.util.*;

/**
 * Created by Maks on 9/25/16.
 */
public class DiscountCinemaService implements DiscountService {

    private Collection<DiscountStrategy> strategies = new ArrayList<>();

    @Override
    public Set<Discount> getDiscounts(User user, Collection<Ticket> tickets) {
        TreeSet<Discount> discounts = new TreeSet<>();
        for (DiscountStrategy strategy : strategies) {
            boolean discountable = strategy.isDiscountable(user, tickets);
            if (discountable){
                discounts.add(strategy.getDiscount(user, tickets));
            }
        }
        return discounts;
    }

    @Override
    public Optional<Discount> getBestDiscount(User user, Collection<Ticket> tickets) {
        Set<Discount> availableDiscounts =  getDiscounts(user, tickets);
        return availableDiscounts.stream().findFirst();
    }

    public Collection<DiscountStrategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(Collection<DiscountStrategy> strategies) {
        this.strategies = strategies;
    }
}
