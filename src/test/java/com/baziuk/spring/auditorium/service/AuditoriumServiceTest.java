package com.baziuk.spring.auditorium.service;

import com.baziuk.spring.auditorium.bean.Auditorium;
import com.baziuk.spring.auditorium.config.AuditoriumServiceLayerConfig;
import com.baziuk.spring.data.H2DBConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by Maks on 9/26/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {H2DBConfig.class, AuditoriumServiceLayerConfig.class})
@DirtiesContext
public class AuditoriumServiceTest {

    private static final String EXISTEN_AUDITORIUM_NAME = "red";
    private static final String NON_EXISTEN_AUDITORIUM_NAME = "greenwich";
    private static final Long EXISTEN_AUDITORIUM_ID = 1l;
    private static final Long NON_EXISTEN_AUDITORIUM_ID = 111111l;

    @Autowired
    public AuditoriumService service;

    @Test
    public void getByName(){
        Optional<Auditorium> auditorium = service.getAuditoriumByName(EXISTEN_AUDITORIUM_NAME);
        assertTrue(auditorium.isPresent());
        assertEquals(EXISTEN_AUDITORIUM_NAME, auditorium.get().getName());
    }

    @Test
    public void getByNonExistenName(){
        Optional<Auditorium> auditorium = service.getAuditoriumByName(NON_EXISTEN_AUDITORIUM_NAME);
        assertFalse(auditorium.isPresent());
    }

    @Test
    public void getByNullName(){
        Optional<Auditorium> auditorium = service.getAuditoriumByName(null);
        assertFalse(auditorium.isPresent());
    }

    @Test
    public void getByEmptyName(){
        Optional<Auditorium> auditorium = service.getAuditoriumByName("");
        assertFalse(auditorium.isPresent());
    }

    @Test
    public void getById(){
        Optional<Auditorium> auditorium = service.getAuditoriumById(EXISTEN_AUDITORIUM_ID);
        assertTrue(auditorium.isPresent());
        assertEquals(EXISTEN_AUDITORIUM_NAME, auditorium.get().getName());
    }

    @Test
    public void getByNonExistenId(){
        Optional<Auditorium> auditorium = service.getAuditoriumById(NON_EXISTEN_AUDITORIUM_ID);
        assertFalse(auditorium.isPresent());
    }

    @Test
    public void getByZeroId(){
        Optional<Auditorium> auditorium = service.getAuditoriumById(0);
        assertFalse(auditorium.isPresent());
    }

    @Test
    public void getAllAuditoriums(){
        Collection<Auditorium> auditoriums = service.getAllAuditoriums();
        assertEquals(3, auditoriums.size());
    }
}
