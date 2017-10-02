package com.xiaoleilu.loServer.pojos;

import win.liyufan.im.proto.UserOuterClass;

public class InputCreateUser {
    private String userId;
    private String name;
    private String password;
    private String displayName;
    private String portrait;
    private String mobile;
    private String email;
    private String address;
    private String company;
    private String extra;

    public UserOuterClass.User toUser() {
        UserOuterClass.User.Builder newUserBuilder = UserOuterClass.User.newBuilder()
            .setUid(userId);
        if (name != null)
            newUserBuilder.setName(name);
        if (displayName != null)
            newUserBuilder.setDisplayName(displayName);
        if (getPortrait() != null)
            newUserBuilder.setPortrait(getPortrait());
        if (getEmail() != null)
            newUserBuilder.setEmail(getEmail());
        if (getAddress() != null)
            newUserBuilder.setAddress(getAddress());
        if (getCompany() != null)
            newUserBuilder.setCompany(getCompany());
        if (getMobile() != null)
            newUserBuilder.setMobile(getMobile());
        if (getExtra() != null)
            newUserBuilder.setExtra(getExtra());

        newUserBuilder.setUpdateDt(System.currentTimeMillis());
        return newUserBuilder.build();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
