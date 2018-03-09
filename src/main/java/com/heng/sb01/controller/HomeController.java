package com.heng.sb01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String toIndex() {
        return "/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin() {
        return "/login";
    }
}
