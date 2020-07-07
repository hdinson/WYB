package com.zjta.wyb.event;

public class WindowShopEv {

    private final boolean isSelectedAll;

    public static WindowShopEv getInstance(boolean isSelectedAll) {
        return new WindowShopEv(isSelectedAll);
    }

    private WindowShopEv(boolean calculate) {
        this.isSelectedAll = calculate;
    }

    public boolean isOpen() {
        return isSelectedAll;
    }
}
