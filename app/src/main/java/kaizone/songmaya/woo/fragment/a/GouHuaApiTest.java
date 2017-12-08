package kaizone.songmaya.woo.fragment.a;

import android.app.Activity;
import android.content.pm.PackageInfo;
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaizone.songmaya.haiercash.retrofit.annoation.NeedToken;
import kaizone.songmaya.haiercash.retrofit.beans.CustomerLogin;
import kaizone.songmaya.haiercash.retrofit.beans.Entity;
import kaizone.songmaya.haiercash.retrofit.beans.IsRegister;
import kaizone.songmaya.haiercash.retrofit.beans.Result;
import kaizone.songmaya.haiercash.retrofit.beans.VersionCheck;
import kaizone.songmaya.haiercash.retrofit.service.APIFactory;
import kaizone.songmaya.haiercash.retrofit.service.ApiBuilder;
import kaizone.songmaya.haiercash.retrofit.util.EncryptUtil;
import kaizone.songmaya.haiercash.retrofit.util.HttpUtil;
import kaizone.songmaya.haiercash.retrofit.util.Persistence;
import kaizone.songmaya.haiercash.retrofit.util.SubscriberOnNextListener;
import kaizone.songmaya.woo.ContainerActivity;
import kaizone.songmaya.woo.R;
import kaizone.songmaya.woo.util.RecyclerViewAdapterTemplate;
import kaizone.songmaya.woo.util.SystemUtils;
import kaizone.songmaya.woo.util.Tips;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;


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
                info.append("]").append("\n").append("-------------------------------------").append("\n");
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
                String uri = null;
                for (int i = 0; i < annotations.length; i++) {
                    Annotation annotation = annotations[i];
                    boolean has_token = false;
                    if (annotation instanceof GET) {
                        uri = ((GET) annotation).value();
                    }
                    if (annotation instanceof POST) {
                        uri = ((POST) annotation).value();
                    }
                    if (annotation instanceof PUT) {
                        uri = ((PUT) annotation).value();
                    }
                    if (annotation instanceof DELETE) {
                        uri = ((DELETE) annotation).value();
                    }

                    if (annotation instanceof NeedToken) {
                        has_token = true;
                    }
                    String string = annotation.toString();
                    Log.e(TAG, "getAnnactionApiServices: " + string);
                    if (string.contains("value=")) {
                        int subindex = string.lastIndexOf("/");
                        if (subindex > 0) {
                            arrayList.add(String.format("%s, %s", string.substring(subindex), has_token ? "sToken" : ""));
                        }
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
        if (obj.contains("GouHuaApiDetail")) {
            ContainerActivity.to(getContext(), GouHuaApiDetailTest.ID);
        } else if (obj.contains("check")) {
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
        } else if (obj.contains("getBankCard")) {
            getBankCard();
        } else if (obj.contains("getBankInfo")) {
            getBankInfo();
        } else if (obj.contains("token")) {
            token();
        } else if (obj.contains("getBankList")) {
            getBankList();
        }
    }

    //检测版本号
    public void versionCheck() {
        APIFactory.getInstance().versionCheck(new SubscriberOnNextListener<Result<VersionCheck>>() {
            @Override
            public void next(Result result) {
                Tips.toDialog(getContext(), result.toString());
            }
        }, getContext(), "android", "gh", "1.2.1", "haiercash");
    }

    //版本下载
    public void versionDownload() {
        APIFactory.getInstance().versionDownload(new SubscriberOnNextListener<Result>() {
            @Override
            public void next(Result result) {
                Tips.toDialog(getContext(), result.toString());
            }
        }, getContext(), "android", "gh", getVersion(), "haiercash");
    }

    /* 6.102.	(GET) 系统参数列表查询*/
    public void selectByParams() {
//        APIFactory.getInstance().selectByParams(new SubscriberOnNextListener<Result>() {
//            @Override
//            public void next(Result result) {
//
//            }
//        }, getContext(), "app_Personal");

        new ApiBuilder()
                .context(getContext())
                .nextListener(new SubscriberOnNextListener<Entity>() {
                    @Override
                    public void next(Entity result) {
                        Tips.toDialog(getActivity(), result.toString());
                    }
                })
                .selectByParams("app_Personal");
    }

    /*查询客户信息*/
    public void queryPerCustInfo() {
        new ApiBuilder().nextListener(new SubscriberOnNextListener<Entity>() {
            @Override
            public void next(Entity entity) {

            }
        }).queryPerCustInfo("13167066861");
    }


    public void getHomePhoto() {
        APIFactory.getInstance().getHomePhoto(new SubscriberOnNextListener<Result>() {
            @Override
            public void next(Result result) {
                Tips.toDialog(getContext(), result.toString());
            }
        }, getContext(), getSizeType(getActivity()));
    }

    public void isRegister() {
        APIFactory.getInstance().isRegister(new SubscriberOnNextListener<Result<IsRegister>>() {
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

/*        APIFactory.getInstance().saveUauthUsers(new SubscriberOnNextListener<Result>() {
            @Override
            public void next(Result result) {
                showDialog(result.toString());
            }
        }, getContext(), verifyNo, mobileEncry, passwordEncry, deviceId);*/
        APIFactory.getInstance().saveUauthUsers(new SubscriberOnNextListener<Result>() {
            @Override
            public void next(Result result) {
                Tips.toDialog(getContext(), result.toString());
            }
        }, getContext(), map);
    }

    public void customerLogin() {
        String type = "login";
        String deviceId = SystemUtils.getDeviceID(getContext());
        deviceId = "862853039717938";
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
/*        APIFactory.getInstance().customerLogin(new SubscriberOnNextListener<Result<CustomerLogin>>() {
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
        APIFactory.getInstance().customerLogin(new SubscriberOnNextListener<Result<CustomerLogin>>() {
                                                   @Override
                                                   public void next(Result<CustomerLogin> result) {
                                                       Tips.toDialog(getContext(), result.toString());
                                                       Persistence.saveCustomerLogin(getContext(), result.body);

                                                       CustomerLogin customerLogin = Persistence.getCustomerLogin(getContext());
                                                       HttpUtil.sTokenStr = customerLogin.token.access_token;
                                                       HttpUtil.sClientSecret = customerLogin.clientSecret;
                                                       Log.e(TAG, customerLogin.toString());
                                                   }
                                               },
                getContext(), map);
    }

    private void getBankCard() {
        new ApiBuilder()
                .context(getContext())
                .nextListener(new SubscriberOnNextListener<Entity>() {
                    @Override
                    public void next(Entity result) {
                        Tips.toDialog(getActivity(), result.toString());
                    }
                }).getBankCard("C201710251005773730250");
    }

    private void getBankInfo() {
        new ApiBuilder()
                .context(getContext())
                .nextListener(new SubscriberOnNextListener<Entity>() {
                    @Override
                    public void next(Entity result) {
                        Tips.toDialog(getActivity(), result.toString());
                    }
                })
                .getBankInfo("6226660605524061");
    }

    private void getBankList() {
        new ApiBuilder()
                .context(getContext())
                .nextListener(new SubscriberOnNextListener<Entity>() {
                    @Override
                    public void next(Entity entity) {
                        Tips.toDialog(getActivity(), entity.toString());
                    }
                }).getBankList();
    }

    private void token() {
        String grant_type = "client_credentials";
        String userid = "13167066861";
        String deviceId = "862853039717938";
        String client_id = EncryptUtil.simpleEncrypt("AND-" + deviceId + "-" + userid);

        new ApiBuilder()
                .context(getContext())
                .nextListener(new SubscriberOnNextListener<Entity>() {
                    @Override
                    public void next(Entity result) {
                        Tips.toDialog(getActivity(), result.toString());
                    }
                })
                .token(HttpUtil.sClientSecret, grant_type, client_id);
    }

    public void getCustLoanCodeAndRatCRM() {
        String custNo = "";
        String typGrp = "02";
        APIFactory.getInstance().getCustLoanCodeAndRatCRM(new SubscriberOnNextListener<Result>() {
            @Override
            public void next(Result result) {
                Tips.toDialog(getActivity(), result.toString());
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
