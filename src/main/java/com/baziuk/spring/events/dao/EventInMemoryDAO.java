package com.baziuk.spring.events.dao;

import com.baziuk.spring.data.JSONDataPopulator;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.Show;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.util.CollectionUtils;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Maks on 9/20/16.
 */
public class EventInMemoryDAO implements EventDAO {

    private static long currentEventMaxId = 1;
    private static long currentShowMaxId = 1;


    private JSONDataPopulator dataPopulator;

    private TreeSet<Event> events = new TreeSet<>();
    private Map<Long, Show> idToShowCache = new HashMap<>();

    @Override
    public Event create(Event item) {
        item.setId(++currentEventMaxId);
        for (Show curShow : item.getSchedule()){
            curShow.setId(++currentShowMaxId);
            createShowIfNotExist(curShow);
        }
        events.add(item);
        return item;
    }

    @Override
    public Event update(Event item) {
        for (Show curShow : item.getSchedule()){
            createShowIfNotExist(curShow);
        }
        return item;
    }

    @Override
    public boolean remove(Event item) {
        if (item != null && events.contains(item)){
            for (Show curShow : item.getSchedule()){
                idToShowCache.remove(curShow.getId());
            }
            events.remove(item);
        }
        return true;
    }

    @Override
    public Event get(long id) {
        return events.stream().filter(event -> event.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Event> getAll() {
        return new ArrayList<>(events);
    }

    @Override
    public Collection<Event> getForDateRange(LocalDateTime from, LocalDateTime to) {
        return events.stream().filter(event ->
            event.getSchedule().stream().anyMatch(show ->
                    show.getStart().isAfter(from) && show.getEnd().isBefore(to))
        ).collect(Collectors.toList());
    }

    @Override
    public Event getByName(String name) {
        return events.stream().filter(event -> event.getName().equals(name)).findFirst().orElse(null);
    }

    private void createShowIfNotExist(Show show){
        if (show.getId() == 0){
            show.setId(++currentShowMaxId);
        }
        idToShowCache.put(show.getId(), show);
    }

    @Override
    public Show getShowById(Long id){
        return idToShowCache.get(id);
    }

    private void initWithData() throws FileNotFoundException {
        List<Event> data = dataPopulator.getData(new Event[0].getClass());
        data.forEach(cur -> {
            if (currentEventMaxId <= cur.getId()){
                currentEventMaxId = cur.getId();
            }
            for (Show curShow : cur.getSchedule()) {
                if (currentShowMaxId <= curShow.getId()){
                    currentShowMaxId = curShow.getId();
                }
                createShowIfNotExist(curShow);
            }
            events.add(cur);
        });
    }

    public void setDataPopulator(JSONDataPopulator dataPopulator) {
        this.dataPopulator = dataPopulator;
    }
}
