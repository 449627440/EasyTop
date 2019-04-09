package com.swufe.bluebook.PersonalCenter.Photo.CityList;

public class MyPlace {
    String cityName,picUrl;

    public MyPlace(String cityName, String picUrl) {
        this.cityName = cityName;
        this.picUrl = picUrl;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
