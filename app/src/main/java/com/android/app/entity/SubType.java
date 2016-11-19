package com.android.app.entity;

/**
 * Created by Justin on 2016/6/1.
 */
public class SubType {

    private int subid;
    private String subgroup;

    public SubType(int subid, String subgroup) {
        this.subid = subid;
        this.subgroup = subgroup;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }
}
