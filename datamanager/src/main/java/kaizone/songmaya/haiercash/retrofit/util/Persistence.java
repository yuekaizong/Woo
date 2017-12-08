package kaizone.songmaya.haiercash.retrofit.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import kaizone.songmaya.haiercash.retrofit.beans.CustomerLogin;
import kaizone.songmaya.haiercash.retrofit.beans.Token;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;

/**
 * Created by yuelb on 2017/7/17.
 */

public class Persistence {
    private static final String TAG = "Persistence";

    /**
     * 登录相关信息
     */
    public static String login = "LOGIN";
    public static String login_clientsercert = "sClientSecret";//请求token用
    public static String login_userid = "userId";//用户id
    public static String login_inputid = "inputId";//用户手输的id
    public static String login_mobile = "login_mobile";//绑定手机号
    public static String login_accesstoken = "accessToken";//接口请求中用到的token
    public static String login_userprovider = "userProvider";//用户身份，是不是海尔会员，Y：是海尔会员
    public static String login_deviceid = "deviceId";//设备号
    public static String login_version_number = "version_number";//最新版本号
    public static String login_br = "br_login";//百融登录事件
    /**
     * 实名相关信息，启动页会清除该sp文件
     */
    public static String USER = "USER";
    public static String user_custno = "custNo";//客户编号
    public static String user_custname = "custName";//用户姓名
    public static String user_certtype = "certType";//证件类型
    public static String user_certno = "certNo";//证件号 身份证号
    public static String user_cardno = "cardNo";//银行卡号
    public static String user_mobile = "mobile";//实名认证手机号
    public static String user_acctno = "acctNo";//账号
    public static String user_acctname = "acctName";//账户名
    public static String user_acctbankno = "acctBankNo";//银行代码
    public static String user_acctbankname = "acctBankName";//银行名称
    public static String user_acctprovince = "acctProvince";//开户省
    public static String user_acctcity = "acctCity";//开户市
    public static String user_acctarea = "acctArea";//开户区
    public static String user_accbchcde = "accBchCde";//支行代码
    public static String user_accbankname = "accBchName";//支行名称

    public static String USER_EDU_ALL = "userCrdAmt";//额度总金额
    public static String USER_EDU_AVLIABLE = "userCrdNorAmt";//剩余额度
    public static String USER_EDU_CRDSTS = "userCRDSTS";//额度状态
    public static String USER_EDU_OUTSTS = "userOUTSTS";//当前申请状态
    public static String USER_EDU_USERSTS = "userFLAG";//是否可以发起额度申请
    public static String USER_EDU_APPLSEQ = "userCRDSEQ";//在途申请流水号
    public static String USER_EDU_AMOUNT = "userAmount";//最近还款金额
    public static String USER_EDU_SHOW = "USER_edu_unCommit";//是否弹出了额度未提交的提示
    public static String USER_YUQI_COUNT = "useryuqicount";//逾期笔数
    public static String USER_YUQI_AMOUNT = "userYuqiAmount";//逾期总额

    /**
     * 位置相关信息，启动页会清除该sp文件
     */
    public static String LOCATION = "LOCATION";
    public static String LOCATION_LON = "longitude";//经度
    public static String LOCATION_LAT = "latitude";//纬度
    public static String LOCATION_COUNTRYNAME = "countryName";//国家名称
    public static String LOCATION_COUNTRYCODE = "countryCode";//国家代码
    public static String LOCATION_PEOVINCENAME = "provinceName";//省名称
    public static String LOCATION_PROVINCECODE = "provinceCode";//省代码
    public static String LOCATION_CITYNAME = "cityName";//市名称
    public static String LOCATION_CITYCODE = "cityCode";//市代码
    public static String LOCATION_AREANAME = "areaName";//区名称
    public static String LOCATION_AREACODE = "areaName";//区代码

    /**
     * 信息状态标识（是否登录，实名，有无支付密码等）等信息，启动页会清除该sp文件
     */
    public static String STATE = "STATE";
    public static String STATE_LOGINSTATE = "loginState";//是否登录（登录状态）Y：已登录，N：未登录
    public static String STATE_ISAUTHENTICATION = "isAuthentication";//是否做过实名认证。Y：有，N：没有，E：查询失败
    public static String STATE_HASGESTURESPAS = "hasGesturesPas";//是否有手势密码。Y：有，N：没有，E：查询失败
    public static String STATE_HASPAYPAS = "hasPayPas";//是否有支付密码。Y：有，N：没有，E：查询失败

    /**
     * 流程标识相关信息，流程结束后可以直接clean该文件下的所有标识，启动页也会清除该sp文件
     */
    public static String FLAG = "FLAG";
    public static String FLAG_IFCHANGEPAYPASS = "ifChangePayPass";//标记是否从录单修改支付密码Y 是  N 不是

    /**
     * 系统配置项（默认参数），启动页会清除该sp文件
     */
    public static String CONFIGURE = "CONFIGURE";
    public static String CONFIGURE_MAXNUM = "maxNum";//商品默认的可选个数
    public static String CONFIGURE_MINLOANMONEY = "minLoanMoney";//没有申请额度用户最低借款金额
    public static String CONFIGURE_MAXLOANMONEY = "maxLoanMoney";//没有申请额度用户最高借款金额
    public static String CONFIGURE_GOODSMINMONER = "goodsMinMoney";//没有申请额度用户商品最低借款金额
    public static String CONFIGURE_GOODSMAXMONER = "goodsMaxMoney";//没有申请额度用户商品最高借款金额
    public static String CONFIGURE_INFOMATION = "goodsMaxMoney";//信贷配置的字典项，如关系、房产信息等

    /**
     * 其他未分类信息
     */
    public static String OTHER = "OTHER";
    public static String OTHER_PHOTOID = "photoId";//广告开屏页图片的id
    public static String OTHER_GUIDE_PAGE = "guidePage";//是否要进引导页，"N"是不要进，其余进
    public static String OTHER_INT_RAT = "intRat";//日利率

    /**
     * 锁屏计时相关
     */
    public static String LOCK = "LOCK";
    public static String LOCK_CURRENT_TIME = "currentTime";

    public static final String CUSTOMER = "Customer";
    public static final String CUSTOMER_LOGIN = "CustomerLogin";

    public static void saveCustomerLogin(Context context, CustomerLogin obj){
        SharedPreferences sp = context.getSharedPreferences(CUSTOMER, Context.MODE_PRIVATE);
        String customerLoginString = encodeCustomerLogin(obj);
        sp.edit().putString(CUSTOMER_LOGIN, customerLoginString).apply();
    }

    public static CustomerLogin getCustomerLogin(Context context){
        SharedPreferences sp = context.getSharedPreferences(CUSTOMER, Context.MODE_PRIVATE);
        String string = sp.getString(CUSTOMER_LOGIN,null);
        if(string == null) return null;
        return decodeCustomerLogin(string);
    }

    public static void setToken(Context context, Token token){
        CustomerLogin user = getCustomerLogin(context);
        user.token = token;
        saveCustomerLogin(context, user);
    }

    public static String clientId(Context context){
        CustomerLogin obj = getCustomerLogin(context);
        String userId = obj.userId;
        String deviceId = obj.deviceId;
        return EncryptUtil.simpleEncrypt("AND-" + deviceId + "-" + userId);
    }



    public static String encodeCustomerLogin(CustomerLogin obj){
        if (obj == null)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(obj);
        } catch (IOException e) {
            Log.e(TAG, "IOException in encodeCustomerLogin", e);
            return null;
        }

        return byteArrayToHexString(os.toByteArray());
    }


    /**
     * 将字符串反序列化成CustomerLogin
     *
     * @param customerLoginString string
     * @return cookie object
     */
    protected static CustomerLogin decodeCustomerLogin(String customerLoginString) {
        byte[] bytes = hexStringToByteArray(customerLoginString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        CustomerLogin obj = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            obj = (CustomerLogin) objectInputStream.readObject();
        } catch (IOException e) {
            Log.d(TAG, "IOException in decodeCookie", e);
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "ClassNotFoundException in decodeCookie", e);
        }

        return obj;
    }


    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    protected static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    protected static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}
