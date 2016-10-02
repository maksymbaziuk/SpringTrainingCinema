package com.baziuk.spring.auditorium.config.inmemory;

import com.baziuk.spring.auditorium.dao.AuditoriumDAO;
import com.baziuk.spring.auditorium.dao.AuditoriumInMemoryDAO;
import com.baziuk.spring.data.JSONDataPopulator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

import java.io.File;

/**
 * Created by Maks on 9/30/16.
 */
@Configuration
@PropertySource("classpath:config/in-memory-data-files.properties")
public class AuditoriumDataLayerConfig {

    @Value("${auditorium.initial.data.file}")
    private File dataFile;

    @Bean(initMethod = "initWithData")
    public AuditoriumDAO auditoriumDAO(){
        AuditoriumInMemoryDAO inMemoryDAO = new AuditoriumInMemoryDAO();
        inMemoryDAO.setDataPopulator(auditoriumDataPopulator());
        return inMemoryDAO;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public JSONDataPopulator auditoriumDataPopulator(){
        JSONDataPopulator dataPopulator = new JSONDataPopulator();
        dataPopulator.setDataFile(dataFile);
        return dataPopulator;
    }

}
