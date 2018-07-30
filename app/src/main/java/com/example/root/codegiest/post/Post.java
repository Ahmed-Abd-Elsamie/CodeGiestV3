package com.example.root.codegiest.post;

/**
 * Created by root on 26/07/18.
 */

public class Post {

    String name;
    String userImg;
    String date;
    String desc;
    String like;
    String loc;
    String lat;
    String lang;

    public Post(){


    }


    public Post(String name, String userImg, String date, String desc, String like, String loc, String lat, String lang) {
        this.name = name;
        this.userImg = userImg;
        this.date = date;
        this.desc = desc;
        this.like = like;
        this.loc = loc;
        this.lat = lat;
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


}
