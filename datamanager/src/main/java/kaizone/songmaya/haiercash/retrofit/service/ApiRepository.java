package kaizone.songmaya.haiercash.retrofit.service;

import kaizone.songmaya.haiercash.retrofit.annoation.NeedToken;
import kaizone.songmaya.haiercash.retrofit.beans.BankCard;
import kaizone.songmaya.haiercash.retrofit.beans.BankInfo;
import kaizone.songmaya.haiercash.retrofit.beans.CustomerLogin;
import kaizone.songmaya.haiercash.retrofit.beans.IsRegister;
import kaizone.songmaya.haiercash.retrofit.beans.QueryPerCustInfo;
import kaizone.songmaya.haiercash.retrofit.beans.RealInfo;
import kaizone.songmaya.haiercash.retrofit.beans.Result;
import kaizone.songmaya.haiercash.retrofit.beans.Token;
import kaizone.songmaya.haiercash.retrofit.beans.ValidateUserFlag;
import kaizone.songmaya.haiercash.retrofit.beans.VersionCheck;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yuelb on 2017/7/15.
 */

public interface ApiRepository {


    /**
     * 系统参数列表查询
     *
     * @param sysTyp
     * @return
     */
    @GET("app/appserver/appmanage/param/selectByParams")
    Observable<Result> selectByParams(@Query("sysTyp") String sysTyp);

    /**
     * 检测app版本
     *
     * @param sysVersion
     * @param versionType
     * @param version
     * @param channelId
     * @return
     */
    @GET("app/appmanage/version/check")
    Observable<Result<VersionCheck>> versionCheck(@Query("sysVersion") String sysVersion,
                                                  @Query("versionType") String versionType,
                                                  @Query("version") String version,
                                                  @Query("channelId") String channelId);

    @GET("app/appserver/uauth/validateUserFlag")
    Observable<Result<ValidateUserFlag>> validateUserFlag(@Query("userId") String userId);


    /**
     * 发送手机验证码
     *
     * @param phone
     * @return
     */
    @GET("app/appserver/smsSendVerify")
    Observable<Result> smsSendVerify(@Query("phone") String phone);

    /**
     * 校验短信验证码
     *
     * @param map
     * @return
     */
    @POST("app/appserver/smsVerify")
    Observable<Result> smsVerify(@Body Map map);

    /**
     * 找回登录密码之修改登录密码
     *
     * @param map
     * @return
     */
    @POST("/app/appserver/findLoginPwd2Update/needVerify")
    Observable<Result> findLoginPwd2UpdateneedVerify(@Body Map map);

    /**
     * 银行列表名称查询
     *
     * @return
     */
    @GET("app/appserver/crm/cust/getBankList")
    Observable<Result<List<BankInfo>>> getBankList();

    /**
     * 银行卡
     *
     * @return
     */
    @NeedToken
    @GET("app/appserver/crm/cust/getBankCard")
    Observable<Result<BankCard>> getBankCard(@Query("custNo") String custNo);

    /**
     * 查询银行卡信息
     *
     * @param cardNo
     * @return
     */
    @NeedToken
    @GET("app/appserver/crm/cust/getBankInfo")
    Observable<Result<BankInfo>> getBankInfo(@Query("cardNo") String cardNo);

    /**
     * 保存银行卡
     *
     * @param map
     * @return
     */
    @NeedToken
    @POST("app/appserver/crm/cust/saveBankCard/needVerify")
    Observable<Result> saveBankCardNeedVerify(@Body Map map);

    /**
     * 删除银行卡
     *
     * @param map
     * @return
     */
    @NeedToken
    @GET("app/appserver/deleteBankCard")
    Observable<Result> deleteBankCard(@Body Map map);

    /**
     * 查询默认还款卡
     *
     * @param map
     * @return
     */
    @NeedToken
    @GET("app/appserver/crm/cust/getDefaultBankCard")
    Observable<Result> getDefaultBankCard(@Body Map map);

    /**
     * 设置默认还款卡
     *
     * @param map
     * @return
     */
    @NeedToken
    @PUT("app/appserver/crm/cust/saveDefaultBankCard")
    Observable<Result> saveDefaultBankCard(@Body Map map);

    /**
     * 所有银行信息
     *
     * @return
     */
    @GET("app/appserver/crm/cust/getAllBankInfo")
    Observable<Result> getAllBankInfo();

    /**
     * 银行卡变更授权书签章
     *
     * @return
     */
    @NeedToken
    @GET("app/appserver/bankCardGrant")
    Observable<Result> bankCardGrant(@Query("custNo") String custNo,
                                     @Query("custName") String custName,
                                     @Query("certNo") String certNo,
                                     @Query("cardNo") String cardNo,
                                     @Query("bankName") String bankName);

    /**
     * 字典项查询
     *
     * @return
     */
    @GET("app/appserver/cmis/getDict")
    Observable<Result> getDict();

    /**
     * 订单合同确认
     *
     * @return
     */
    @NeedToken
    @POST("app/appserver/apporder/updateOrderContract")
    Observable<Result> updateOrderContract();


    /**
     * 修改登录密码（已登录）
     *
     * @param map
     * @return
     */
    @NeedToken
    @PUT("app/appserver/loginAndResetPwd")
    Observable<Result> loginAndResetPwd(@Body Map map);

    /**
     * 用户注册
     *
     * @param map
     * @return
     */
    @POST("app/appserver/uauth/saveUauthUsers")
    Observable<Result> saveUauthUsers(@Body Map map);

    /**
     * 查询客户编号
     *
     * @param name
     * @return
     */
    @NeedToken
    @GET("app/appserver/crm/cust/queryPerCustInfo")
    Observable<Result<QueryPerCustInfo>> queryPerCustInfo(@Query("userId") String name);

    /**
     * 校验验证码并绑定设备号
     *
     * @param map
     * @return
     */
    @POST("app/appserver/verifyAndBindDeviceId")
    Observable<Result<CustomerLogin>> verifyAndBindDeviceId(@Body Map map);

    /**
     * 找回登录密码之验证客户身份
     *
     * @param map
     * @return
     */
    @NeedToken
    @POST("/app/appserver/findLoginPwd2ValidateCustInfo")
    Observable<Result<RealInfo>> findLoginPwd2ValidateCustInfo(@Body Map map);

    /**
     * 消息列表查询
     *
     * @return
     */
    @GET("app/appserver/customer/getMessageList")
    Observable<Result> getMessageList();

    /**
     * 确认消息已读
     *
     * @param map
     * @return
     */
    @POST("app/appserver/message/updateMsgStatus")
    Observable<Result> updateMsgStatus(@Body Map map);

    /**
     * 帮助信息列表
     *
     * @param map
     * @return
     */
    @GET("app/appmanage/help/pageByHelpType")
    Observable<Result> pageByHelpType(@Body Map map);

    /**
     * 帮助信息详情
     *
     * @param map
     * @return
     */
    @GET("app/appmanage/help")
    Observable<Result> help(@Body Map map);

    /**
     * 版本更新
     *
     * @param sysVersion
     * @param versionType
     * @param version
     * @param channelId
     * @return
     */
    @GET("app/appmanage/version/download")
    Observable<Result> versionDownload(@Query("sysVersion") String sysVersion,
                                       @Query("versionType") String versionType,
                                       @Query("version") String version,
                                       @Query("channelId") String channelId);

    @GET("app/appserver/enoughspend/getHomePhoto")
    Observable<Result> getHomePhoto(@Query("sizeType") String sizeType);

    @GET("app/appserver/uauth/isRegister")
    Observable<Result<IsRegister>> isRegister(@Query("mobile") String mobile);

    /**
     * 信息完整查询
     *
     * @param map
     * @return
     */
    @POST("/app/appserver/shh/EDJH/checkIfMsgComplete")
    Observable<Result> checkIfMsgComplete(@Body Map map);

    /**
     * 保存身份证信息
     *
     * @param map
     * @return
     */
    @POST("app/appserver/saveCardMsg")
    Observable<Result> saveCardMsg(@Body Map map);

    /**
     * 用户身份证照片路径信息
     *
     * @param custNo
     * @return
     */
    @GET("/app/appserver/userinfo/fcf/queryUserCertPhotoPath")
    Observable<Result> queryUserCertPhotoPath(@Query("custNo") String custNo);


    /**
     * 保存个人扩展信息
     *
     * @param map
     * @return
     */
    @POST("app/appserver/crm/cust/saveAllCustExtInfo")
    Observable<Result> saveAllCustExtInfo(@Body Map map);

    /**
     * 客户编号查询用户信息
     *
     * @param custNo
     * @return
     */
    @GET("app/appserver/crm/getUserInfoByCustNo")
    Observable<Result> getUserInfoByCustNo(@Query("custNo") String custNo);

//    @POST("app/appserver/uauth/saveUauthUsers")
//    Observable<Result> saveUauthUsers(
//            @Field("verifyNo") String verifyNo,
//            @Field("mobile") String mobile,
//            @Field("password") String password,
//            @Field("deviceId") String deviceId
//    );

//    @FormUrlEncoded
//    @PUT("app/appserver/customerLogin")
//    Observable<Result<CustomerLogin>> customerLogin(@Field("type") String type, @Field("userId") String userId, @Field("password") String password, @Field("deviceId") String deviceId);

    @PUT("app/appserver/customerLogin")
    Observable<Result<CustomerLogin>> customerLogin(@Body Map map);

    @GET("app/appserver/crm/cust/getCustLoanCodeAndRatCRM")
    Observable<Result> getCustLoanCodeAndRatCRM(@Query("custNo") String custNo,
                                                @Query("typGrp") String typGrp);

    @GET("app/appserver/token")
    Observable<Token> token(@Query("client_secret") String client_secret,
                            @Query("grant_type") String grant_type,
                            @Query("client_id") String client_id);

    @GET("app/appserver/validate/checkFourKeys")
    Observable<Result> checkFourKeys(@Query("mobile") String mobile,
                                     @Query("custName") String custName,
                                     @Query("idNo") String idNo,
                                     @Query("cardNo") String cardNo);

    @PUT("app/appserver/uauth/updateMobile/needVerify")
    Observable<Result> updateMobileNeedVerify(@Body Map map);

    @PUT("app/appserver/uauth/payPasswd")
    Observable<Result> payPasswd(@Body Map map);

    @POST("app/appserver/integration/fkb/edApplInfoAndRiskInfo")
    Observable<Result> edApplInfoAndRiskInfo(@Body Map map);

}
