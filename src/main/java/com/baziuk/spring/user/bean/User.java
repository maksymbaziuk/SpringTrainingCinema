package com.baziuk.spring.user.bean;

import com.baziuk.spring.booking.bean.Ticket;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Maks on 9/20/16.
 */
public class User {

    private long id;
    private String email;
    private LocalDate birthday;
    private UserRole userRole;
    private List<Ticket> boughtTickets;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", userRole=" + userRole +
                ", boughtTickets=" + boughtTickets +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<Ticket> getBoughtTickets() {
        return boughtTickets;
    }

    public void setBoughtTickets(List<Ticket> boughtTickets) {
        this.boughtTickets = boughtTickets;
    }
}
