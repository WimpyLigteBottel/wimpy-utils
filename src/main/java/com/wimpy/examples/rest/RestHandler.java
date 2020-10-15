package com.wimpy.examples.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestHandler {


    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;


    @GetMapping(value = "/help")
    public List<String> show() {
        return this.requestMappingHandlerMapping.getHandlerMethods().keySet().stream().map(RequestMappingInfo::toString).collect(Collectors.toList());
    }

}
