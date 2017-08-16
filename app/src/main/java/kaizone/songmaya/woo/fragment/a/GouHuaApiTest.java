package kaizone.songmaya.woo.fragment.a;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haiercash.gouhua.retrofit.beans.CustomerLogin;
import com.haiercash.gouhua.retrofit.beans.IsRegister;
import com.haiercash.gouhua.retrofit.beans.Result;
import com.haiercash.gouhua.retrofit.beans.VersionCheck;
import com.haiercash.gouhua.retrofit.service.APIFactory;
import com.haiercash.gouhua.retrofit.util.HttpUtil;
import com.haiercash.gouhua.retrofit.util.Persistence;
import com.haiercash.gouhua.retrofit.util.SubscriberOnNextListenter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaizone.songmaya.woo.ContainerActivity;
import kaizone.songmaya.woo.R;
import kaizone.songmaya.woo.util.EncryptUtil;
import kaizone.songmaya.woo.util.RecyclerViewAdapterTemplate;
import kaizone.songmaya.woo.util.SystemUtils;
import kaizone.songmaya.woo.util.Tips;


/**
 * Created by yuelb on 2017/7/15.
 */

public class GouHuaApiTest extends Fragment {
    public static final int ID = GouHuaApiTest.class.hashCode();
    public static final String NAME = GouHuaApiTest.class.getSimpleName();
    public static final String TAG = "GouHuaApiTest";

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    RecyclerViewAdapterTemplate mAdapter;

    List<String> mData = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mData.add("Annotation");
        mData.add("GouHuaApiDetail");
        mData.addAll(getAnnactionApiServices());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.gouhua_apitest_panel, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerViewAdapterTemplate(mData, R.layout.gouhua_apitest_panel_item);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDataBindView(new RecyclerViewAdapterTemplate.DataBindView() {
            @Override
            public void bind(RecyclerViewAdapterTemplate.ViewHolder holder, final int position, List data) {
                final String obj = (String) data.get(position);
                String text = String.format("%s", obj);
                TextView textView = (TextView) holder.findViewId(R.id.text1);
                textView.setText(text);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler(position, obj);
                    }
                });
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                StringBuilder sb = new StringBuilder();
                sb.append(SystemUtils.obtainIpAddress(getActivity())).append("\n");
                sb.append(SystemUtils.obtainDhcpInfo(getActivity())).append("\n");
                Tips.toDialog(getContext(), sb.toString());
            }
        });

        return view;
    }

    public static String getAnnactionInfo() {
        String classpath = "com.haiercash.gouhua.retrofit.service.ApiService";
        StringBuilder info = new StringBuilder();
        try {
            Method[] methods = Class.forName(classpath).getMethods();
            for (Method method : methods) {
                String name = method.getName();
                Annotation[] annotations = method.getAnnotations();
                info.append(String.format("method=%s", name)).append("[");
                for (Annotation annotation : annotations) {
                    info.append(String.format("%s,%s", annotation.annotationType(), annotation.toString())).append(",");
                }
                info.append("]");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return info.toString();
    }

    public static List<String> getAnnactionApiServices() {
        String classpath = "com.haiercash.gouhua.retrofit.service.ApiService";
        ArrayList arrayList = new ArrayList();
        try {
            Method[] methods = Class.forName(classpath).getMethods();
            for (Method method : methods) {
                Annotation[] annotations = method.getAnnotations();
                for (int i = 0; i < annotations.length; i++) {
                    Annotation annotation = annotations[i];
                    String string = annotation.toString();
                    Log.e(TAG, "getAnnactionApiServices: " + string);
                    if (string.contains("value=")) {
                        int subindex = string.lastIndexOf("/");
                        arrayList.add(string.substring(subindex));
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    private void handler(int i, String obj) {
        switch (i) {
            case 0:
                Tips.toDialog(getContext(), getAnnactionInfo());
                break;
        }
        if(obj.contains("GouHuaApiDetail")){
            ContainerActivity.to(getContext(), GouHuaApiDetailTest.ID);
        }
        else if (obj.contains("check")) {
            versionCheck();
        } else if (obj.contains("download")) {
            versionDownload();
        } else if (obj.contains("getHomePhoto")) {
            getHomePhoto();
        } else if (obj.contains("isRegister")) {
            isRegister();
        } else if (obj.contains("queryPerCustInfo")) {
            queryPerCustInfo();
        } else if (obj.contains("saveUauthUsers")) {
            saveUauthUsers();
        } else if (obj.contains("selectByParams")) {
            selectByParams();
        } else if (obj.contains("customerLogin")) {
            customerLogin();
        } else if (obj.contains("getCustLoanCodeAndRatCRM")) {
            getCustLoanCodeAndRatCRM();
        }
    }

    //检测版本号
    public void versionCheck() {
        APIFactory.getInstance().versionCheck(new SubscriberOnNextListenter<Result<VersionCheck>>() {
            @Override
            public void next(Result result) {
                Tips.toDialog(getContext(), result.toString());
            }
        }, getContext(), "android", "gh", getVersion(), "haiercash");
    }

    //版本下载
    public void versionDownload() {
        APIFactory.getInstance().versionDownload(new SubscriberOnNextListenter<Result>() {
            @Override
            public void next(Result result) {
                Tips.toDialog(getContext(), result.toString());
            }
        }, getContext(), "android", "gh", getVersion(), "haiercash");
    }

    /* 6.102.	(GET) 系统参数列表查询*/
    public void selectByParams() {
        APIFactory.getInstance().selectByParams(new SubscriberOnNextListenter<Result>() {
            @Override
            public void next(Result result) {

            }
        }, getContext(), "app_Personal");
    }

    /*查询客户信息*/
    public void queryPerCustInfo() {
        APIFactory.getInstance().queryPerCustInfo(new SubscriberOnNextListenter<Result>() {
            @Override
            public void next(Result result) {
                Log.e(TAG, result.toString());
            }
        }, getContext(), "13167066861");
    }


    public void getHomePhoto() {
        APIFactory.getInstance().getHomePhoto(new SubscriberOnNextListenter<Result>() {
            @Override
            public void next(Result result) {
                Tips.toDialog(getContext(), result.toString());
            }
        }, getContext(), getSizeType(getActivity()));
    }

    public void isRegister() {
        APIFactory.getInstance().isRegister(new SubscriberOnNextListenter<Result<IsRegister>>() {
            @Override
            public void next(Result<IsRegister> result) {
                Tips.toDialog(getContext(), result.toString());
            }
        }, getContext(), "13167066861");
    }

    public void saveUauthUsers() {
        String verifyNo = "2314";
        String mobile = "13167066862";
        String password = "a12345";
        String deviceId = SystemUtils.getDeviceID(getContext());
        String passwordEncry = EncryptUtil.simpleEncrypt(password);
        String mobileEncry = EncryptUtil.simpleEncrypt(mobile);

        Map map = new HashMap();
        map.put("verifyNo", verifyNo);
        map.put("mobile", mobileEncry);
        map.put("password", passwordEncry);
        map.put("deviceId", deviceId);

/*        APIFactory.getInstance().saveUauthUsers(new SubscriberOnNextListenter<Result>() {
            @Override
            public void next(Result result) {
                showDialog(result.toString());
            }
        }, getContext(), verifyNo, mobileEncry, passwordEncry, deviceId);*/
        APIFactory.getInstance().saveUauthUsers(new SubscriberOnNextListenter<Result>() {
            @Override
            public void next(Result result) {
                Tips.toDialog(getContext(), result.toString());
            }
        }, getContext(), map);
    }

    public void customerLogin() {
        String type = "login";
        String deviceId = SystemUtils.getDeviceID(getContext());
        deviceId="862853039717938";
        String userId = "13167066861";
        String deviceType = "AND";
        String userIdEncrypt = EncryptUtil.simpleEncrypt(userId);
        String passwordEncrypt = EncryptUtil.simpleEncrypt("a123456");
        String deviceIdEncrypt = EncryptUtil.simpleEncrypt("AND-" + deviceId + "-" + userId);
        Map map = new HashMap();
        map.put("type", type);
        map.put("deviceType", deviceType);
        map.put("deviceId", deviceIdEncrypt);
        map.put("userId", userIdEncrypt);
        map.put("password", passwordEncrypt);
/*        APIFactory.getInstance().customerLogin(new SubscriberOnNextListenter<Result<CustomerLogin>>() {
                                                   @Override
                                                   public void next(Result<CustomerLogin> result) {
                                                       showDialog(result.toString());
                                                   }
                                               },
                getContext(),
                type,
                userIdEncrypt,
                passwordEncrypt,
                deviceIdEncrypt);*/
        APIFactory.getInstance().customerLogin(new SubscriberOnNextListenter<Result<CustomerLogin>>() {
                                                   @Override
                                                   public void next(Result<CustomerLogin> result) {
                                                       Tips.toDialog(getContext(), result.toString());
                                                       Persistence.saveCustomerLogin(getContext(), result.body);

                                                       CustomerLogin customerLogin = Persistence.getCustomerLogin(getContext());
                                                       HttpUtil.token = customerLogin.token.access_token;
                                                       Log.e(TAG, customerLogin.toString());
                                                   }
                                               },
                getContext(), map);
    }

    public void getCustLoanCodeAndRatCRM() {
        String custNo = "";
        String typGrp = "02";
        APIFactory.getInstance().getCustLoanCodeAndRatCRM(new SubscriberOnNextListenter<Result>() {
            @Override
            public void next(Result result) {

            }
        }, getContext(), custNo, typGrp);
    }

    public String getVersion() {
        PackageInfo info = null;
        String verson = "";
        try {
            info = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            verson = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verson;
    }

    /**
     * 获取手机分辨率的倍数
     */
    public static String getSizeType(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float density = metrics.density;
        if (1.5 == density) {
            return "AND480";
        } else if (2.0 == density) {
            return "AND720";
        } else if (3.0 == density) {
            return "AND1080";
        } else {
            return "AND1080";
        }
    }

    public static GouHuaApiTest newInstance(Bundle bd) {
        final GouHuaApiTest f = new GouHuaApiTest();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }

}
