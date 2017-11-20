package com.haiercash.gouhua.retrofit.service;

import com.haiercash.gouhua.retrofit.beans.CustomerLogin;
import com.haiercash.gouhua.retrofit.beans.IsRegister;
import com.haiercash.gouhua.retrofit.beans.Result;
import com.haiercash.gouhua.retrofit.beans.VersionCheck;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yuelb on 2017/7/15.
 */

public interface ApiService {


    /**
     *  发送手机验证码
     * @param phone
     * @return
     */
    @GET("app/appserver/smsSendVerify")
    Observable<Result> smsSendVerify(@Query("phone")String phone);

    /**
     * 校验短信验证码
     * @param map
     * @return
     */
    @POST("app/appserver/smsVerify")
    Observable<Result> smsVerify(@Body Map map);

    /**
     * 找回登录密码之修改登录密码
     * @param map
     * @return
     */
    @POST("/app/appserver/findLoginPwd2Update/needVerify")
    Observable<Result> findLoginPwd2UpdateneedVerify(@Body Map map);


    @GET("app/appserver/crm/cust/queryPerCustInfo")
    Observable<Result> queryPerCustInfo(@Query("userId") String name);

    @GET("app/appserver/appmanage/param/selectByParams")
    Observable<Result> selectByParams(@Query("sysTyp") String sysTyp);

    @GET("app/appmanage/version/check")
    Observable<Result<VersionCheck>> versionCheck(@Query("sysVersion") String sysVersion,
                                                  @Query("versionType") String versionType,
                                                  @Query("version") String version,
                                                  @Query("channelId") String channelId);

    @GET("app/appmanage/version/download")
    Observable<Result> versionDownload(@Query("sysVersion") String sysVersion,
                                       @Query("versionType") String versionType,
                                       @Query("version") String version,
                                       @Query("channelId") String channelId);

    @GET("app/appserver/enoughspend/getHomePhoto")
    Observable<Result> getHomePhoto(@Query("sizeType") String sizeType);

    @GET("app/appserver/uauth/isRegister")
    Observable<Result<IsRegister>> isRegister(@Query("mobile") String mobile);

//    @POST("app/appserver/uauth/saveUauthUsers")
//    Observable<Result> saveUauthUsers(
//            @Field("verifyNo") String verifyNo,
//            @Field("mobile") String mobile,
//            @Field("password") String password,
//            @Field("deviceId") String deviceId
//    );

    @POST("app/appserver/uauth/saveUauthUsers")
    Observable<Result> saveUauthUsers(@Body Map map);

//    @FormUrlEncoded
//    @PUT("app/appserver/customerLogin")
//    Observable<Result<CustomerLogin>> customerLogin(@Field("type") String type, @Field("userId") String userId, @Field("password") String password, @Field("deviceId") String deviceId);

    @PUT("app/appserver/customerLogin")
    Observable<Result<CustomerLogin>> customerLogin(@Body Map map);

    @GET("app/appserver/crm/cust/getCustLoanCodeAndRatCRM")
    Observable<Result> getCustLoanCodeAndRatCRM(@Query("custNo") String custNo,
                                                @Query("typGrp") String typGrp);

    @GET("app/appserver/token")
    Observable<Result> token(@Query("client_secret") String client_secret,
                             @Query("grant_type") String grant_type,
                             @Query("client_id") String client_id);

}
