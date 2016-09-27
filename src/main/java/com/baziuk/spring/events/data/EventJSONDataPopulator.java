package com.baziuk.spring.events.data;

import com.baziuk.spring.auditorium.bean.Auditorium;
import com.baziuk.spring.auditorium.dao.AuditoriumDAO;
import com.baziuk.spring.data.JSONDataPopulator;
import com.baziuk.spring.events.bean.Event;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

/**
 * Created by Maks on 9/25/16.
 */
public class EventJSONDataPopulator extends JSONDataPopulator<Event> {

    private AuditoriumDAO auditoriumDAO;

    @Override
    protected GsonBuilder constructBuilder() {
        GsonBuilder builder = super.constructBuilder();
        builder.registerTypeAdapter(Auditorium.class, (JsonDeserializer<Auditorium>) (json, type, jsonDeserializationContext) ->
        {
            Long auditoriumId = json.getAsJsonPrimitive().getAsLong();
            return auditoriumDAO.get(auditoriumId);
        });
        return builder;
    }

    public AuditoriumDAO getAuditoriumDAO() {
        return auditoriumDAO;
    }

    public void setAuditoriumDAO(AuditoriumDAO auditoriumDAO) {
        this.auditoriumDAO = auditoriumDAO;
    }
}
