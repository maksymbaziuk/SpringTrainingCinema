package com.baziuk.spring.auditorium.data.inmemory.dao;

import com.baziuk.spring.auditorium.bean.Auditorium;
import com.baziuk.spring.auditorium.dao.AuditoriumDAO;
import com.baziuk.spring.data.JSONDataPopulator;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple Auditorium DAO. Allows only GET operations!
 *
 * @author Maks
 */
public class AuditoriumInMemoryDAO implements AuditoriumDAO {

    private List<Auditorium> auditoriums = new ArrayList<>();

    private JSONDataPopulator dataPopulator;

    @Override
    public Auditorium create(Auditorium item) {
        throw new NotImplementedException("auditorium creation not implemented");
    }

    @Override
    public Auditorium update(Auditorium item) {
        throw new NotImplementedException("auditorium updating not implemented");
    }

    @Override
    public boolean remove(Auditorium item) {
        throw new NotImplementedException("auditorium removal not implemented");
    }

    @Override
    public Auditorium get(long id) {
        return auditoriums.stream().filter(auditorium -> auditorium.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Auditorium> getAll() {
        return new ArrayList<>(auditoriums);
    }

    @Override
    public Auditorium getByName(String name) {
        return auditoriums.stream().filter(auditorium -> auditorium.getName().equals(name)).findFirst().orElse(null);
    }

    private void initWithData() throws IOException {
        List<Auditorium> data = dataPopulator.getData(new Auditorium[0].getClass());
        data.forEach(cur -> {
            auditoriums.add(cur);
        });
    }

    public void setDataPopulator(JSONDataPopulator dataPopulator) {
        this.dataPopulator = dataPopulator;
    }
}