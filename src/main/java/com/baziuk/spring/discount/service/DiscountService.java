package com.baziuk.spring.discount.service;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.discount.bean.Discount;
import com.baziuk.spring.user.bean.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Maks on 9/20/16.
 */
public interface DiscountService {

    Collection<Discount> getDiscounts(User user, Collection<Ticket> tickets);
    Optional<Discount> getBestDiscount(User user, Collection<Ticket> tickets);

}
