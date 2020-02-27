package com.zp.code.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/health")
public class HealthController extends BaseController {

    @RequestMapping
    public Object checkHealth() {
        return success();
    }

}
