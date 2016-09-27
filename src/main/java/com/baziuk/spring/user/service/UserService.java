package com.baziuk.spring.user.service;

import com.baziuk.spring.user.bean.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Maks on 9/19/16.
 */
public interface UserService {

    Optional<User> getUserById(long id);
    Optional<User> getUserByEmail(String email);
    User registerUser(User user);
    boolean removeUser(User user);
    Collection<User> getAllRegisteredUsers();
    User saveUser(User user);
}
