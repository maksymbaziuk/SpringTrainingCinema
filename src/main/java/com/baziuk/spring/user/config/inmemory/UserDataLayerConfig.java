package com.baziuk.spring.user.config.inmemory;

import com.baziuk.spring.data.JSONDataPopulator;
import com.baziuk.spring.user.data.inmemory.UserJSONDataPopulator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

import java.io.File;

/**
 * Created by Maks on 9/30/16.
 */
@Configuration
@ComponentScan(basePackages = { "com.baziuk.spring.user.data.inmemory.dao" })
@PropertySource("classpath:config/in-memory-data-files.properties")
public class UserDataLayerConfig {

    @Value("${user.initial.data.file}")
    private File dataFile;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public JSONDataPopulator userJSONDataPopulator(){
        JSONDataPopulator dataPopulator = new UserJSONDataPopulator();
        dataPopulator.setDataFile(dataFile);
        return dataPopulator;
    }

}
