package com.trungtamjava.hello1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @Autowired
    MessageSource messageSource;

    @GetMapping("/hello")
    public String home(){
        System.out.println(messageSource.getMessage("msg.hello",null,null));
        return "/hello";
    }
}
