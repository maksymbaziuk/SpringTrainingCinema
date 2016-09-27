package com.baziuk.spring.events.bean;

import com.baziuk.spring.auditorium.bean.Auditorium;

import java.time.LocalDateTime;

/**
 * Created by Maks on 9/25/16.
 */
public class Show implements Comparable<Show>{

    private long id;
    private Auditorium auditorium;
    private LocalDateTime start;
    private LocalDateTime end;

    @Override
    public int compareTo(Show o) {
        if (start == null && o.start == null)
            return 0;
        else if (start == null && o.start != null)
            return -1;
        else if (start != null && o.start == null)
            return 1;
        else
            return start.compareTo(o.start);
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", auditorium=" + auditorium +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
