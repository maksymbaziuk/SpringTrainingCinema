package com.baziuk.spring.common.web.binding;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Maks on 11/8/16.
 */
public class LocalDateDataBinder extends PropertyEditorSupport{

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void setAsText(String text) throws IllegalArgumentException{
        setValue(LocalDate.parse(text, dateTimeFormatter));
    }

    @Override
    public String getAsText() throws IllegalArgumentException {
        return ((LocalDate) getValue()).format(dateTimeFormatter);
    }
}
