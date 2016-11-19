package com.baziuk.spring.user.web.bean;

import java.time.LocalDate;

/**
 * Created by Maks on 11/8/16.
 */
public class UserRegistrationRequest {

    private String email;
    private String password;
    private LocalDate birthday;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
