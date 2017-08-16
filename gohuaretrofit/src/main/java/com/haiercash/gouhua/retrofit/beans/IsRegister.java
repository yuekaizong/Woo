package com.haiercash.gouhua.retrofit.beans;

import retrofit2.http.PUT;

/**
 * Created by yuelb on 2017/7/18.
 */

public class IsRegister {
    public String isRegister;
    public String provider;
    public String alterPwd;
    public String alterPwdIn;
    public String alterPwdOut;

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", isRegister,provider,alterPwd,alterPwdIn,alterPwdOut);
    }
}
