package com.kelompok13.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HealthController {
    @GetMapping("/health")
    public Map<String,Object> healthCheck(){
        Map<String,Object> map = new HashMap<>();
        map.put("status", "UP");
        map.put("message", "Wild Card Backend is running");
        map.put("timestamp", System.currentTimeMillis());
        return map;
    }

    @GetMapping("/info")
    public  Map<String, Object> appInfo(){
        Map<String,Object> map = new HashMap<>();
        map.put("Nama Aplikasi", "Wild Card");
        map.put("Version", "BETA 1.0.0");
        map.put("Description", "Card Game");

        Map<String,String> endpoints = new HashMap<>();
        endpoints.put("players", "/players");
        endpoints.put("scores", "/scores");
        endpoints.put("leaderboard", "/leaderboard");
        endpoints.put("health", "health");

        map.put("endpoints", endpoints);

        return map;
    }
}
