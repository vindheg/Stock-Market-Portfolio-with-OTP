package com.stock.portfolio.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UsernameEmailMapper {

    private final Map<String, String> userEmailMap = new HashMap<>();

    public UsernameEmailMapper() {
        userEmailMap.put("Vindhya", "vindheg2025@gmail.com");
        userEmailMap.put("Anagha", "anaghegde19@gmail.com");
        userEmailMap.put("01fe23bcs160", "01fe23bcs160@kletech.ac.in");
    }

    public String getEmailForUsername(String username) {
        return userEmailMap.get(username);
    }
}
