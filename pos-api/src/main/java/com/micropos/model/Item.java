package com.micropos.model;

import java.io.Serializable;

public class Item implements Serializable {
    private final String productId;
    private int quantity;

    public Item(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" + "productId='" + productId + '\'' + ", quantity=" + quantity + '}';
    }
}
