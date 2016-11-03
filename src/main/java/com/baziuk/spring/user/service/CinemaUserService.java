package com.baziuk.spring.user.service;

import com.baziuk.spring.user.bean.User;
import com.baziuk.spring.user.bean.UserRole;
import com.baziuk.spring.user.dao.UserDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Cinema user service. Allows to register, save, remove or find users.
 *
 * @author Maks
 */
@Service("userService")
public class CinemaUserService implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(userDAO.get(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userDAO.getByEmail(email));
    }

    /**
     * Registers common users
     *
     * @param user user bean
     * @return registered user
     * @throws IllegalArgumentException if email not specified
     */
    @Override
    public User registerUser(User user) {
        if (StringUtils.isBlank(user.getEmail()) || user.getBirthday() == null){
            throw new IllegalArgumentException("Required fields not set! Required: email, birthday");
        }
        user.setUserRole(UserRole.REGISTERED);
        return userDAO.create(user);
    }

    @Override
    public boolean removeUser(User user) {
        return user != null ? userDAO.remove(user) : true;
    }

    @Override
    public Collection<User> getAllRegisteredUsers() {
        return userDAO.getAll();
    }

    @Override
    public User saveUser(User user) {
        if (user.getId() == 0){
            return registerUser(user);
        }
        return userDAO.update(user);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
