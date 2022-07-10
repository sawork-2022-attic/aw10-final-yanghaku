package com.micropos.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private Integer id;

    private List<Item> items;

    public Integer getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", items=" + items + '}';
    }
}
