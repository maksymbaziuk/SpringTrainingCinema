package com.baziuk.spring.common.web.bean;

import java.time.LocalDateTime;

/**
 * Created by Maks on 11/10/16.
 */
public class DateTimeRange {

    private LocalDateTime from;
    private LocalDateTime to;

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "DateTimeRange{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
