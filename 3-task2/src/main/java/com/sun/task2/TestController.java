package com.sun.task2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Value("${dev.name}")
    private String name;

    @Value("${test}")
    private String test;

    @RequestMapping("/apollo")
    public String apollo() {
        return "value " + name +  ",test" + test;
    }
}
