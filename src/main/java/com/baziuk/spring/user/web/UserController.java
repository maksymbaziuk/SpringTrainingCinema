package com.baziuk.spring.user.web;

import com.baziuk.spring.user.bean.User;
import com.baziuk.spring.user.bean.UserRole;
import com.baziuk.spring.user.service.UserService;
import com.baziuk.spring.user.web.bean.UserRegistrationRequest;
import com.baziuk.spring.user.web.bean.UpdateUserRequest;
import com.baziuk.spring.common.web.binding.LocalDateDataBinder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Maks on 11/6/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @RequestMapping("/info/{userId}")
    public ModelAndView getUserInfo(@PathVariable long userId, ModelAndView model){
        Optional<User> user = userService.getUserById(userId);
        model.addObject("user", user.get());
        model.setViewName("user-info");
        return model;
    }

    @RequestMapping("/info")
    public String getUserByEmail(@RequestParam String email, ModelMap model){
        Optional<User> user = userService.getUserByEmail(email);
        model.addAttribute("user", user.get());
        return "user-info";
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.PUT)
    public boolean createNew(@ModelAttribute UserRegistrationRequest userRegistrationRequest){
        //TODO add validation
        User user = new User();
        user.setBirthday(userRegistrationRequest.getBirthday());
        user.setEmail(userRegistrationRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        user = userService.registerUser(user);
        log.info("Created user: " + user.toString());
        return true;
    }

    @RequestMapping("/all")
    public ModelAndView getAllRegisteredUsers(ModelAndView modelAndView){
        Collection<User> users = userService.getAllRegisteredUsers();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("all-users");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute UpdateUserRequest updateUserRequest){
        // TODO No null checks and validation
        User user = userService.getUserById(updateUserRequest.getId()).get();
        user.setEmail(updateUserRequest.getEmail());
        user.setBirthday(updateUserRequest.getBirthday());
        user.setPasswordHash(passwordEncoder.encode(updateUserRequest.getPassword()));
        user.setUserRole(UserRole.valueOf(updateUserRequest.getUserRole()));
        user = userService.saveUser(user);
        return "redirect:/user/info/" + user.getId();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(LocalDate.class, new LocalDateDataBinder());
    }
}
