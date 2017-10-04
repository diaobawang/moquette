package com.xiaoleilu.loServer.pojos;

import win.liyufan.im.proto.UserOuterClass;

public class OutputGetUserInfo {
    private String userId;
    private String name;
    private String displayName;
    private String portrait;
    private String mobile;
    private String email;
    private String address;
    private String company;
    private String extra;
    private long updateDt;

    public static OutputGetUserInfo fromUser(UserOuterClass.User user) {
        OutputGetUserInfo out = new OutputGetUserInfo();
        out.userId = user.getUid();
        out.name = user.getName();
        out.displayName = user.getDisplayName();
        out.portrait = user.getPortrait();
        out.mobile = user.getMobile();
        out.email = user.getEmail();
        out.address = user.getAddress();
        out.company = user.getCompany();
        out.extra = user.getExtra();
        out.updateDt = user.getUpdateDt();
        return out;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public long getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(long updateDt) {
        this.updateDt = updateDt;
    }
}
