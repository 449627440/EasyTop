package com.swufe.bluebook.Backstage;

import android.content.Intent;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class City extends BmobObject{
    private User user;
    private String CityName;//城市名
    private Integer time;

    public City(User user, String cityName, Integer time) {
        this.user = user;
        CityName = cityName;
        this.time = time;

    }
    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

}
