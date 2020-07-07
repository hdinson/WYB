package com.zjta.wyb.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 直播间实体
 */
public class LivePusherRoom implements Parcelable {

    private long openTime;//开播时间
    private int id;//直播间id


    public LivePusherRoom() {
    }

    protected LivePusherRoom(Parcel in) {
        openTime = in.readLong();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(openTime);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LivePusherRoom> CREATOR = new Creator<LivePusherRoom>() {
        @Override
        public LivePusherRoom createFromParcel(Parcel in) {
            return new LivePusherRoom(in);
        }

        @Override
        public LivePusherRoom[] newArray(int size) {
            return new LivePusherRoom[size];
        }
    };

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
