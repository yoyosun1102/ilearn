package com.sun.task2.controller;

import com.sun.task2.entity.Person;
import com.sun.task2.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/person")
public class GetPersonInfo {

    @GetMapping(value = "/getJson")
    @ResponseBody
    public Person getPersonInfoForJson(){
        Person person = generatePerson();
        return person;
    }

    @GetMapping(value = "/getXml")
    @ResponseBody
    public User getPersonInfoForXml(){
        User user = generateUser();
        return user;
    }

    public Person generatePerson(){
        Person person = new Person();
        person.setId("001");
        person.setName("zhangsan");
        person.setAge(25);
        return person;
    }

    public User generateUser(){
        User user = new User();
        user.setId("002");
        user.setName("lisi");
        user.setAge(29);
        return user;
    }
}
