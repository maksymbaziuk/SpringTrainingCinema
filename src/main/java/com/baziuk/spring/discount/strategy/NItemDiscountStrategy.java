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
public class NItemDiscountStrategy extends EachItemDiscountStrategy {

    private int discountedTicketNumber;

    @Override
    public boolean isDiscountable(User user, Collection<Ticket> tickets) {
        DiscountContext discountContext = new DiscountContext(user, new ArrayList<>(user.getBoughtTickets()));
        for (Ticket curTicket : tickets) {
            discountContext.getTickets().add(curTicket);
            ExpressionParser expressionParser = new SpelExpressionParser();
            Expression expression = expressionParser.parseExpression(getEvaluationStrategy());
            StandardEvaluationContext context = new StandardEvaluationContext(discountContext);
            if(expression.getValue(context, Boolean.class)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void applyDiscount(Discount discount) {
        for (Ticket ticket : discount.getDiscountableItems()){
            ExpressionParser expressionParser = new SpelExpressionParser();
            Expression expression = expressionParser.parseExpression(getAction());
            StandardEvaluationContext context = new StandardEvaluationContext(ticket);
            double discountAmount = expression.getValue(context, Double.class);
            ticket.setPrice(discountAmount);
        }
    }

    @Override
    public Discount getDiscount(User user, Collection<Ticket> tickets) {
        List<Ticket> ticketList = new ArrayList<>(tickets);
        int boughtTickets = user.getBoughtTickets().size();
        List<Ticket> discountable = new ArrayList<>();
        Discount discount = new Discount();
        for (int i = 0 ; i < ticketList.size(); i++){
            if ((boughtTickets + i + 1) % discountedTicketNumber == 0){
                Ticket current = ticketList.get(i);
                discountable.add(current);
                ExpressionParser expressionParser = new SpelExpressionParser();
                Expression expression = expressionParser.parseExpression(getAction());
                StandardEvaluationContext context = new StandardEvaluationContext(current);
                double discountAmount = expression.getValue(context, Double.class);
                discount.setDiscountAmount(discount.getDiscountAmount() + discountAmount);
            }
        }
        discount.setDiscountableItems(discountable);
        discount.setDiscountStrategy(this);
        return discount;
    }

    public int getDiscountedTicketNumber() {
        return discountedTicketNumber;
    }

    public void setDiscountedTicketNumber(int discountedTicketNumber) {
        this.discountedTicketNumber = discountedTicketNumber;
    }
}
