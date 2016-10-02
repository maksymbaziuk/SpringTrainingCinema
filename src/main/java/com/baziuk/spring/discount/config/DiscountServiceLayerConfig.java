package com.baziuk.spring.discount.config;

import com.baziuk.spring.discount.config.strategy.DiscountStrategyConfig;
import com.baziuk.spring.discount.service.DiscountCinemaService;
import com.baziuk.spring.discount.service.DiscountService;
import com.baziuk.spring.discount.strategy.DiscountStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Maks on 10/2/16.
 */
@Configuration
@Import(DiscountStrategyConfig.class)
public class DiscountServiceLayerConfig {

    @Bean
    public DiscountService discountService(@Qualifier("eachNTicketDiscount")DiscountStrategy eachNTicketDiscount,
                                           @Qualifier("birthdayDiscount") DiscountStrategy birthdayDiscount){
        DiscountCinemaService discountService = new DiscountCinemaService();
        discountService.getStrategies().add(birthdayDiscount);
        discountService.getStrategies().add(eachNTicketDiscount);
        return discountService;
    }

}
