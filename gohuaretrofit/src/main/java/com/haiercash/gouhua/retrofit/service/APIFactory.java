package com.haiercash.gouhua.retrofit.service;

import android.content.Context;

import com.haiercash.gouhua.retrofit.beans.CustomerLogin;
import com.haiercash.gouhua.retrofit.beans.IsRegister;
import com.haiercash.gouhua.retrofit.beans.Result;
import com.haiercash.gouhua.retrofit.beans.VersionCheck;
import com.haiercash.gouhua.retrofit.util.HttpUtil;
import com.haiercash.gouhua.retrofit.util.ProgressSubscriber;
import com.haiercash.gouhua.retrofit.util.SubscriberOnNextListenter;

import java.util.Map;

import rx.Observable;

/**
 * Created by yuelb on 2017/7/15.
 */

public class APIFactory {

    //单例模式
    public static APIFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final APIFactory INSTANCE = new APIFactory();
    }

    public void queryPerCustInfo(SubscriberOnNextListenter<Result> nextListenter, Context context, String userId) {
        Observable<Result> call = HttpUtil.getApiService(context).queryPerCustInfo(userId);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
    }

    public void versionCheck(SubscriberOnNextListenter<Result<VersionCheck>> nextListenter, Context context, String sysVersion,
                             String versionType,
                             String version,
                             String channelId) {
        Observable<Result<VersionCheck>> call = HttpUtil.getApiService(context).versionCheck(sysVersion, versionType, version, channelId);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<VersionCheck>>(nextListenter, context));
    }

    public void versionDownload(SubscriberOnNextListenter<Result> nextListenter, Context context, String sysVersion,
                                String versionType,
                                String version,
                                String channelId) {
        Observable<Result> call = HttpUtil.getApiService(context).versionDownload(sysVersion, versionType, version, channelId);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
    }

    public void selectByParams(SubscriberOnNextListenter<Result> nextListenter, Context context, String sysTyp) {
        Observable<Result> call = HttpUtil.getApiService(context).selectByParams(sysTyp);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
    }

    public void getHomePhoto(SubscriberOnNextListenter<Result> nextListenter, Context context, String sizeType) {
        Observable<Result> call = HttpUtil.getApiService(context).getHomePhoto(sizeType);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
    }

    public void isRegister(SubscriberOnNextListenter<Result<IsRegister>> nextListenter, Context context, String mobile) {
        Observable<Result<IsRegister>> call = HttpUtil.getApiService(context).isRegister(mobile);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<IsRegister>>(nextListenter, context));
    }

//    public void saveUauthUsers(SubscriberOnNextListenter<Result> nextListenter, Context context, String verifyNo,
//                              String mobile,
//                              String password,
//                              String deviceId) {
//        Observable<Result> call = HttpUtil.getApiService(context).saveUauthUsers(verifyNo, mobile, password, deviceId);
//        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
//    }

    public void saveUauthUsers(SubscriberOnNextListenter<Result> nextListenter, Context context, Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).saveUauthUsers(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
    }

//    public void customerLogin(SubscriberOnNextListenter<Result<CustomerLogin>> nextListenter, Context context, String type,
//                              String userId,
//                              String password,
//                              String deviceId) {
//        Observable<Result<CustomerLogin>> call = HttpUtil.getApiService(context).customerLogin(type, userId, password, deviceId);
//        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<CustomerLogin>>(nextListenter, context));
//    }

    public void customerLogin(SubscriberOnNextListenter<Result<CustomerLogin>> nextListenter, Context context, Map map) {
        Observable<Result<CustomerLogin>> call = HttpUtil.getApiService(context).customerLogin(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<CustomerLogin>>(nextListenter, context));
    }

    public void getCustLoanCodeAndRatCRM(SubscriberOnNextListenter<Result> nextListenter, Context context,
                                         String custNo,
                                         String typGrp) {
        Observable<Result> call = HttpUtil.getApiService(context).getCustLoanCodeAndRatCRM(custNo, typGrp);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
    }
}
