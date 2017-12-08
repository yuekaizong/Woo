package kaizone.songmaya.haiercash.retrofit.service;

import android.content.Context;

import kaizone.songmaya.haiercash.retrofit.beans.CustomerLogin;
import kaizone.songmaya.haiercash.retrofit.beans.IsRegister;
import kaizone.songmaya.haiercash.retrofit.beans.Result;
import kaizone.songmaya.haiercash.retrofit.beans.VersionCheck;
import kaizone.songmaya.haiercash.retrofit.util.HttpUtil;
import kaizone.songmaya.haiercash.retrofit.util.ProgressSubscriber;
import kaizone.songmaya.haiercash.retrofit.util.SubscriberOnNextListener;

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

//    public void queryPerCustInfo(SubscriberOnNextListener<Result> nextListenter, Context context, String userId) {
//        Observable<Result> call = HttpUtil.getApiService(context).queryPerCustInfo(userId);
//        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
//    }

    public void versionCheck(SubscriberOnNextListener<Result<VersionCheck>> nextListenter, Context context, String sysVersion,
                             String versionType,
                             String version,
                             String channelId) {
        Observable<Result<VersionCheck>> call = HttpUtil.getApiService(context).versionCheck(sysVersion, versionType, version, channelId);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<VersionCheck>>(nextListenter, context));
    }

    public void versionDownload(SubscriberOnNextListener<Result> nextListenter, Context context, String sysVersion,
                                String versionType,
                                String version,
                                String channelId) {
        Observable<Result> call = HttpUtil.getApiService(context).versionDownload(sysVersion, versionType, version, channelId);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
    }

    public void selectByParams(SubscriberOnNextListener<Result> nextListenter, Context context, String sysTyp) {
        Observable<Result> call = HttpUtil.getApiService(context).selectByParams(sysTyp);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
    }

    public void getHomePhoto(SubscriberOnNextListener<Result> nextListenter, Context context, String sizeType) {
        Observable<Result> call = HttpUtil.getApiService(context).getHomePhoto(sizeType);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
    }

    public void isRegister(SubscriberOnNextListener<Result<IsRegister>> nextListenter, Context context, String mobile) {
        Observable<Result<IsRegister>> call = HttpUtil.getApiService(context).isRegister(mobile);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<IsRegister>>(nextListenter, context));
    }

//    public void saveUauthUsers(SubscriberOnNextListener<Result> nextListenter, Context context, String verifyNo,
//                              String mobile,
//                              String password,
//                              String deviceId) {
//        Observable<Result> call = HttpUtil.getApiService(context).saveUauthUsers(verifyNo, mobile, password, deviceId);
//        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
//    }

    public void saveUauthUsers(SubscriberOnNextListener<Result> nextListenter, Context context, Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).saveUauthUsers(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
    }

//    public void customerLogin(SubscriberOnNextListener<Result<CustomerLogin>> nextListenter, Context context, String type,
//                              String userId,
//                              String password,
//                              String deviceId) {
//        Observable<Result<CustomerLogin>> call = HttpUtil.getApiService(context).customerLogin(type, userId, password, deviceId);
//        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<CustomerLogin>>(nextListenter, context));
//    }

    public void customerLogin(SubscriberOnNextListener<Result<CustomerLogin>> nextListenter, Context context, Map map) {
        Observable<Result<CustomerLogin>> call = HttpUtil.getApiService(context).customerLogin(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<CustomerLogin>>(nextListenter, context));
    }

    public void getCustLoanCodeAndRatCRM(SubscriberOnNextListener<Result> nextListenter, Context context,
                                         String custNo,
                                         String typGrp) {
        Observable<Result> call = HttpUtil.getApiService(context).getCustLoanCodeAndRatCRM(custNo, typGrp);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
    }
}
