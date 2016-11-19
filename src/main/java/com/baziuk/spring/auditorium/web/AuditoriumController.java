package com.baziuk.spring.auditorium.web;

import com.baziuk.spring.auditorium.bean.Auditorium;
import com.baziuk.spring.auditorium.service.AuditoriumService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

/**
 * Created by Maks on 11/10/16.
 */
@Controller
@RequestMapping("/aud")
public class AuditoriumController {

    @Autowired
    private AuditoriumService auditoriumService;

    private static final Gson GSON = new Gson();

    @ResponseBody
    @RequestMapping("/all")
    public String getAll(){
        Collection<Auditorium> auditoriums = auditoriumService.getAllAuditoriums();
        return GSON.toJson(auditoriums);
    }

}
