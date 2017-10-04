package com.xiaoleilu.loServer.pojos;

import win.liyufan.im.proto.GroupOuterClass;

public class OutputGetGroupInfo {
    String groupId;
    int line;
    String name;
    String portrait;
    String owner;
    int type;
    String extra;
    long updateDt;

    public static OutputGetGroupInfo fromGroupInfo(GroupOuterClass.GroupInfo groupInfo) {
        OutputGetGroupInfo out = new OutputGetGroupInfo();
        out.groupId = groupInfo.getTargetId();
        out.line = groupInfo.getLine();
        out.name = groupInfo.getName();
        out.portrait = groupInfo.getPortrait();
        out.owner = groupInfo.getOwner();
        out.type = groupInfo.getTypeValue();
        out.extra = groupInfo.getExtra();
        out.updateDt = groupInfo.getUpdateDt();
        return out;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
