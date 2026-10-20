package com.aws.learn.controller;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class UserController {

    @GetMapping("hello")
    public String getHello(){
        return "User Said Hello";
    }

    @GetMapping("hello/user")
    public String hello(@RequestParam String name){
        return name +" Said Hello";
    }
}
