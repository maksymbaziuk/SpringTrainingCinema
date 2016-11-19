package com.baziuk.spring.common.web.binding;

import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Maks on 11/10/16.
 */
@Component
public class LocalDateTimeDataBinder extends PropertyEditorSupport {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy.HH:mm");

    @Override
    public String getAsText() {
        return ((LocalDateTime) getValue()).format(dateTimeFormatter);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(LocalDateTime.parse(text, dateTimeFormatter));
    }
}
