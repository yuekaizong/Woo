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
        return null;
    }

    @Override
    public Observable<Result> smsVerify(Map map) {
        return null;
    }

    @Override
    public Observable<Result> findLoginPwd2UpdateneedVerify(Map map) {
        return null;
    }

    @Override
    public Observable<Result> getBankList() {
        return null;
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
        return null;
    }

    @Override
    public Observable<Result> deleteBankCard(Map map) {
        return null;
    }

    @Override
    public Observable<Result> getDefaultBankCard(Map map) {
        return null;
    }

    @Override
    public Observable<Result> saveDefaultBankCard(Map map) {
        return null;
    }

    @Override
    public Observable<Result> getAllBankInfo() {
        return null;
    }

    @Override
    public Observable<Result> bankCardGrant(Map map) {
        return null;
    }

    @Override
    public Observable<Result> getDict() {
        return null;
    }

    @Override
    public Observable<Result> updateOrderContract() {
        return null;
    }

    @Override
    public Observable<Result> loginAndResetPwd(Map map) {
        return null;
    }

    @Override
    public Observable<Result> saveUauthUsers(Map map) {
        return null;
    }

    @Override
    public Observable<Result> queryPerCustInfo(String name) {
        return null;
    }

    @Override
    public Observable<Result> findLoginPwd2ValidateCustInfo(Map map) {
        return null;
    }

    @Override
    public Observable<Result> getMessageList() {
        return null;
    }

    @Override
    public Observable<Result> updateMsgStatus(Map map) {
        return null;
    }

    @Override
    public Observable<Result> pageByHelpType(Map map) {
        return null;
    }

    @Override
    public Observable<Result> help(Map map) {
        return null;
    }

    @Override
    public Observable<Result> versionDownload(String sysVersion, String versionType, String version, String channelId) {
        return null;
    }

    @Override
    public Observable<Result> getHomePhoto(String sizeType) {
        return null;
    }

    @Override
    public Observable<Result<IsRegister>> isRegister(String mobile) {
        return null;
    }

    @Override
    public Observable<Result> checkIfMsgComplete(Map map) {
        return null;
    }

    @Override
    public Observable<Result> saveCardMsg(Map map) {
        return null;
    }

    @Override
    public Observable<Result> saveCardMsg(String custNo) {
        return null;
    }

    @Override
    public Observable<Result> saveAllCustExtInfo(Map map) {
        return null;
    }

    @Override
    public Observable<Result> getUserInfoByCustNo(String custNo) {
        return null;
    }

    @Override
    public Observable<Result<CustomerLogin>> customerLogin(Map map) {
        return null;
    }

    @Override
    public Observable<Result> getCustLoanCodeAndRatCRM(String custNo, String typGrp) {
        return null;
    }

    @Override
    public Observable<Token> token(String client_secret, String grant_type, String client_id) {
        Observable<Token> call = HttpUtil.getApiService(context).token(client_secret, grant_type, client_id);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Token>(nextListener, context));
        return call;
    }
}
