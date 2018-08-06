package kaizone.songmaya.woo.fragment.a;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kaizone.songmaya.jsyl.retrofitutil.bean.Viewpoint;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yuekaizone on 2017/6/15.
 */

public class TestData {

    private static final String TAG = "TestData";

    public static List<Viewpoint> viewpoints;

    public static List<Viewpoint> getViewpoints() {
        if (viewpoints == null) {
            viewpoints = obtainViewpoints();
        }
        return viewpoints;
    }

    private static List<Viewpoint> obtainViewpoints() {

        Viewpoint viewpoint = new Viewpoint();
        viewpoint.img1 = "http://img.ivsky.com/img/tupian/pre/201704/28/haian_fengjing.jpg";
        viewpoint.img2 = "http://img.ivsky.com/img/tupian/pre/201704/28/haian_fengjing-001.jpg";
        viewpoint.img3 = "http://img.ivsky.com/img/tupian/pre/201704/28/haian_fengjing-002.jpg";
        viewpoint.img4 = "http://img.ivsky.com/img/tupian/pre/201704/28/haian_fengjing-003.jpg";
        viewpoint.img5 = "http://img.ivsky.com/img/tupian/pre/201704/28/haian_fengjing-004.jpg";
        viewpoint.img6 = "http://img.ivsky.com/img/tupian/pre/201704/28/haian_fengjing-005.jpg";
        viewpoint.name = "海岸风景";
        viewpoint.description = "这个海岸风景";

        Viewpoint viewpoint2 = new Viewpoint();
        viewpoint2.img1 = "http://img.ivsky.com/img/tupian/pre/201612/09/grand_canyon_national_park.jpg";
        viewpoint2.img2 = "http://img.ivsky.com/img/tupian/pre/201612/09/grand_canyon_national_park-001.jpg";
        viewpoint2.img3 = "http://img.ivsky.com/img/tupian/pre/201612/09/grand_canyon_national_park-002.jpg";
        viewpoint2.img4 = "http://img.ivsky.com/img/tupian/pre/201612/09/grand_canyon_national_park-004.jpg";
        viewpoint2.img5 = "http://img.ivsky.com/img/tupian/pre/201612/09/grand_canyon_national_park-005.jpg";
        viewpoint2.img6 = "http://img.ivsky.com/img/tupian/pre/201612/09/grand_canyon_national_park-006.jpg";
        viewpoint2.name = "海岸风景";
        viewpoint2.description = "这个海岸风景";

        Viewpoint viewpoint3 = new Viewpoint();
        viewpoint3.img1 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong.jpg";
        viewpoint3.img2 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong-001.jpg";
        viewpoint3.img3 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong-002.jpg";
        viewpoint3.img4 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong-003.jpg";
        viewpoint3.img5 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong-004.jpg";
        viewpoint3.img6 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong-005.jpg";
        viewpoint3.img6 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong-006.jpg";
        viewpoint3.name = "璀璨星空";
        viewpoint3.description = "这个是催催星空";

        Viewpoint viewpoint4 = new Viewpoint();
        viewpoint4.img1 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye.jpg";
        viewpoint4.img2 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye-001.jpg";
        viewpoint4.img3 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye-002.jpg";
        viewpoint4.img4 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye-003.jpg";
        viewpoint4.img5 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye-004.jpg";
        viewpoint4.img6 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye-005.jpg";
        viewpoint4.img6 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye-006.jpg";
        viewpoint4.name = "枫叶";
        viewpoint4.description = "这个是枫叶美景";

        List<Viewpoint> data = new ArrayList<>();
        data.add(viewpoint);
        data.add(viewpoint2);
        data.add(viewpoint3);
        data.add(viewpoint4);
        return data;
    }

    public static Viewpoint getViewpoint(int i) {
        return getViewpoints().get(0);
    }

//    public static class Info {
//        public String name;
//        public String sex;
//        public String folk;
//        public String birthday;
//        public String address;
//        public String num;
//        public String issue;
//        public String period;
//        public String phoneNum;
//
//        public static Info parse(CardInfo cardInfo) {
//            Info obj = new Info();
//            obj.name = cardInfo.getFieldString(TFieldID.NAME);
//            obj.sex = cardInfo.getFieldString(TFieldID.SEX);
//            obj.folk = cardInfo.getFieldString(TFieldID.FOLK);
//            obj.birthday = cardInfo.getFieldString(TFieldID.BIRTHDAY);
//            obj.address = cardInfo.getFieldString(TFieldID.ADDRESS);
//            obj.num = cardInfo.getFieldString(TFieldID.NUM);
//            obj.issue = cardInfo.getFieldString(TFieldID.ISSUE);
//            obj.period = cardInfo.getFieldString(TFieldID.PERIOD);
//            obj.phoneNum = "13109097878";
//            return obj;
//        }
//
//        public static JSONObject parse(Info obj) {
//            JSONObject json = new JSONObject();
//            try {
//                json.putOpt("name", obj.name);
//                json.putOpt("sex", obj.sex);
//                json.putOpt("folk", obj.folk);
//                json.putOpt("birthday", obj.birthday);
//                json.putOpt("address", obj.address);
//                json.putOpt("num", obj.num);
//                json.putOpt("issue", obj.issue);
//                json.putOpt("period", obj.period);
//                json.putOpt("phoneNum", obj.phoneNum);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return json;
//        }
//    }

//    public static void check(CardInfo cardInfo) {
//        try {
//            JSONObject jsonObject = Info.parse(Info.parse(cardInfo));
//            Log.e(TAG, String.format("%s", jsonObject.toString()));
//            String encrypt = DES.encryptDES(jsonObject.toString(), "*()&^%$#");
//            Log.e(TAG, String.format("encrypt %s", encrypt));
//            String source = DES.decryptDES(encrypt, "*()&^%$#");
//            Log.e(TAG, String.format("source %s", source));
//            HashMap map = new HashMap();
//            map.put("ciphertext", encrypt);
//            http("POST", map);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void http(String method, Map<String, String> map) {

        try {
            URL url = new URL("http://10.164.17.173:8080/jsyl/pc/u");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);// 提交模式
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            StringBuffer params = new StringBuffer();
            params.append("?");
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            byte[] bypes = params.toString().getBytes();
            conn.getOutputStream().write(bypes);// 输入参数
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            is.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void post(String url, Map map) {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.url = url;
        requestInfo.map = map;
        requestInfo.method = "POST";
        new MyTask().execute(requestInfo);
    }

    static OkHttpClient client = new OkHttpClient();

    private static String okhttp3post(String url, Map map) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            builder.add(entry.getKey(), entry.getValue());
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    private static class MyTask extends AsyncTask<RequestInfo, Object, Object> {
        @Override
        protected Object doInBackground(RequestInfo... params) {
            try {
                if (params.length >= 1) {
                    RequestInfo requestInfo = params[0];
                    if ("GET".equals(requestInfo.method)) {

                    } else if ("POST".equals(requestInfo.method)) {
                        return okhttp3post(requestInfo.url, requestInfo.map);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static class RequestInfo {
        public String method;
        public String url;
        public Map map;
    }
}
