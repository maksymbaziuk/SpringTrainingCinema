package com.baziuk.spring.booking.data;

import com.baziuk.spring.booking.bean.Ticket;
import com.baziuk.spring.data.JSONDataPopulator;
import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.bean.Show;
import com.baziuk.spring.events.dao.EventDAO;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

/**
 * Created by Maks on 9/25/16.
 */
public class TicketJSONDataPopulator extends JSONDataPopulator<Ticket> {

    private EventDAO eventDAO;

    @Override
    protected GsonBuilder constructBuilder() {
        GsonBuilder builder = super.constructBuilder();
        builder.registerTypeAdapter(Event.class, (JsonDeserializer<Event>) (json, type, jsonDeserializationContext) ->
        {
            Long eventId = json.getAsJsonPrimitive().getAsLong();
            return eventDAO.get(eventId);
        }).registerTypeAdapter(Show.class, (JsonDeserializer<Show>) (json, type, jsonDeserializationContext) ->
        {
            Long showId = json.getAsJsonPrimitive().getAsLong();
            return eventDAO.getShowById(showId);
        });
        return builder;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }
}
