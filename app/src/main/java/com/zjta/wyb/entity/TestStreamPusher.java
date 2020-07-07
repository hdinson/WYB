package com.zjta.wyb.entity;

public class TestStreamPusher {

    /**
     * createDate :
     * expireDate :
     * playUrlFlv :
     * playUrlM3u8 :
     * playUrlRtmp :
     * pushUrlRtmp :
     */

    private String createDate;
    private String expireDate;
    private String playUrlFlv;
    private String playUrlM3u8;
    private String playUrlRtmp="";
    private String pushUrlRtmp="";

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getPlayUrlFlv() {
        return playUrlFlv;
    }

    public void setPlayUrlFlv(String playUrlFlv) {
        this.playUrlFlv = playUrlFlv;
    }

    public String getPlayUrlM3u8() {
        return playUrlM3u8;
    }

    public void setPlayUrlM3u8(String playUrlM3u8) {
        this.playUrlM3u8 = playUrlM3u8;
    }

    public String getPlayUrlRtmp() {
        return playUrlRtmp;
    }

    public void setPlayUrlRtmp(String playUrlRtmp) {
        this.playUrlRtmp = playUrlRtmp;
    }

    public String getPushUrlRtmp() {
        return pushUrlRtmp;
    }

    public void setPushUrlRtmp(String pushUrlRtmp) {
        this.pushUrlRtmp = pushUrlRtmp;
    }
}
