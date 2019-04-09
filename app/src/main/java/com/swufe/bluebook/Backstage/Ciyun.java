package com.swufe.bluebook.Backstage;

import java.io.Serializable;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

public class Ciyun extends BmobObject implements Serializable {
    private String provinceName;
    private String ciyun;
    private String shoutu;
    private String hengtu;
    private String xiaoxun;
    private String daxue;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCiyun() {
        return ciyun;
    }

    public void setCiyun(String ciyun) {
        this.ciyun = ciyun;
    }

    public String getShoutu() {
        return shoutu;
    }

    public void setShoutu(String shoutu) {
        this.shoutu = shoutu;
    }

    public String getHengtu() {
        return hengtu;
    }

    public void setHengtu(String hengtu) {
        this.hengtu = hengtu;
    }

    public String getXiaoxun() {
        return xiaoxun;
    }

    public void setXiaoxun(String xiaoxun) {
        this.xiaoxun = xiaoxun;
    }

    public String getDaxue() {
        return daxue;
    }

    public void setDaxue(String daxue) {
        this.daxue = daxue;
    }
}
