package com.swufe.bluebook.Backstage;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class province extends BmobObject implements Serializable {
    private String city;
    private String province;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
