package com.baziuk.spring.discount.strategy;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.discount.bean.Discount;
import com.baziuk.spring.user.bean.User;

import java.util.Collection;

/**
 * Created by Maks on 9/24/16.
 */
public abstract class DiscountStrategy {

    private String name;
    private String description;
    private String evaluationStrategy;
    private String action;

    public abstract boolean isDiscountable(User user, Collection<Ticket> tickets);

    public abstract void applyDiscount(Discount discount);

    public abstract Discount getDiscount(User user, Collection<Ticket> tickets);

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvaluationStrategy() {
        return evaluationStrategy;
    }

    public void setEvaluationStrategy(String evaluationStrategy) {
        this.evaluationStrategy = evaluationStrategy;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public class DiscountContext{
        private User user;
        private Collection<Ticket> tickets;

        public DiscountContext(User user, Collection<Ticket> tickets) {
            this.user = user;
            this.tickets = tickets;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Collection<Ticket> getTickets() {
            return tickets;
        }

        public void setTickets(Collection<Ticket> tickets) {
            this.tickets = tickets;
        }
    }
}
