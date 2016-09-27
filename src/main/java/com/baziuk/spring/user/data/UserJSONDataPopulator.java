package com.baziuk.spring.user.data;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.booking.dao.BookingDAO;
import com.baziuk.spring.data.JSONDataPopulator;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDate;

/**
 * Created by Maks on 9/25/16.
 */
public class UserJSONDataPopulator extends JSONDataPopulator {

    private BookingDAO bookingDAO;

    @Override
    protected GsonBuilder constructBuilder() {
        GsonBuilder builder = super.constructBuilder();
        builder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)(json, type, jsonContext) -> {
            String date = json.getAsJsonPrimitive().getAsString();
            return LocalDate.parse(date);
        }).registerTypeAdapter(Ticket.class, (JsonDeserializer<Ticket>)(json, type, jsonContext) -> {
            Long ticketId = json.getAsJsonPrimitive().getAsLong();
            return bookingDAO.get(ticketId);
        });
        return builder;
    }

    public BookingDAO getBookingDAO() {
        return bookingDAO;
    }

    public void setBookingDAO(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }
}
