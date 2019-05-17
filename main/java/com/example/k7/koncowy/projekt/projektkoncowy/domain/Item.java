package com.example.k7.koncowy.projekt.projektkoncowy.domain;

public class Item {
    private String saveDate;
    private int size;
    private String name;

    public Item(String saveDate, int size, String name)
    {
        this.saveDate = saveDate;
        this.size = size;
        this.name = name;
    }

    public String getSaveDate() {
        return saveDate;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}
