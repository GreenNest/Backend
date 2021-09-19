package com.example.GreenNest.response;

public class ReorderLevelResponse {

    private long id;
    private String name;
    private int amount;
    private int reorder_level;

    public ReorderLevelResponse() {
    }

    public ReorderLevelResponse(String name, int amount, int reorder_level) {
        this.name = name;
        this.amount = amount;
        this.reorder_level = reorder_level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getReorder_level() {
        return reorder_level;
    }

    public void setReorder_level(int reorder_level) {
        this.reorder_level = reorder_level;
    }
}
