package kaizone.songmaya.woo.fragment.a;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import kaizone.songmaya.woo.R;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_goweb, null);
        mTitle = (TextView) view.findViewById(R.id.help_title);
        mContent = (WebView) view.findViewById(R.id.help_content);
        button1 = (Button) view.findViewById(R.id.button1);
        button2 = (Button) view.findViewById(R.id.button2);

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

        mContent.addJavascriptInterface(new JsBridge(), "androidController");
        mContent.loadUrl("http://10.164.17.113:8080/index.html");
        mContent.setWebChromeClient(new WebChromeClient());

        return view;
    }

    private class JsBridge {

        @JavascriptInterface
        public void live() {
            Log.e(NAME, "jsController live");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                jsBackCall("这是android数据");
                break;
            case R.id.button2:
                break;
        }
    }


    public void jsBackCall(Object object) {
        if (Build.VERSION.SDK_INT < 15) {
            String loadUrl = String.format("javascript:receive(%s)", object.toString());
            mContent.loadUrl(loadUrl);
        } else {
            String loadUrl = String.format("javascript:receive(\"%s\")", object.toString());
            mContent.evaluateJavascript(loadUrl, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.e(NAME, String.format("onReceive value%s", value));
                }
            });
        }
    }
}
