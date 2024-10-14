package com.example.dumvanimechat;

import java.io.Serializable;

public class NVAnime implements Serializable {
    private int mal_id;
    private String url;
    private String imageUrl;
    private String name;
    private String about;

    public NVAnime(int mal_id, String url, String imageUrl, String name, String about) {
        this.mal_id = mal_id;
        this.url = url;
        this.imageUrl = imageUrl;
        this.name = name;
        this.about = about;
    }

    public int getMal_id() {
        return mal_id;
    }

    public void setMal_id(int mal_id) {
        this.mal_id = mal_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}

