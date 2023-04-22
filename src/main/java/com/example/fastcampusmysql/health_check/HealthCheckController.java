package com.example.fastcampusmysql.health_check;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HealthCheckController {

    @GetMapping("/health_check")
    public String helloWorld() {
        return "success";
    }
}
