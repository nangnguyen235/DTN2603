package org.example.buoi1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    // http
    @GetMapping("/hello")
   public String hello() { return "hello world";
    }
}


// API: khai niem


