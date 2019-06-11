package com.peter.bean;

public class Condition {
    private Integer id;

    private String item;

    public Condition(Integer id, String item) {
        this.id = id;
        this.item = item;
    }

    public Condition() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item == null ? null : item.trim();
    }
}