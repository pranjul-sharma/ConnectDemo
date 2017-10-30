package com.test.connect.connectdemo;

import java.util.ArrayList;

/**
 * Created by pranjul on 28/10/17.
 */

public class Group {
    long grpId;
    String grpName;
    ArrayList<Contact> members;

    Group(){
    }

    Group(long grpId,String grpName){
        this.grpId=grpId;
        this.grpName=grpName;
    }
    public void setGrpId(long grpId) {
        this.grpId = grpId;
    }

    public long getGrpId() {
        return grpId;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    public ArrayList<Contact> getMembers() {
        return members;
    }

    public String getGrpName() {
        return grpName;
    }

    public void setMembers(ArrayList<Contact> memberIds) {
        this.members = memberIds;
    }
}
