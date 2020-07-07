package com.zjta.wyb.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户参数
 */
public class UserInfo implements Parcelable {


    /**
     * createTimeEnd :
     * createTimeStart :
     * id :
     * wybVno : [{"createTime":"","createUser":"","enableTransfer":true,"img":"","isDefault":true,"user":"","id":"","imgUrl":"","name":"","status":0}]
     * account :
     * createTime :
     * enableSavaRole : true
     * img :
     * imgUrl :
     * isAccountNonExpired : true
     * isAccountNonLocked : true
     * isCredentialsNonExpired : true
     * isEnabled : true
     * name :
     * permissionList : []
     * pid :
     * saltValue :
     */

    private String createTimeEnd;
    private String createTimeStart;
    private String id;
    private String account;
    private String createTime;
    private boolean enableSavaRole;
    private String imgUrl;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private String name;
    private String pid;
    private String saltValue;
    private WybVnoBean wybVno;

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        createTimeEnd = in.readString();
        createTimeStart = in.readString();
        id = in.readString();
        account = in.readString();
        createTime = in.readString();
        enableSavaRole = in.readByte() != 0;
        imgUrl = in.readString();
        isAccountNonExpired = in.readByte() != 0;
        isAccountNonLocked = in.readByte() != 0;
        isCredentialsNonExpired = in.readByte() != 0;
        isEnabled = in.readByte() != 0;
        name = in.readString();
        pid = in.readString();
        saltValue = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createTimeEnd);
        dest.writeString(createTimeStart);
        dest.writeString(id);
        dest.writeString(account);
        dest.writeString(createTime);
        dest.writeByte((byte) (enableSavaRole ? 1 : 0));
        dest.writeString(imgUrl);
        dest.writeByte((byte) (isAccountNonExpired ? 1 : 0));
        dest.writeByte((byte) (isAccountNonLocked ? 1 : 0));
        dest.writeByte((byte) (isCredentialsNonExpired ? 1 : 0));
        dest.writeByte((byte) (isEnabled ? 1 : 0));
        dest.writeString(name);
        dest.writeString(pid);
        dest.writeString(saltValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isEnableSavaRole() {
        return enableSavaRole;
    }

    public void setEnableSavaRole(boolean enableSavaRole) {
        this.enableSavaRole = enableSavaRole;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isIsAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setIsAccountNonExpired(boolean isAccountNonExpired) {
        this.isAccountNonExpired = isAccountNonExpired;
    }

    public boolean isIsAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setIsAccountNonLocked(boolean isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
    }

    public boolean isIsCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setIsCredentialsNonExpired(boolean isCredentialsNonExpired) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    public boolean isIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSaltValue() {
        return saltValue;
    }

    public void setSaltValue(String saltValue) {
        this.saltValue = saltValue;
    }

    public WybVnoBean getWybVno() {
        return wybVno;
    }

    public void setWybVno(WybVnoBean wybVno) {
        this.wybVno = wybVno;
    }

    public static class WybVnoBean implements Parcelable {
        /**
         * createTime :
         * createUser :
         * enableTransfer : true
         * img :
         * isDefault : true
         * user :
         * id :
         * imgUrl :
         * name :
         * status : 0
         */

        private String createTime;
        private String createUser;
        private boolean enableTransfer;
        private String img;
        private boolean isDefault;
        private String user;
        private String id;
        private String imgUrl;
        private String name;
        private int status;

        protected WybVnoBean(Parcel in) {
            createTime = in.readString();
            createUser = in.readString();
            enableTransfer = in.readByte() != 0;
            img = in.readString();
            isDefault = in.readByte() != 0;
            user = in.readString();
            id = in.readString();
            imgUrl = in.readString();
            name = in.readString();
            status = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(createTime);
            dest.writeString(createUser);
            dest.writeByte((byte) (enableTransfer ? 1 : 0));
            dest.writeString(img);
            dest.writeByte((byte) (isDefault ? 1 : 0));
            dest.writeString(user);
            dest.writeString(id);
            dest.writeString(imgUrl);
            dest.writeString(name);
            dest.writeInt(status);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<WybVnoBean> CREATOR = new Creator<WybVnoBean>() {
            @Override
            public WybVnoBean createFromParcel(Parcel in) {
                return new WybVnoBean(in);
            }

            @Override
            public WybVnoBean[] newArray(int size) {
                return new WybVnoBean[size];
            }
        };

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public boolean isEnableTransfer() {
            return enableTransfer;
        }

        public void setEnableTransfer(boolean enableTransfer) {
            this.enableTransfer = enableTransfer;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public boolean isIsDefault() {
            return isDefault;
        }

        public void setIsDefault(boolean isDefault) {
            this.isDefault = isDefault;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
