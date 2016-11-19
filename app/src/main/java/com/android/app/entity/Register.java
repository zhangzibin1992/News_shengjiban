package com.android.app.entity;

/**
 * Created by Justin on 2016/6/1.
 */
public class Register {

    private String result;
    private String token;//用户令牌，用于验证用户。每次请求都传递给服务器。具有时效期。
    private String explain;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
