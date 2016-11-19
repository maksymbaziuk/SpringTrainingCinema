package com.baziuk.spring.events.web;

import com.baziuk.spring.events.bean.Event;
import com.baziuk.spring.events.dao.EventDAO;
import com.baziuk.spring.events.data.inmemory.EventJSONDataPopulator;
import com.baziuk.spring.user.web.UserFeedController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maks on 11/12/16.
 */
@Controller
@RequestMapping("/eventfeed")
public class EventFeedController {

    private static final Logger log = Logger.getLogger(UserFeedController.class);

    @Autowired
    private EventDAO eventDAO;

    @ResponseBody
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public String load(@RequestParam("file") MultipartFile[] files) throws Exception{
        log.info("Event file upload " + Arrays.stream(files).map(MultipartFile::getOriginalFilename).collect(Collectors.joining(",")));
        for (MultipartFile file : files){
            EventJSONDataPopulator dataPopulator = new EventJSONDataPopulator();
            dataPopulator.setInputStream(file.getInputStream());
            List<Event> events = dataPopulator.getData(new Event[0].getClass());
            events.forEach(event -> eventDAO.create(event));
        }
        return "Successfully uploaded event data files: " + Arrays.stream(files).map(MultipartFile::getOriginalFilename).collect(Collectors.joining(","));
    }
}
