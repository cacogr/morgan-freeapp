package com.arte.photoapp.model;


import java.io.Serializable;

public class Photo {

    private String id;
    private String title;
    private String url;
    private String thumbnailUrl;

    public Photo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return "https://placeholdit.imgix.net/~text?txtsize=14&bg=" + url.substring(url.length() - 6, url.length()) + "&txt=BIGGERWORLD&w=150&h=150";
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return "https://placeholdit.imgix.net/~text?txtsize=14&bg=" + thumbnailUrl.substring(thumbnailUrl.length() - 6, thumbnailUrl.length()) + "&txt=HELLOWORLD&w=150&h=150";
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
