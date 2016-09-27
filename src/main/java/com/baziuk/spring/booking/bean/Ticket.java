package com.baziuk.spring.booking.bean;

import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.Show;

/**
 * Created by Maks on 9/25/16.
 */
public class Ticket {

    private long id;
    private Event event;
    private Show show;
    private double price;
    private int sitNumber;

    public Ticket(){
    }

    public Ticket(Event event, Show show, int sitNumber) {
        this.event = event;
        this.show = show;
        this.sitNumber = sitNumber;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", event=" + event +
                ", show=" + show +
                ", price=" + price +
                ", sitNumber=" + sitNumber +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSitNumber() {
        return sitNumber;
    }

    public void setSitNumber(int sitNumber) {
        this.sitNumber = sitNumber;
    }
}
