package com.swufe.bluebook.Backstage;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class School extends BmobObject implements Serializable{
    private String title;//描述
    private String coverUrl;//封面
    private String cityName;
    private String taskDescription;
    private Boolean isAchieved;
    private String provinceName;


    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Boolean getAchieved() {
        return isAchieved;
    }

    public void setAchieved(Boolean achieved) {
        isAchieved = achieved;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}

