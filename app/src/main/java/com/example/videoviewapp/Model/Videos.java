package com.example.videoviewapp.Model;

public class Videos {
    String title,vurl;

    public Videos(String title, String vurl) {
        this.title = title;
        this.vurl = vurl;
    }

    public Videos() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }
}
