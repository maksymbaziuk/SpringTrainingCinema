package com.baziuk.spring.booking.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Maks on 11/24/16.
 */
@Controller
@RequestMapping("/person")
public class TestController {

    public static class Person{
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }


    @RequestMapping(value = {"/add", "/edit"}, method = {RequestMethod.POST})
    public String addPerson(@ModelAttribute("person") Person person) {
        return "redirect:view?id=" + person.getId();
    }

    @ModelAttribute("person")
    public Person getPerson(@RequestParam(value = "id", required = false) Integer id) {
        Person person = null;
        if (id != null) {
            person= new Person();
            person.setId(id);
        }
        return person == null ? new Person() : person;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String viewPersonForm() {
        return "person/view";
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.GET})
    public String showEditForm() {
        return "/person/edit";
    }
}
