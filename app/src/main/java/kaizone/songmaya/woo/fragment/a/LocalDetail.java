package kaizone.songmaya.woo.fragment.a;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import kaizone.songmaya.woo.util.SystemUtils;

/**
 * Created by yuekaizone on 2017/8/26.
 */

public class LocalDetail extends Fragment{

    TextView mTextView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSwipeRefreshLayout = new SwipeRefreshLayout(getContext());
        ScrollView scrollView = new ScrollView(getContext());
        mTextView = new TextView(getContext());
        mTextView.setBackgroundColor(Color.BLACK);
        mTextView.setTextColor(Color.RED);
        mSwipeRefreshLayout.addView(mTextView);
        mSwipeRefreshLayout.addView(scrollView);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
            }
        });

        return mSwipeRefreshLayout;
    }

    public void update(){
        StringBuilder sb = new StringBuilder();

        Activity activity = getActivity();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        sb.append("density").append(":").append(dm.density).append("\n");
        sb.append("densityDpi").append(":").append(dm.densityDpi).append("\n");
        sb.append("widthPixels").append(":").append(dm.widthPixels).append("\n");
        sb.append("heightPixels").append(":").append(dm.heightPixels).append("\n");
        sb.append("scaledDensity").append(":").append(dm.scaledDensity).append("\n");
        sb.append("xdpi").append(":").append(dm.xdpi).append("\n");
        sb.append("ydpi").append(":").append(dm.ydpi).append("\n");
        sb.append("Build.ID").append(":").append(Build.ID).append("\n");
        sb.append("Build.MODEL").append(":").append(Build.MODEL).append("\n");
        sb.append("Build.VERSION.RELEASE").append(":").append(Build.VERSION.RELEASE).append("\n");

        sb.append("DeviceId").append(":").append(SystemUtils.getDeviceID(getContext())).append("\n");
        sb.append("DhcpInfo").append(":").append(SystemUtils.obtainDhcpInfo(getActivity())).append("\n");
        sb.append("NetworkInfo").append(":").append(SystemUtils.obtainNetworkInfo(getActivity())).append("\n");
        sb.append("IpAddress").append(":").append(SystemUtils.obtainIpAddress(getActivity())).append("\n");
        mTextView.setText(sb.toString());
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public static final int ID = LocalDetail.class.hashCode();
    public static final String NAME = LocalDetail.class.getSimpleName();
    public static LocalDetail newInstance(Bundle bd) {
        final LocalDetail f = new LocalDetail();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }

}
