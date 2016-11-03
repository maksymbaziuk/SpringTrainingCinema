package com.baziuk.spring.events.config.inmemory;

import com.baziuk.spring.data.JSONDataPopulator;
import com.baziuk.spring.events.data.inmemory.EventJSONDataPopulator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

import java.io.File;

/**
 * Created by Maks on 9/30/16.
 */
@Configuration
@ComponentScan({"com.baziuk.spring.events.data.inmemory.dao", "com.baziuk.spring.events.data"})
@PropertySource("classpath:config/in-memory-data-files.properties")
public class EventDataLayerConfig {

    @Value("${event.initial.data.file}")
    private File dataFile;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public JSONDataPopulator eventDataPopulator(){
        EventJSONDataPopulator eventJSONDataPopulator = new EventJSONDataPopulator();
        eventJSONDataPopulator.setDataFile(dataFile);
        return eventJSONDataPopulator;
    }

}
