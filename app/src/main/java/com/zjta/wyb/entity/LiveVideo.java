package com.zjta.wyb.entity;

public class LiveVideo {

    /**
     * browseQty : 0
     * comment :
     * createTime :
     * createTimeLong : 0
     * executeEndTime :
     * executeEndTimeLong : 0
     * executeStartTime :
     * executeStartTimeLong : 0
     * func :
     * funcValue :
     * id :
     * img :
     * imgUrl :
     * infoPrice : 0
     * isRecommend : true
     * isShow : true
     * managerTopTime :
     * managerTopTimeLong : 0
     * name :
     * popularity : 0
     * status : 0
     * type : 0
     * user :
     * userImgUrl :
     * userName :
     * userTopTime :
     * userTopTimeLong : 0
     * vno :
     * vnoImgUrl :
     * vnoName :
     */

    private int browseQty;
    private String comment;
    private String createTime;
    private long createTimeLong;
    private String executeEndTime;
    private long executeEndTimeLong;
    private String executeStartTime;
    private long executeStartTimeLong;
    private String func;
    private String funcValue;
    private String id;
    private String img;
    private String imgUrl="";
    private Double infoPrice;
    private int infoCoinType;
    private boolean isRecommend;
    private boolean isShow;
    private String managerTopTime;
    private long managerTopTimeLong;
    private String name;
    private int popularity;
    private int status;
    private int type;
    private String user;
    private String userImgUrl="";
    private String userName;
    private String userTopTime;
    private long userTopTimeLong;
    private String vno;
    private String vnoImgUrl;
    private String vnoName;
    private String vonNum;

    public int getInfoCoinType() {
        return infoCoinType;
    }

    public void setInfoCoinType(int infoCoinType) {
        this.infoCoinType = infoCoinType;
    }

    public String getVonNum() {
        return vonNum;
    }

    public void setVonNum(String vonNum) {
        this.vonNum = vonNum;
    }

    public int getBrowseQty() {
        return browseQty;
    }

    public void setBrowseQty(int browseQty) {
        this.browseQty = browseQty;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getCreateTimeLong() {
        return createTimeLong;
    }

    public void setCreateTimeLong(int createTimeLong) {
        this.createTimeLong = createTimeLong;
    }

    public String getExecuteEndTime() {
        return executeEndTime;
    }

    public void setExecuteEndTime(String executeEndTime) {
        this.executeEndTime = executeEndTime;
    }

    public long getExecuteEndTimeLong() {
        return executeEndTimeLong;
    }

    public void setExecuteEndTimeLong(int executeEndTimeLong) {
        this.executeEndTimeLong = executeEndTimeLong;
    }

    public String getExecuteStartTime() {
        return executeStartTime;
    }

    public void setExecuteStartTime(String executeStartTime) {
        this.executeStartTime = executeStartTime;
    }

    public long getExecuteStartTimeLong() {
        return executeStartTimeLong;
    }

    public void setExecuteStartTimeLong(int executeStartTimeLong) {
        this.executeStartTimeLong = executeStartTimeLong;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public String getFuncValue() {
        return funcValue;
    }

    public void setFuncValue(String funcValue) {
        this.funcValue = funcValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Double getInfoPrice() {
        return infoPrice;
    }

    public void setInfoPrice(Double infoPrice) {
        this.infoPrice = infoPrice;
    }

    public boolean isIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public boolean isIsShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public String getManagerTopTime() {
        return managerTopTime;
    }

    public void setManagerTopTime(String managerTopTime) {
        this.managerTopTime = managerTopTime;
    }

    public long getManagerTopTimeLong() {
        return managerTopTimeLong;
    }

    public void setManagerTopTimeLong(int managerTopTimeLong) {
        this.managerTopTimeLong = managerTopTimeLong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    /**
     * 动态状态(10准备中、20等待中、30执行中)
     * @return
     */
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTopTime() {
        return userTopTime;
    }

    public void setUserTopTime(String userTopTime) {
        this.userTopTime = userTopTime;
    }

    public long getUserTopTimeLong() {
        return userTopTimeLong;
    }

    public void setUserTopTimeLong(int userTopTimeLong) {
        this.userTopTimeLong = userTopTimeLong;
    }

    public String getVno() {
        return vno;
    }

    public void setVno(String vno) {
        this.vno = vno;
    }

    public String getVnoImgUrl() {
        return vnoImgUrl;
    }

    public void setVnoImgUrl(String vnoImgUrl) {
        this.vnoImgUrl = vnoImgUrl;
    }

    public String getVnoName() {
        return vnoName;
    }

    public void setVnoName(String vnoName) {
        this.vnoName = vnoName;
    }
}
