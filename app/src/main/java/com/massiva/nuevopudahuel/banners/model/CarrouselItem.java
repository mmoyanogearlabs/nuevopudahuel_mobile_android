package com.massiva.nuevopudahuel.banners.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by iaguila on 24/3/16.
 */
public class CarrouselItem extends RealmObject {

    @PrimaryKey
    private String id;
    private String linkUrl;
    private String imageUrl;
    private int position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
