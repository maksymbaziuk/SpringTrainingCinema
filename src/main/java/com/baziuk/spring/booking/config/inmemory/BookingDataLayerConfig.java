package com.baziuk.spring.booking.config.inmemory;

import com.baziuk.spring.booking.data.TicketJSONDataPopulator;
import com.baziuk.spring.data.JSONDataPopulator;
import com.baziuk.spring.events.dao.EventDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.File;

/**
 * Created by Maks on 10/2/16.
 */
@Configuration
@ComponentScan("com.baziuk.spring.booking.dao")
public class BookingDataLayerConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public JSONDataPopulator ticketJSONDataPopulator(@Value("data/initial/tickets.json") File dataFile, @Qualifier("eventDAO") EventDAO eventDAO){
        TicketJSONDataPopulator dataPopulator = new TicketJSONDataPopulator();
        dataPopulator.setDataFile(dataFile);
        dataPopulator.setEventDAO(eventDAO);
        return dataPopulator;
    }


}
