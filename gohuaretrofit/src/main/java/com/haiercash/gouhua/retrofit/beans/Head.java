package com.haiercash.gouhua.retrofit.beans;

/**
 * Created by yuelb on 2017/7/15.
 */

public class Head {
    public String retFlag;
    public String retMsg;

    @Override
    public String toString() {
        return String.format("retFlag=%s,retMsg=%s", retFlag, retMsg);
    }
}
