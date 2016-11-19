package com.baziuk.spring.booking.web.bean;

import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.Show;

/**
 * Created by Maks on 11/8/16.
 */
public class BookingRequest {

    private Event event;
    private Show show;
    private String places;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "BookingRequest{" +
                "event=" + event +
                ", show=" + show +
                ", places='" + places + '\'' +
                '}';
    }
}
