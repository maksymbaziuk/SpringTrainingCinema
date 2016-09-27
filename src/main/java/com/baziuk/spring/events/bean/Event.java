package com.baziuk.spring.events.bean;

import java.util.TreeSet;

/**
 * Created by Maks on 9/20/16.
 */
public class Event implements Comparable<Event>{

    private long id;
    private String name;
    private EventRating eventRating;
    private TreeSet<Show> schedule;
    private double price;

    public Event(){
    }

    @Override
    public int compareTo(Event o) {
        return schedule.first().getStart().compareTo(o.getSchedule().first().getStart());
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eventRating=" + eventRating +
                ", schedule=" + schedule +
                ", price=" + price +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventRating getEventRating() {
        return eventRating;
    }

    public void setEventRating(EventRating eventRating) {
        this.eventRating = eventRating;
    }

    public TreeSet<Show> getSchedule() {
        return schedule;
    }

    public void setSchedule(TreeSet<Show> schedule) {
        this.schedule = schedule;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
