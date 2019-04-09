package com.swufe.bluebook.Collect;

public class MyCollection {
    private String pic_colletion,title_collection;

    public MyCollection(String pic_colletion, String title_collection) {
        this.pic_colletion = pic_colletion;
        this.title_collection = title_collection;
    }

    public String getPic_colletion() {
        return pic_colletion;
    }

    public void setPic_colletion(String pic_colletion) {
        this.pic_colletion = pic_colletion;
    }

    public String getTitle_collection() {
        return title_collection;
    }

    public void setTitle_collection(String title_collection) {
        this.title_collection = title_collection;
    }
}
