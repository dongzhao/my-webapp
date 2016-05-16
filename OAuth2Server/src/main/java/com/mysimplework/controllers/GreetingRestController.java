package com.mysimplework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by dzhao on 2/11/2015.
 */
@RestController
public class GreetingRestController {

    @RequestMapping(value="/rest/api/v1/greeting/{name}", method= RequestMethod.GET)
    public ResponseEntity<String> sayHello(@PathVariable("name") String name){
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ");
        sb.append(name);
        sb.append(", welcome to my world!");
        return new ResponseEntity<String>(sb.toString(), HttpStatus.OK);
    }

    @RequestMapping(value="/rest/api/v1/greeting", method= RequestMethod.POST)
    public ResponseEntity<String> postHello(@RequestBody @Valid final String name){
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ");
        sb.append(name);
        sb.append(", welcome to my world!");
        return new ResponseEntity<String>(sb.toString(), HttpStatus.OK);
    }
}
