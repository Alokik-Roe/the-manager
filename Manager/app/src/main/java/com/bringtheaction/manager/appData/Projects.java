package com.bringtheaction.manager.appData;

public class Projects {
    public String key;
    public String projectName;

    public Projects(){

    }
    public Projects(String key,String projectName) {
        this.key = key;
        this.projectName = projectName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
