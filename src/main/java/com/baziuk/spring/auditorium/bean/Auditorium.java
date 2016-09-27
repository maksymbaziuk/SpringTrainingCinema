package com.baziuk.spring.auditorium.bean;

import java.util.List;

/**
 * Created by Maks on 9/20/16.
 */
public class Auditorium {

    private long id;
    private String name;
    private int sits;
    private List<Integer> vipSits;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSits() {
        return sits;
    }

    public void setSits(int sits) {
        this.sits = sits;
    }

    public List<Integer> getVipSits() {
        return vipSits;
    }

    public void setVipSits(List<Integer> vipSits) {
        this.vipSits = vipSits;
    }

    @Override
    public String toString() {
        return "Auditorium{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sits=" + sits +
                ", vipSits=" + vipSits +
                '}';
    }
}
