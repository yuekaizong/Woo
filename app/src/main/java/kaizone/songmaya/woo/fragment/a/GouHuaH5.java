package kaizone.songmaya.woo.fragment.a;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kaizone.songmaya.baidulbs.LbsManager;
import kaizone.songmaya.baidulbs.entity.LocationInfo;
import kaizone.songmaya.woo.R;
import kaizone.songmaya.woo.util.SystemUtils;
import kaizone.songmaya.woo.util.contact.ContactManager;

/**
 * Created by Kaizo on 2017/12/5.
 */

public class GouHuaH5 extends Fragment implements View.OnClickListener {

    public static final int ID = GouHuaH5.class.hashCode();
    public static final String NAME = GouHuaH5.class.getSimpleName();

    public static GouHuaH5 newInstance(Bundle bd) {
        final GouHuaH5 f = new GouHuaH5();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }

    TextView mTitle;
    WebView mContent;

    Button button1;
    Button button2;

    JsBridge jsBridge;

    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_goweb, null);
        mTitle = (TextView) view.findViewById(R.id.help_title);
        mTitle.setVisibility(View.GONE);
        mContent = (WebView) view.findViewById(R.id.help_content);
        button1 = (Button) view.findViewById(R.id.button1);
        button2 = (Button) view.findViewById(R.id.button2);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh();
            }
        });

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        mTitle.setText("JS android");
//        mContent.setBackgroundColor(getContext().getResources().getColor(R.color.app_bg));
        WebSettings settings = mContent.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        settings.setSupportZoom(true); // 支持缩放
        settings.setJavaScriptEnabled(true);

        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        settings.setAllowFileAccess(true); //设置可以访问文件
//        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        jsBridge = new JsBridge(this, mContent);
        mContent.addJavascriptInterface(jsBridge, "bridge");
        mContent.loadUrl("http://10.164.194.121/static/gouhua/#/");
        mContent.setWebChromeClient(new WebChromeClient());

        return view;
    }

    void swipeRefresh() {
        mContent.reload();
        swipeRefreshLayout.setRefreshing(false);
    }

    private static class JsBridge {

        private Fragment fragment;
        private WebView webView;

        public JsBridge(Fragment context, WebView webView) {
            this.fragment = context;
            this.webView = webView;
        }

        @JavascriptInterface
        public void liveDetect() {
            Log.e(NAME, "jsController live");
            liveDetectSuccess();
        }

        public void liveDetectSuccess() {
            JSONArray array = new JSONArray();
            jsBackCall("liveDetect", "success", array);
        }

        @JavascriptInterface
        public void contacts() {
            if (SystemUtils.checkHasPermission(fragment.getActivity(), Manifest.permission.READ_CONTACTS)) {
                ContactManager contactManager = new ContactManager(fragment);
                JSONArray jsonArray = contactManager.search();
                contactsSuccess(jsonArray);
            }
        }

        public void contactsSuccess(JSONArray jsonArray) {
            jsBackCall("contacts", "success", jsonArray);
        }

        @JavascriptInterface
        public void lbs() {
            if (SystemUtils.checkHasPermission(fragment.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                LbsManager lbsManager = new LbsManager(fragment.getActivity());
                lbsManager.startLocation(new LbsManager.OnLocationListener() {
                    @Override
                    public void onErr(String retFlag, String reason) {
                        Log.e(NAME, retFlag + reason);
                    }

                    @Override
                    public void onSuccess(Object response) {
                        if (response instanceof LocationInfo) {
                            LocationInfo info = (LocationInfo) response;
                            JSONArray body = new JSONArray();
                            JSONObject item = new JSONObject();
                            try {
                                item.put("address", info.getAddressStr());
                                item.put("latitude", info.getLatitude());
                                item.put("longitude", info.getLongitude());
                                body.put(item);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            lbsSuccess(body);
                        }
                    }
                });
            }
        }

        public void lbsSuccess(JSONArray body) {
            jsBackCall("lbs", "success", body);
        }

        public void jsBackCall(final String action, final String status, final JSONArray body) {
            Log.e(NAME, "thread=" + Thread.currentThread().toString());
            fragment.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    jsBackCallNeedMainThread(action, status, body);
                }
            });
        }

        @JavascriptInterface
        public void jsBackCallNeedMainThread(final String action, final String status, final JSONArray body) {
            JSONObject result = new JSONObject();
            try {
                result.put("action", action);
                result.put("status", status);
                result.put("body", body);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e(NAME, result.toString());
            if (Build.VERSION.SDK_INT < 15) {
                String loadUrl = String.format("javascript:receive2(%s)", result.toString());
                webView.loadUrl(loadUrl);
            } else {
                String loadUrl = String.format("javascript:bridgeReceive(%s)", result.toString());
                webView.evaluateJavascript(loadUrl, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.e(NAME, String.format("onReceive value%s", value));
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                androidCallJS();
                break;
            case R.id.button2:
                break;
        }
    }


    public void androidCallJS() {
        JSONObject result = new JSONObject();
        try {
            result.put("action", "button");
            result.put("status", "fail");
            result.put("body", "body");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 15) {
            String loadUrl = String.format("javascript:bridgeReceive(%s)", result.toString());
            mContent.loadUrl(loadUrl);
        } else {
            String loadUrl = String.format("javascript:bridgeReceive(%s)", result.toString());
            mContent.evaluateJavascript(loadUrl, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.e(NAME, String.format("onReceive value%s", value));
                }
            });
        }
    }
}
