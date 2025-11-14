package com.stock.portfolio.model;

public class TradeRequest {
    private String stock;
    private int quantity;

    public String getStock() { return stock; }
    public void setStock(String stock) { this.stock = stock; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
