package kaizone.songmaya.utils;

import android.app.Activity;
import android.text.TextUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaizone.songmaya.haiercash.retrofit.beans.Entity;
import kaizone.songmaya.haiercash.retrofit.service.ApiBuilder;
import kaizone.songmaya.haiercash.retrofit.util.EncryptUtil;
import kaizone.songmaya.haiercash.retrofit.util.SubscriberOnErrorListener;
import kaizone.songmaya.haiercash.retrofit.util.SubscriberOnNextListener;

/**
 * Created by yuelibiao on 2017/12/8.
 */

public class DataService {

    private Activity activity;


    public void edApplInfoAndRiskInfo() {
        String custNo = "";
        String applSeq = "";
        String name = "";
        String mobile = "";
        String idNo = "";
        String lon = "";
        String lat = "";
        String getCity = "";
        String cityCode = "";
        String deviceId = "";

        Map<String, Object> map = new HashMap<>();
        map.put("custNo", custNo);
        if (!TextUtils.isEmpty(applSeq)) {
            map.put("flag", "2");
            map.put("applSeq", applSeq);
        } else {
            map.put("flag", "0");
        }
        //map.put("expectCredit", "");//期望额度

        //新风险采集接口，上传经纬度、市名称、市编码
        List<Map<String, Object>> listRisk = new ArrayList<>();


        Map<String, Object> listMap1 = new HashMap<>();
        listMap1.put("dataTyp", "04");
        listMap1.put("source", "2");
        listMap1.put("content", new String[]{EncryptUtil.simpleEncrypt("经度" + lon + "纬度" + lat)});
        listMap1.put("idNo", EncryptUtil.simpleEncrypt(idNo));
        listMap1.put("name", EncryptUtil.simpleEncrypt(name));
        listMap1.put("mobile", EncryptUtil.simpleEncrypt(mobile));
        listRisk.add(listMap1);//经纬度信息收集

        Map<String, Object> listMap2 = new HashMap<>();
        listMap2.put("dataTyp", "A504");
        listMap2.put("source", "2");
        listMap2.put("content", new String[]{EncryptUtil.simpleEncrypt(getCity)});
        listMap2.put("idNo", EncryptUtil.simpleEncrypt(idNo));
        listMap2.put("name", EncryptUtil.simpleEncrypt(name));
        listMap2.put("mobile", EncryptUtil.simpleEncrypt(mobile));
        listRisk.add(listMap2);//市名称信息收集

        Map<String, Object> listMap3 = new HashMap<>();
        listMap3.put("dataTyp", "A502");
        listMap3.put("source", "18");
        listMap3.put("content", new String[]{EncryptUtil.simpleEncrypt(cityCode)});
        listMap3.put("idNo", EncryptUtil.simpleEncrypt(idNo));
        listMap3.put("name", EncryptUtil.simpleEncrypt(name));
        listMap3.put("mobile", EncryptUtil.simpleEncrypt(mobile));
        listRisk.add(listMap3);//市code信息收集

        Map<String, Object> listMap4 = new HashMap<>();
        if (!TextUtils.isEmpty(deviceId)) {
            listMap4.put("dataTyp", "A505");
            listMap4.put("source", "2");
            listMap4.put("content", new String[]{EncryptUtil.simpleEncrypt(deviceId)});
            listMap4.put("idNo", EncryptUtil.simpleEncrypt(idNo));
            listMap4.put("name", EncryptUtil.simpleEncrypt(name));
            listMap4.put("mobile", EncryptUtil.simpleEncrypt(mobile));
            listRisk.add(listMap4);//设备号
        }
        map.put("listRiskMap", listRisk);

        new ApiBuilder().context(activity)
                .nextListener(new SubscriberOnNextListener<Entity>() {
                    @Override
                    public void next(Entity entity) {

                    }
                })
                .errorListener(new SubscriberOnErrorListener() {
                    @Override
                    public void onError(Throwable e) {

                    }
                })
                .edApplInfoAndRiskInfo(map);
    }
}
