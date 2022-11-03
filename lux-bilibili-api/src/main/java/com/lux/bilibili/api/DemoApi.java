package com.lux.bilibili.api;

import com.lux.bilibili.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DemoApi {

    @Autowired
    private DemoService demoService;

    @GetMapping("/query")
    public Map<String, Object> query(Long id) {
        return demoService.query(id);
    }
}
