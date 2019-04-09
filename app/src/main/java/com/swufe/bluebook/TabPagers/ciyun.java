package com.swufe.bluebook.TabPagers;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class ciyun extends BmobObject implements Serializable {
    private String hengtu;
    private String shoutu;
    private String ciyun;
    private String university;
    private String provinceName;
    private String xiaoxun;

    public String getXiaoxun() {
        return xiaoxun;
    }

    public void setXiaoxun(String xiaoxun) {
        this.xiaoxun = xiaoxun;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getHengtu() {
        return hengtu;
    }

    public void setHengtu(String hengtu) {
        this.hengtu = hengtu;
    }

    public String getShoutu() {
        return shoutu;
    }

    public void setShoutu(String shoutu) {
        this.shoutu = shoutu;
    }

    public String getCiyun() {
        return ciyun;
    }

    public void setCiyun(String ciyun) {
        this.ciyun = ciyun;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
