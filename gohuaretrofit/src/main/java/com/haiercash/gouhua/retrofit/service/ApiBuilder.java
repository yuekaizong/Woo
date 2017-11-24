package com.haiercash.gouhua.retrofit.service;

import android.content.Context;

import com.haiercash.gouhua.retrofit.beans.BankCard;
import com.haiercash.gouhua.retrofit.beans.BankInfo;
import com.haiercash.gouhua.retrofit.beans.CustomerLogin;
import com.haiercash.gouhua.retrofit.beans.Entity;
import com.haiercash.gouhua.retrofit.beans.IsRegister;
import com.haiercash.gouhua.retrofit.beans.Result;
import com.haiercash.gouhua.retrofit.beans.Token;
import com.haiercash.gouhua.retrofit.beans.VersionCheck;
import com.haiercash.gouhua.retrofit.util.HttpUtil;
import com.haiercash.gouhua.retrofit.util.ProgressSubscriber;
import com.haiercash.gouhua.retrofit.util.SubscriberOnNextListener;

import java.util.Map;

import rx.Observable;

/**
 * Created by Kaizo on 2017/11/22.
 */

public class ApiBuilder implements ApiService {

    private Context context;
    private SubscriberOnNextListener<Entity> nextListener;

    public ApiBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public ApiBuilder nextListenter(SubscriberOnNextListener<Entity> nextListenter) {
        this.nextListener = nextListenter;
        return this;
    }

    @Override
    public Observable<Result> selectByParams(String sysTyp) {
        Observable<Result> call = HttpUtil.getApiService(context).selectByParams(sysTyp);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result<VersionCheck>> versionCheck(String sysVersion, String versionType, String version, String channelId) {
        Observable<Result<VersionCheck>> call = HttpUtil.getApiService(context).versionCheck(sysVersion, versionType, version, channelId);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<VersionCheck>>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> smsSendVerify(String phone) {
        Observable<Result> call = HttpUtil.getApiService(context).smsSendVerify(phone);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> smsVerify(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).smsVerify(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> findLoginPwd2UpdateneedVerify(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).findLoginPwd2UpdateneedVerify(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> getBankList() {
        Observable<Result> call = HttpUtil.getApiService(context).getBankList();
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result<BankCard>> getBankCard(String custNo) {
        Observable<Result<BankCard>> call = HttpUtil.getApiService(context).getBankCard(custNo);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<BankCard>>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result<BankInfo>> getBankInfo(String cardNo) {
        Observable<Result<BankInfo>> call = HttpUtil.getApiService(context).getBankInfo(cardNo);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<BankInfo>>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> saveBankCardNeedVerify(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).saveBankCardNeedVerify(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> deleteBankCard(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).deleteBankCard(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> getDefaultBankCard(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).getDefaultBankCard(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> saveDefaultBankCard(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).saveDefaultBankCard(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> getAllBankInfo() {
        Observable<Result> call = HttpUtil.getApiService(context).getAllBankInfo();
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> bankCardGrant(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).bankCardGrant(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> getDict() {
        Observable<Result> call = HttpUtil.getApiService(context).getDict();
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> updateOrderContract() {
        Observable<Result> call = HttpUtil.getApiService(context).updateOrderContract();
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> loginAndResetPwd(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).loginAndResetPwd(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> saveUauthUsers(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).saveUauthUsers(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> queryPerCustInfo(String name) {
        Observable<Result> call = HttpUtil.getApiService(context).queryPerCustInfo(name);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> findLoginPwd2ValidateCustInfo(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).findLoginPwd2ValidateCustInfo(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> getMessageList() {
        Observable<Result> call = HttpUtil.getApiService(context).getMessageList();
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> updateMsgStatus(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).updateMsgStatus(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> pageByHelpType(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).pageByHelpType(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> help(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).help(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> versionDownload(String sysVersion, String versionType, String version, String channelId) {
        Observable<Result> call = HttpUtil.getApiService(context).versionDownload(sysVersion, versionType, version, channelId);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> getHomePhoto(String sizeType) {
        Observable<Result> call = HttpUtil.getApiService(context).getHomePhoto(sizeType);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result<IsRegister>> isRegister(String mobile) {
        Observable<Result<IsRegister>> call = HttpUtil.getApiService(context).isRegister(mobile);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<IsRegister>>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> checkIfMsgComplete(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).checkIfMsgComplete(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> saveCardMsg(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).saveCardMsg(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> queryUserCertPhotoPath(String custNo) {
        Observable<Result> call = HttpUtil.getApiService(context).queryUserCertPhotoPath(custNo);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> saveAllCustExtInfo(Map map) {
        Observable<Result> call = HttpUtil.getApiService(context).saveAllCustExtInfo(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> getUserInfoByCustNo(String custNo) {
        Observable<Result> call = HttpUtil.getApiService(context).getUserInfoByCustNo(custNo);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result<CustomerLogin>> customerLogin(Map map) {
        Observable<Result<CustomerLogin>> call = HttpUtil.getApiService(context).customerLogin(map);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<CustomerLogin>>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Result> getCustLoanCodeAndRatCRM(String custNo, String typGrp) {
        Observable<Result> call = HttpUtil.getApiService(context).getCustLoanCodeAndRatCRM(custNo, typGrp);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListener, context));
        return call;
    }

    @Override
    public Observable<Token> token(String client_secret, String grant_type, String client_id) {
        Observable<Token> call = HttpUtil.getApiService(context).token(client_secret, grant_type, client_id);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Token>(nextListener, context));
        return call;
    }
}
