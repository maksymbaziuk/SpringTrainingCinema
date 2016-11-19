package com.baziuk.spring.booking.web.bean;

import com.baziuk.spring.user.bean.User;

/**
 * Created by Maks on 11/9/16.
 */
public class UserBookingRequest extends BookingRequest {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserBookingRequest{" +
                "user=" + user + "," +
                "event=" + getEvent() + "," +
                "show=" + getShow() + "," +
                "places=" + getPlaces() +
                '}';
    }
}
