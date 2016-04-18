package com.massiva.nuevopudahuel.banners.model;

import com.massiva.nuevopudahuel.api.response.BaseResponse;

import java.util.List;

/**
 * Created by iaguila on 23/3/16.
 */
public class APIBanner extends BaseResponse {

    private String id;
    private String adUnit;
    private List<AdSize> adSizes;
    private String linkUrl;

    public String getId() {
        return id;
    }

    public String getAdUnit() {
        return adUnit;
    }

    public List<AdSize> getAdSizes() {
        return adSizes;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getBigBannerUrl() {
        return getBannerImage(AdSize.BIG);
    }

    public String getSmallBannerUrl() {
        return getBannerImage(AdSize.SMALL);
    }

    private String getBannerImage(String adType) {
        if (adSizes != null) {
            for (AdSize adSize : adSizes) {
                if (adSize.getSize().equalsIgnoreCase(adType))
                    return adSize.getImageUrl();
            }
        }
        return null;
    }
}
