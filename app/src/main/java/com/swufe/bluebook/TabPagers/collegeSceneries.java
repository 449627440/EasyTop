package com.swufe.bluebook.TabPagers;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class collegeSceneries extends BmobObject implements Serializable {
    private String collegeScenery;
    private String university;
    private String rank;

    public String getCollegeScenery() {
        return collegeScenery;
    }

    public void setCollegeScenery(String collegeScenery) {
        this.collegeScenery = collegeScenery;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
