package com.bringtheaction.manager.appData;

public class Tasks {
    public Boolean complete;
    public String date;
    public String description;
    public String key;
    public String project;
    public String time;
    public String title;

    public Tasks(){}

    public Tasks(Boolean complete, String date, String description, String key, String project, String time, String title) {
        this.complete = complete;
        this.date = date;
        this.description = description;
        this.key = key;
        this.project = project;
        this.time = time;
        this.title = title;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

