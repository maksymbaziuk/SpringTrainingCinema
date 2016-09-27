package com.baziuk.spring.discount.strategy;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.discount.bean.Discount;
import com.baziuk.spring.user.bean.User;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Maks on 9/25/16.
 */
public class EachItemDiscountStrategy extends DiscountStrategy {

    @Override
    public boolean isDiscountable(User user, Collection<Ticket> tickets) {
        DiscountContext discountContext = new DiscountContext(user, tickets);
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression(getEvaluationStrategy());
        StandardEvaluationContext context = new StandardEvaluationContext(discountContext);
        return expression.getValue(context, Boolean.class);
    }

    @Override
    public void applyDiscount(Discount discount) {
        for (Ticket cutTicket : discount.getDiscountableItems()){
            ExpressionParser expressionParser = new SpelExpressionParser();
            Expression expression = expressionParser.parseExpression(getAction());
            StandardEvaluationContext context = new StandardEvaluationContext(cutTicket);
            double discountAmount = expression.getValue(context, Double.class);
            cutTicket.setPrice(cutTicket.getPrice() - discountAmount);
        }
    }

    @Override
    public Discount getDiscount(User user, Collection<Ticket> tickets) {
        Discount discount = new Discount();
        List<Ticket> discountedTickets = new ArrayList<>();
        for (Ticket cutTicket : tickets){
            ExpressionParser expressionParser = new SpelExpressionParser();
            Expression expression = expressionParser.parseExpression(getAction());
            StandardEvaluationContext context = new StandardEvaluationContext(cutTicket);
            double discountAmount = expression.getValue(context, Double.class);
            if (discountAmount != 0){
                discountedTickets.add(cutTicket);
                discount.setDiscountAmount(discount.getDiscountAmount() + discountAmount);
            }

        }
        discount.setDiscountableItems(discountedTickets);
        discount.setDiscountStrategy(this);
        return discount;
    }
}
