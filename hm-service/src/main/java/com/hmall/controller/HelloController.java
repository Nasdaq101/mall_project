package com.hmall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("hi")
public class HelloController {

    private final Map<String, AtomicInteger> countMap = new HashMap<>();

    @GetMapping
    public String hello(HttpServletRequest request) throws InterruptedException {
        Thread.sleep(300);
        String ip = request.getRemoteAddr();
        AtomicInteger ai = countMap.get(ip);
        if (ai == null) {
            ai = new AtomicInteger(0);
            countMap.put(ip, ai);
        }
        return String.format("<h5>welcome to the MALL, this is your %d time login!<h5>", ai.incrementAndGet());
    }
}
