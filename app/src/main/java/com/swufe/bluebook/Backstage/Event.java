package com.swufe.bluebook.Backstage;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class Event extends BmobObject {
    private User user;
    private School school;
    private String description;
    private List<BmobFile> photos;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BmobFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<BmobFile> photos) {
        this.photos = photos;
    }

    //获取这个已完成事件中用户上传的图片
    public void downloadImgs(final DownloadListener listener) {
        List<BmobFile> photos = this.getPhotos();
        for (int i = 0; i < photos.size(); i++) {
            BmobFile pic = photos.get(i);

            pic.download(new DownloadFileListener() {
                @Override
                public void done(String savePath, BmobException e) {
                    if (e == null) {
                        listener.onSuccess(savePath);

                    } else {
                        listener.onFailure(e);
                    }
                }


                @Override
                public void onProgress(Integer progress, long speed) {
                    listener.onProgress(progress);
                }
            });
        }
    }

}
