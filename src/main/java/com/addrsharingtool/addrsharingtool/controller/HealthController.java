package com.addrsharingtool.addrsharingtool.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.addrsharingtool.addrsharingtool.constant.APIPath.HEALTH_CHECK;

@RestController
public class HealthController {

    @GetMapping(value = HEALTH_CHECK)
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Service is up!");
    }

}