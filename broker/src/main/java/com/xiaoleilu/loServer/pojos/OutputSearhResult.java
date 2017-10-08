package com.xiaoleilu.loServer.pojos;

import win.liyufan.im.proto.UserOuterClass;

import java.util.ArrayList;
import java.util.List;

public class OutputSearhResult {
    public static class OutputSearchUser {
        private String userId;
        private String name;
        private String displayName;
        private String portrait;
        private String mobile;
        private String email;
        private String address;
        private String company;
        private String extra;

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
        public static OutputSearchUser from(UserOuterClass.User pbUser) {
            OutputSearchUser out = new OutputSearchUser();
            out.userId = pbUser.getUid();
            out.name = pbUser.getName();
            out.displayName = pbUser.getDisplayName();
            out.portrait = pbUser.getPortrait();
            out.mobile = pbUser.getMobile();
            out.email = pbUser.getEmail();
            out.address = pbUser.getAddress();
            out.company = pbUser.getCompany();
            out.extra = pbUser.getExtra();
            return out;
        }
    }
    private String keyword;
    private int page;
    private List<OutputSearchUser> users;

    public OutputSearhResult(String keyword, int page, List<UserOuterClass.User> pbUsers) {
        this.keyword = keyword;
        this.page = page;
        this.users = new ArrayList<>();
        for (UserOuterClass.User user : pbUsers
             ) {
            this.users.add(OutputSearchUser.from(user));
        }
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<OutputSearchUser> getUsers() {
        return users;
    }

    public void setUsers(List<OutputSearchUser> users) {
        this.users = users;
    }
}
