package com.github.lock.example.controller;

import com.github.lock.example.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wkx
 */
@RestController
public class ExampleController {
    @Autowired
    private ExampleService exampleService;

    @GetMapping("/test1")
    public String test1(@RequestParam String i) {
        exampleService.test1(i);
        return i;
    }

    @GetMapping("/test2")
    public String test2(@RequestParam String i) {
        exampleService.test2(i);
        return i;
    }
}
