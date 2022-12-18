package com.sun.task3.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestContrller {

    @RequestMapping(value="restForGetJson")
    @ResponseBody
    public String restForGetJson(){
        RestTemplate restTemplate = new RestTemplate();
        String url ="http://localhost:8080/person/getJson";
        String body = restTemplate.getForEntity(url, String.class).getBody();
        return body;
    }

    @RequestMapping(value="restForGetXml")
    @ResponseBody
    public String restForGetXml(){
        RestTemplate restTemplate = new RestTemplate();
        String url ="http://localhost:8080/person/getXml";
        String body = restTemplate.getForEntity(url, String.class).getBody();
        return body;
    }

}
