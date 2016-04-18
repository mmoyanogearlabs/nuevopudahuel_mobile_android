package com.massiva.nuevopudahuel.model;

import android.support.annotation.DrawableRes;

/**
 * Created by moddity on 29/2/16.
 */
public class MenuItem {

    private int id;
    private @DrawableRes int imgResource;
    private String title;

    public MenuItem() {}

    public MenuItem(int id, @DrawableRes int imgResource, String title) {
        this.id = id;
        this.imgResource = imgResource;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(@DrawableRes int imgResource) {
        this.imgResource = imgResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
