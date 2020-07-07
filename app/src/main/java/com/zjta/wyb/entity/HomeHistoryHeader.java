package com.zjta.wyb.entity;

import com.jakewharton.rxbinding4.view.RxView;

public class HomeHistoryHeader {

    public HomeHistoryHeader(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
