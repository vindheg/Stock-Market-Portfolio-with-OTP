package com.stock.portfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "holdings")
public class HoldingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String stock;
    private int quantity;

    public HoldingEntity() {}

    public HoldingEntity(String username, String stock, int quantity) {
        this.username = username;
        this.stock = stock;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
