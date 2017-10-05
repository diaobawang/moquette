package com.xiaoleilu.loServer.pojos;

import win.liyufan.im.proto.GroupOuterClass;

import java.util.ArrayList;
import java.util.List;

public class OutputGroupMember {
    private String memberId;
    private String alias;
    private int type;
    private long updateDt;

    public static List<OutputGroupMember> memberListFrom(List<GroupOuterClass.GroupMember> members) {
        List<OutputGroupMember> out = new ArrayList<>();
        for (GroupOuterClass.GroupMember member: members
             ) {
            OutputGroupMember outMember = new OutputGroupMember();
            outMember.memberId = member.getMemberId();
            outMember.alias = member.getAlias();
            outMember.type = member.getType();
            outMember.updateDt = member.getUpdateDt();
            out.add(outMember);
        }
        return out;
    }
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(long updateDt) {
        this.updateDt = updateDt;
    }
}
