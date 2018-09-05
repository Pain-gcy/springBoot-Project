package com.cnbn.apollo.charging.sncmcc.zx.bean;

import java.io.Serializable;

/**
 * Created by DuQiyu on 2017/12/18.
 */
public class CancelOrderBean implements Serializable {

    private String expiredTime;

    public CancelOrderBean(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public CancelOrderBean() {
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }
}
