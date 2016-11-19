package com.baziuk.spring.user.web;

import com.baziuk.spring.user.bean.User;
import com.baziuk.spring.user.dao.UserDAO;
import com.baziuk.spring.user.data.inmemory.UserJSONDataPopulator;
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
@RequestMapping("/userfeed")
public class UserFeedController {

    private static final Logger log = Logger.getLogger(UserFeedController.class);

    /* Get back to it!! RequestPart - Auto mapping
    public void load(@RequestPart String part){

    }*/

    @Autowired
    private UserDAO userDAO;

    @ResponseBody
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public String load(@RequestParam("file") MultipartFile[] files) throws Exception{
        log.info("User file upload " + Arrays.stream(files).map(MultipartFile::getOriginalFilename).collect(Collectors.joining(",")));
        for (MultipartFile file : files){
            UserJSONDataPopulator dataPopulator = new UserJSONDataPopulator();
            dataPopulator.setInputStream(file.getInputStream());
            List<User> users = dataPopulator.getData(new User[0].getClass());
            users.forEach(user -> userDAO.create(user));
        }
        return "Successfully uploaded user data files: " + Arrays.stream(files).map(MultipartFile::getOriginalFilename).collect(Collectors.joining(","));
    }
}
