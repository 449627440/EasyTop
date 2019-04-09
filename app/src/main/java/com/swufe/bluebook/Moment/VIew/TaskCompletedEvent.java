package com.swufe.bluebook.Moment.VIew;

import com.swufe.bluebook.Backstage.School;
import com.swufe.bluebook.Backstage.User;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Andream on 2017/10/19.
 * 空间说说数据类
 */

public class TaskCompletedEvent extends BmobObject {
    private User user; //用户
    private School task;  // 任务
    private String description;   // 说说内容
    private List<String> picUrls; //图片的URL集合
    private boolean haveIcon;  //判断是否有图片

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public School getTask() {
        return task;
    }

    public void setTask(School task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }

    public boolean isHaveIcon() {
        return haveIcon;
    }

    public void setHaveIcon(boolean haveIcon) {
        this.haveIcon = haveIcon;
    }
}

