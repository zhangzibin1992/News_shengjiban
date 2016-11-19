package com.android.app.entity;

import java.util.List;

/**
 * Created by Justin on 2016/6/1.
 */
public class User {

    private String uid;
    private String portrait;
    private Integer integration;
    private Integer comnum;
    private List<Loginlog> loginlog;

    public User(String uid, String portrait, Integer integration, Integer comnum, List<Loginlog> loginlog) {
        this.uid = uid;
        this.portrait = portrait;
        this.integration = integration;
        this.comnum = comnum;
        this.loginlog = loginlog;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }

    public Integer getComnum() {
        return comnum;
    }

    public void setComnum(Integer comnum) {
        this.comnum = comnum;
    }

    public List<Loginlog> getLoginlog() {
        return loginlog;
    }

    public void setLoginlog(List<Loginlog> loginlog) {
        this.loginlog = loginlog;
    }
}
