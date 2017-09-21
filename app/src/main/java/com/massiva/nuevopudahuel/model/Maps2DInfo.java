package com.massiva.nuevopudahuel.model;

import java.util.Date;

/**
 * Created by jotta-emme on 21/9/17.
 */

public class Maps2DInfo {
    private String shopsVersionUpdate;
    private Date shopsUpdateDate;
    private String checkSum;
    private String urlForDownloadZipMaps;

    public Maps2DInfo() {
    }

    public String getShopsVersionUpdate() {
        return shopsVersionUpdate;
    }

    public void setShopsVersionUpdate(String shopsVersionUpdate) {
        this.shopsVersionUpdate = shopsVersionUpdate;
    }

    public Date getShopsUpdateDate() {
        return shopsUpdateDate;
    }

    public void setShopsUpdateDate(Date shopsUpdateDate) {
        this.shopsUpdateDate = shopsUpdateDate;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public String getUrlForDownloadZipMaps() {
        return urlForDownloadZipMaps;
    }

    public void setUrlForDownloadZipMaps(String urlForDownloadZipMaps) {
        this.urlForDownloadZipMaps = urlForDownloadZipMaps;
    }
}
