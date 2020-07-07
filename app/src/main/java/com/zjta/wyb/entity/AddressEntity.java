package com.zjta.wyb.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressEntity implements Parcelable {

    private String id;

    private String province="";// "省份"

    private String city="";//"城市"

    private String county="";//"区县"

    private String consignee="";// "收件人"

    private String phone="";//"收件电话"

    private String address="";//"收件地址"

    private String zipCode="";//"邮政编码"

    private Boolean isDefault;//"是否默认"

    public AddressEntity() {
    }

    protected AddressEntity(Parcel in) {
        id = in.readString();
        province = in.readString();
        city = in.readString();
        county = in.readString();
        consignee = in.readString();
        phone = in.readString();
        address = in.readString();
        zipCode = in.readString();
        byte tmpIsDefault = in.readByte();
        isDefault = tmpIsDefault == 0 ? null : tmpIsDefault == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(county);
        dest.writeString(consignee);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(zipCode);
        dest.writeByte((byte) (isDefault == null ? 0 : isDefault ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddressEntity> CREATOR = new Creator<AddressEntity>() {
        @Override
        public AddressEntity createFromParcel(Parcel in) {
            return new AddressEntity(in);
        }

        @Override
        public AddressEntity[] newArray(int size) {
            return new AddressEntity[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }
}
