package com.zjta.wyb.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class WindowShopGoods implements Parcelable {

    private int liveInfo;//直播间id
    private String name;//商品名称
    private String brand;//品牌
    private String packaging;//包装
    private String specification;//规格型号
    private String manufacturer;//厂商
    private String netWeight;//净含量
    private double price;//产品价格
    private double activityPrice;//产品活动价格
    private String introduce;//产品介绍
    private String imageUrl = "";//上传图片字段
    private String imgUrl = "";//服务器图片地址




    //-------------------------
    private boolean isSelected = false;//适配器中是否被选中
    private int selectGoodsNum = 1;//选中的数量


    public WindowShopGoods() {

    }

    protected WindowShopGoods(Parcel in) {
        liveInfo = in.readInt();
        name = in.readString();
        brand = in.readString();
        packaging = in.readString();
        specification = in.readString();
        manufacturer = in.readString();
        netWeight = in.readString();
        price = in.readDouble();
        activityPrice = in.readDouble();
        introduce = in.readString();
        imageUrl = in.readString();
        isSelected = in.readByte() != 0;
        selectGoodsNum = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(liveInfo);
        dest.writeString(name);
        dest.writeString(brand);
        dest.writeString(packaging);
        dest.writeString(specification);
        dest.writeString(manufacturer);
        dest.writeString(netWeight);
        dest.writeDouble(price);
        dest.writeDouble(activityPrice);
        dest.writeString(introduce);
        dest.writeString(imageUrl);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeInt(selectGoodsNum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WindowShopGoods> CREATOR = new Creator<WindowShopGoods>() {
        @Override
        public WindowShopGoods createFromParcel(Parcel in) {
            return new WindowShopGoods(in);
        }

        @Override
        public WindowShopGoods[] newArray(int size) {
            return new WindowShopGoods[size];
        }
    };

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public int getSelectGoodsNum() {
        return selectGoodsNum;
    }

    public void setSelectGoodsNum(int selectGoodsNum) {
        this.selectGoodsNum = selectGoodsNum;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selector) {
        isSelected = selector;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLiveInfo() {
        return liveInfo;
    }

    public void setLiveInfo(int liveInfo) {
        this.liveInfo = liveInfo;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(double activityPrice) {
        this.activityPrice = activityPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
