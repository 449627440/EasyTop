package com.swufe.bluebook.TabPagers;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class SchoolInfo extends BmobObject implements Serializable {
    private String university;
    private String logo;
    private String type;
    private String belong;
    private String location;
    private String academician;
    private String doctor;
    private String master;
    private String website;
    private String contact;
    private String scoreTrend;
    private String introduction;
    private String teacher;
    private String school;
    private String major;
    private String occupation;

    public SchoolInfo(String university, String logo, String type){
        this.university=university;
        this.logo=logo;
        this.type=type;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAcademician() {
        return academician;
    }

    public void setAcademician(String academician) {
        this.academician = academician;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getScoreTrend() {
        return scoreTrend;
    }

    public void setScoreTrend(String scoreTrend) {
        this.scoreTrend = scoreTrend;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
