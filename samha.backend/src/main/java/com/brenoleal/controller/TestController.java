package com.brenoleal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/teste")
public class TestController {

    @GetMapping(value = "{id}")
    public String getTestValue(@PathVariable(value = "id", required = false) String id){
        return "O valor é: " + id;
    }
}
