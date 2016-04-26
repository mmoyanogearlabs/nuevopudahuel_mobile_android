package com.massiva.nuevopudahuel.api.response;

import com.massiva.nuevopudahuel.banners.model.CarrouselItem;

/**
 * Created by iaguila on 24/3/16.
 */
public class CarrouselItemResponse {

    private String _id;
    private String linkUrl;
    private String imageUrl;
    private int position;

    public CarrouselItem toItem() {
        CarrouselItem item = new CarrouselItem();
        item.setId(_id);
        item.setLinkUrl(linkUrl);
        item.setImageUrl(imageUrl);
        item.setPosition(position);
        return item;
    }
}
