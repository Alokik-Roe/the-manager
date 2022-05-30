package com.bringtheaction.manager.appData;

public class Notes {
    public String key;
    public int color;
    public String description;

    public Notes(){

    }

    public Notes(String key,int color, String description) {
        this.key = key;
        this.color = color;
        this.description = description;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
