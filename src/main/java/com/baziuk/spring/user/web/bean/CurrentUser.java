package com.baziuk.spring.user.web.bean;

import com.baziuk.spring.user.bean.User;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Created by Maks on 11/19/16.
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getUserRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    /*public Role getRole() {
        return user.getUserRole();
    }*/

}
