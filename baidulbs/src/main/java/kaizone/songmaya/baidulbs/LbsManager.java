package kaizone.songmaya.baidulbs;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import kaizone.songmaya.baidulbs.entity.LocationInfo;

/**
 * Created by Kaizo on 2017/12/6.
 */

public class LbsManager {

    private static final String TAG = "LbsManager";
    LocationClient mLocationClient;
    LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private String tempcoor = "bd09ll";
    public Context mCtx;

    private String LOCATION_ERROR_CONFIM = "当前无法确认您的位置！请移动至开阔地带，并开启定位服务！";

    public static final String LOCATION_ERROR_PERMISSION = "定位失败，请开启定位权限";
    public static final String LOCATION_ERROR_NOGPS = "定位失败，请确保网络畅通并开启了GPS";

    private OnLocationListener mOnLocationListener;

    private LocationListener mLocationListener;

    private Address address;


    private String proCode;
    private String cityCode;
    private String areaCode;

    /**
     * 初始化 定位的参数
     *
     * @param context
     */
    public LbsManager(Context context) {
        mCtx = context;
        initLocation();
    }

    /**
     * 开启百度定位
     *
     * @param onLocationListener 回调监听
     */

    public void startLocation(OnLocationListener onLocationListener) {
        mOnLocationListener = onLocationListener;
        mLocationClient.start();
        mLocationClient.requestLocation();
    }


    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
    }


    public void onSuccess(Object response, String flag) {
        JSONObject responseJson = null;
        try {
            responseJson = new JSONObject((String) response);
            JSONObject responseHead = responseJson.optJSONObject("head");
            if ("00000".equals(responseHead.optString("retFlag"))) {
                JSONObject responseBody = responseJson.optJSONObject("body");
                String paramValue = responseBody.optString("paramValue");
                if (!TextUtils.isEmpty(paramValue)) {
                    // 对返回的数据进行处理
                    String[] paramValues = paramValue.split(":");
                    if (paramValues != null) {
                        // 获取省市区编码
                        for (int i = 0; i < paramValues.length; i++) {
                            if (i == 0) {
                                cityCode = paramValues[i];
                            } else if (i == 1) {
                                areaCode = paramValues[i];
                            }
                        }
                        //保存地理位置信息
                        setLocationCode(proCode, cityCode, areaCode);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mOnLocationListener.onSuccess(address);
    }

    public void onErr(Object response, String flag) {
        mOnLocationListener.onSuccess(address);
    }

    /**
     * 实现实位回调监听
     */
    public class LocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            // 获取到信息，则停止定位
            stopLocation();
            if (location.getLocType() == 167) { //无权限
                mOnLocationListener.onErr("167", LOCATION_ERROR_PERMISSION);
            } else if (location.getLocType() == 63) { // 无网络
                mOnLocationListener.onErr("63", "定位失败，请检查网络是否畅通");
            } else if (location.getLocType() == 62) { // 没有打开GPS
                mOnLocationListener.onErr("62", LOCATION_ERROR_NOGPS);
            } else {
                // 获取定位的经纬度
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                Log.e(TAG, location.toString());
//                if ("4.9E-324".equals(String.valueOf(lat)) || "4.9E-324".equals(String.valueOf(lon))) {
//                    mOnLocationListener.onErr(String.valueOf(location.getLocType()), LOCATION_ERROR_CONFIM);
//                } else {
//                    if (lat > 0 && lon > 0) {
//                        SpHelper baiduSp = SpHelper.getInstance();
//                        baiduSp.saveMsgToSp(SpKey.LOCATION, SpKey.LOCATION_LAT, String.valueOf(lat));
//                        baiduSp.saveMsgToSp(SpKey.LOCATION, SpKey.LOCATION_LON, String.valueOf(lon));
//                        if (!TextUtils.isEmpty(location.getAddrStr())) {
//                            Log.e("当前位置：", location.getAddrStr());
//                            address = location.getAddress();
//                            proCode = AddressUtils.getInstance().proCde(address.province);
//                            if (TextUtils.isEmpty(proCode)) {
//                                mOnLocationListener.onErr("99", LOCATION_ERROR_CONFIM);
//                                return;
//                            }
//                            cityCode = AddressUtils.getInstance().cityCode(proCode, address.city);
//                            areaCode = AddressUtils.getInstance().areaCode(cityCode, address.district);
//                            setLocationCode(proCode, cityCode, areaCode);
//                            // 如果查询到的市区编码为空，则请求服务器端进行校验
//                            if (TextUtils.isEmpty(cityCode) || TextUtils.isEmpty(areaCode)) {
//                                HttpDAO httpDAO = new HttpDAO(LocationUtils.this);
//                                Map<String, String> map = new HashMap<>();
//                                map.put("paramName", address.province + ":" + address.city + ":" + address.district);
//                                httpDAO.get(Base_call.url_queryareacode, map, Base_call.url_queryareacode);
//
//                            } else { // 如果数据正常则回调
//                                mOnLocationListener.onSuccess(address);
//                            }
//                        } else {
//                            mOnLocationListener.onErr("99", LOCATION_ERROR_CONFIM);
//                        }
//                    } else {
//                        mOnLocationListener.onErr(String.valueOf(location.getLocType()), LOCATION_ERROR_CONFIM);
//                    }
//                }
                LocationInfo info = new LocationInfo();
                info.setBdLocation(location);
                mOnLocationListener.onSuccess(info);
            }
        }
    }


    private void setLocationCode(String proCode, String cityCode, String areaCode) {

    }


    private void initLocation() {
        mLocationClient = new LocationClient(mCtx);
        mLocationListener = new LocationListener();
        mLocationClient.registerLocationListener(mLocationListener); // 注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);// 设置定位模式为高精度
        option.setCoorType(tempcoor);// 返回的定位结果是百度经纬度，默认值gcj02，设置为 bd09ll
        int span = 1000;
        option.setIsNeedAddress(true);  // 返回的定位结果包含地址信息
        option.setScanSpan(span);// 设置发起定位请求的间隔时间为1000ms
        option.setOpenGps(true);// 设置是否使用GPS 定位
        option.disableCache(true);// 禁止启用缓存定位
        mLocationClient.setLocOption(option);
    }


    public interface OnLocationListener {
        void onErr(String retFlag, String reason);

        void onSuccess(Object response);
    }

}
