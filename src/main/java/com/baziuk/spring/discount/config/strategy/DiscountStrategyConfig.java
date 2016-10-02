package com.baziuk.spring.discount.config.strategy;

import com.baziuk.spring.discount.strategy.EachItemDiscountStrategy;
import com.baziuk.spring.discount.strategy.NItemDiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

/**
 * Created by Maks on 10/2/16.
 */
@Configuration
@PropertySource("classpath:config/discount-strategies.properties")
public class DiscountStrategyConfig {

    @Autowired
    private Environment env;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public EachItemDiscountStrategy birthdayDiscount(){
        EachItemDiscountStrategy discountStrategy = new EachItemDiscountStrategy();
        discountStrategy.setEvaluationStrategy(env.getRequiredProperty("birthday.evaluationStrategy"));
        discountStrategy.setAction(env.getRequiredProperty("birthday.action"));
        return discountStrategy;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public NItemDiscountStrategy eachNTicketDiscount(){
        NItemDiscountStrategy discountStrategy = new NItemDiscountStrategy();
        discountStrategy.setAction(env.getRequiredProperty("eachNTicketDiscount.action"));
        discountStrategy.setEvaluationStrategy(env.getRequiredProperty("eachNTicketDiscount.evaluationStrategy"));
        discountStrategy.setDiscountedTicketNumber(Integer.parseInt(env.getRequiredProperty("eachNTicketDiscount.discountedTicketNumber")));
        return discountStrategy;
    }
}
