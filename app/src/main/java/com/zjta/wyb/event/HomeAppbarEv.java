package com.zjta.wyb.event;

public class HomeAppbarEv {

    private final boolean open;

    public static HomeAppbarEv getInstance(boolean open) {
        return new HomeAppbarEv(open);
    }

    private HomeAppbarEv(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }
}
