package com.baziuk.spring.common.web.binding;

import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by Maks on 11/10/16.
 */
@Component
public class EventByIdDataBinder extends PropertyEditorSupport {

    @Autowired
    private EventService eventService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(eventService.getEventById(Long.parseLong(text)).get());
    }

    @Override
    public String getAsText() {
        return Long.toString(((Event)getValue()).getId());
    }
}
