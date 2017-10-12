package com.taller2.llevame;

/**
 * Created by martin on 10/12/2017.
 */

public enum CookieHolder {
    INSTANCE;

    String cookie = "";

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
