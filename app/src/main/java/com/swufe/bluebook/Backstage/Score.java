package com.swufe.bluebook.Backstage;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Score extends BmobObject implements Serializable {
    private String university;
    private String year;
    private Integer minScore;
    private String type;
    private String province;
    private String order;
    private Integer aveScore;
    private Integer maxScore;

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public Integer getAveScore() {
        return aveScore;
    }

    public void setAveScore(Integer aveScore) {
        this.aveScore = aveScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }
}
