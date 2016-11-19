package com.baziuk.spring.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Maks on 11/8/16.
 */
@Controller
public class CommonController {

    @RequestMapping("/")
    public String getCommonInfo(){
        return "index";
    }
}
