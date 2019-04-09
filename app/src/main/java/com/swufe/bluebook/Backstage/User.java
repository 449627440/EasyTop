package com.swufe.bluebook.Backstage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class User extends BmobUser {

    private String headPortrait;//用户头像
    private String nickname;
    private String city;
    private String location;
    private String wenlike;
    private String pici;
    private String score;
    private String currentCity;

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getOwnCity() {
        return city;
    }

    public void setOwnCity(String city) {
        this.city = city;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWenlike() {
        return wenlike;
    }

    public void setWenlike(String wenlike) {
        this.wenlike = wenlike;
    }

    public String getPici() {
        return pici;
    }

    public void setPici(String pici) {
        this.pici = pici;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    //获取已经用户已完成的事件
    public void getCompletedEvent(School school, final com.swufe.bluebook.Backstage.QueryListener callback) {
        BmobQuery<Event> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", this.getObjectId());
        query.addWhereEqualTo("taskId", school.getObjectId());
        query.findObjects(new FindListener<Event>() {
            @Override
            public void done(List<Event> list, BmobException e) {
                if (e == null) {
                    //已完成任务
                    callback.onSuccess(list);
                } else {
                    callback.onFailure(e);
                }
            }
        });
    }

    //用户开始完成这个任务
    public void getTaskCompleted(School school, List<String> picPaths, String description) {
        Event event = new Event();
        List<BmobFile> pics = new ArrayList<>();
        for (String picPath :
                picPaths) {
            pics.add(new BmobFile(new File(picPath)));
        }
        event.setPhotos(pics);
        event.setDescription(description);
        event.save(new SaveListener<String>() {
            @Override
            public void done(String eventId, BmobException e) {

            }
        });

    }


}
