package com.stock.portfolio.model;

import java.util.HashMap;
import java.util.Map;

public class UserPortfolio {
    private String username;
    private double balance;
    private Map<String, Integer> holdings = new HashMap<>();

    public UserPortfolio(String username) {
        this.username = username;
        this.balance = 100000.0;
    }

    public String getUsername() { return username; }
    public double getBalance() { return balance; }
    public Map<String, Integer> getHoldings() { return holdings; }

    public void setBalance(double balance) { this.balance = balance; }
}
