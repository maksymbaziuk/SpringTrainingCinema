package com.baziuk.spring.user.service;

import com.baziuk.spring.auditorium.config.AuditoriumServiceLayerConfig;
import com.baziuk.spring.booking.config.BookingServiceLayerConfig;
import com.baziuk.spring.data.H2DBConfig;
import com.baziuk.spring.discount.config.DiscountServiceLayerConfig;
import com.baziuk.spring.events.config.EventServiceLayerConfig;
import com.baziuk.spring.user.bean.User;
import com.baziuk.spring.user.bean.UserRole;
import com.baziuk.spring.user.config.UserServiceLayerConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by Maks on 9/26/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        H2DBConfig.class,
        AuditoriumServiceLayerConfig.class,
        EventServiceLayerConfig.class,
        UserServiceLayerConfig.class,
        BookingServiceLayerConfig.class,
        DiscountServiceLayerConfig.class})
@DirtiesContext
public class UserServiceTest {

    private static final String NEW_USER_EMAIL = "failemail@test.test";
    private static final String EXISTEN_USER_EMAIL = "asdasd@qwe.qwe";

    @Autowired
    public UserService userService;

    @Test
    public void getExistingUserById(){
        Optional<User> user = userService.getUserById(1);
        assertNotNull(user);
        assertNotNull(user.get());
    }

    @Test
    public void getFailUserById(){
        Optional<User> user = userService.getUserById(100);
        assertNotNull(user);
        assertFalse(user.isPresent());
    }

    @Test
    public void getUserByEmptyEmail(){
        Optional<User> user = userService.getUserByEmail("");
        assertNotNull(user);
        assertFalse(user.isPresent());
    }

    @Test
    public void getUserByNullEmail(){
        Optional<User> user = userService.getUserByEmail(null);
        assertNotNull(user);
        assertFalse(user.isPresent());
    }

    @Test
    public void getUserByFailEmail(){
        Optional<User> user = userService.getUserByEmail(NEW_USER_EMAIL);
        assertNotNull(user);
        assertFalse(user.isPresent());
    }

    @Test
    public void getUserByEmail(){
        Optional<User> user = userService.getUserByEmail(EXISTEN_USER_EMAIL);
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(EXISTEN_USER_EMAIL, user.get().getEmail());
    }

    @Test
    @DirtiesContext
    public void registrationSuccess(){
        User user = new User();
        user.setBirthday(LocalDate.now());
        user.setEmail(NEW_USER_EMAIL);
        User result = userService.registerUser(user);
        assertNotNull(result);
        assertNotEquals(0, result.getId());
        assertEquals(UserRole.REGISTERED, result.getUserRole());
        assertEquals(NEW_USER_EMAIL, result.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerWithoutEmail(){
        User user = new User();
        user.setBirthday(LocalDate.now());
        userService.registerUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerWithoutBirthday(){
        User user = new User();
        user.setEmail(NEW_USER_EMAIL);
        userService.registerUser(user);
    }

    @Test
    @DirtiesContext
    public void registerAdmin(){
        User user = new User();
        user.setEmail(NEW_USER_EMAIL);
        user.setBirthday(LocalDate.now());
        user.setUserRole(UserRole.ADMIN);
        User result = userService.registerUser(user);
        assertEquals(UserRole.REGISTERED, result.getUserRole());
    }

    @Test
    public void getAllRegisteredUsers(){
        Collection<User> users = userService.getAllRegisteredUsers();
        assertEquals(3, users.size());
    }

    @Test
    @DirtiesContext
    public void removeExistingUser(){
        Optional<User> userToRemove = userService.getUserById(2);
        boolean result = userService.removeUser(userToRemove.get());
        assertTrue(result);
        Collection<User> users = userService.getAllRegisteredUsers();
        assertFalse(users.contains(userToRemove.get()));
        assertEquals(2, users.size());
    }

    @Test
    public void removeNonExistingUser(){
        User userToRemove = userService.getUserById(20).orElse(null);
        boolean result = userService.removeUser(userToRemove);
        assertTrue(result);
        Collection<User> users = userService.getAllRegisteredUsers();
        assertFalse(users.contains(userToRemove));
        assertEquals(3, users.size());
    }

    @Test
    @DirtiesContext
    public void saveNewUser(){
        // If new user - save should create a user
        User user = new User();
        user.setEmail(NEW_USER_EMAIL);
        user.setBirthday(LocalDate.now());
        User result = userService.saveUser(user);
        assertNotNull(result);
        assertNotEquals(0, result.getId());
        User expected = userService.getUserByEmail(NEW_USER_EMAIL).get();
        assertEquals(expected.getId(), result.getId());
    }

    @Test
    @DirtiesContext
    public void saveOldUser(){
        User user = userService.getUserByEmail(EXISTEN_USER_EMAIL).get();
        user.setBirthday(LocalDate.now());
        User result = userService.saveUser(user);
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }
}
