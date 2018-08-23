package kaizone.songmaya.woo.fragment.a;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaizone.songmaya.datamanager.retrofit.annoation.NeedToken;
import kaizone.songmaya.datamanager.retrofit.beans.CustomerLogin;
import kaizone.songmaya.datamanager.retrofit.beans.Entity;
import kaizone.songmaya.datamanager.retrofit.beans.IsRegister;
import kaizone.songmaya.datamanager.retrofit.beans.Result;
import kaizone.songmaya.datamanager.retrofit.beans.VersionCheck;
import kaizone.songmaya.datamanager.retrofit.service.APIFactory;
import kaizone.songmaya.datamanager.retrofit.service.ApiBuilder;
import kaizone.songmaya.datamanager.retrofit.util.EncryptUtil;
import kaizone.songmaya.datamanager.retrofit.util.HttpUtil;
import kaizone.songmaya.datamanager.retrofit.util.Persistence;
import kaizone.songmaya.datamanager.retrofit.util.SubscriberOnErrorListener;
import kaizone.songmaya.datamanager.retrofit.util.SubscriberOnNextListener;
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

    RecyclerView mTopView;
    RecyclerViewAdapterTemplate mTopAdapter;

    List<String> mData = new ArrayList<>();
    List<String> mTopDate = new ArrayList<>();

    private int REQUEST_CODE = 110;

    private Uri mImageUri;
    private File outputImage;
    private List<File> outputFiles = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mData.add("Annotation");
        mData.add("GouHuaApiDetail");
        mData.addAll(getAnnactionApiServices());

        mTopDate.add("http://pic.jj20.com/up/allimg/911/101416132J5/161014132J5-1.jpg");
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

        mTopView = (RecyclerView) view.findViewById(R.id.top);
        LinearLayoutManager topLayoutManager = new LinearLayoutManager(getContext());
        topLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTopView.setLayoutManager(topLayoutManager);
        mTopAdapter = new RecyclerViewAdapterTemplate(mTopDate, R.layout.a_item);
        mTopAdapter.setDataBindView(new RecyclerViewAdapterTemplate.DataBindView() {
            @Override
            public void bind(RecyclerViewAdapterTemplate.ViewHolder holder, int position, List data) {
                final int i = position;
                String obj = data.get(position).toString();
                TextView textView = (TextView) holder.findViewId(R.id.text);
                textView.setText(obj);
                SimpleDraweeView draweeView = (SimpleDraweeView) holder.findViewId(R.id.drawee);
                if (obj.startsWith("/")) {
                    obj = "file:/" + obj;
                }
                draweeView.setImageURI(obj);
                draweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCamera(REQUEST_CODE + i);
                    }
                });
            }
        });
        mTopView.setAdapter(mTopAdapter);

        return view;
    }

    public void openCamera(int requestCode) {
        int permissionState = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if (permissionState == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA
            }, requestCode);
            return;
        }
        outputImage = new File(Environment.getExternalStorageDirectory(), String.format("tempImage_%s.jpg", requestCode));
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mImageUri = FileProvider.getUriForFile(getContext(), "kaizone.songmaya.woo.FileProvider", outputImage);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTopAdapter.add(mImageUri);
        outputFiles.add(outputImage);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean permissionGranted = true;
        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                int grant = grantResults[i];
                if (grant == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getContext(), "权限拒绝" + permissions[i], Toast.LENGTH_SHORT).show();
                    permissionGranted = false;
                }
            }

            if (permissionGranted) {
                openCamera(REQUEST_CODE);
            }
        }
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
        String classpath = "kaizone.songmaya.haiercash.retrofit.service.ApiRepository";
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
        } else if (obj.contains("login")) {
            smartNsLogin();
        } else if (obj.contains("setup1")) {
            smartNsSetup1();
        } else if (obj.contains("setup2")) {
            smartNsSetup2();
        }
    }

    public void smartNsLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", "13167066861");
        map.put("deviceId", "DE123456");
        map.put("password", "a123456");
        new ApiBuilder().context(getContext()).nextListener(new SubscriberOnNextListener<Entity>() {
            @Override
            public void next(Entity entity) {
                Toast.makeText(getContext(), entity.toString(), Toast.LENGTH_SHORT).show();
                Result<CustomerLogin> result = (Result<CustomerLogin>) entity;
                Persistence.saveCustomerLogin(getContext(), result.body);

                CustomerLogin customerLogin = Persistence.getCustomerLogin(getContext());
                HttpUtil.sTokenStr = customerLogin.token.access_token;
                HttpUtil.sClientSecret = customerLogin.clientSecret;
                Log.e(TAG, customerLogin.toString());
            }
        }).errorListener(new SubscriberOnErrorListener() {
            @Override
            public void onError(Throwable e) {

            }
        }).smartNsLogin(map);
    }

    public void smartNsSetup1() {
        Map<String, String> map = new HashMap<>();
        map.put("ssoId", "13167066861");
        map.put("contacts", "lulu");
        map.put("profession", "码农");
        map.put("carState", "3");


        new ApiBuilder().context(getContext()).nextListener(new SubscriberOnNextListener<Entity>() {
            @Override
            public void next(Entity entity) {
                Toast.makeText(getContext(), entity.toString(), Toast.LENGTH_SHORT).show();
            }
        }).errorListener(new SubscriberOnErrorListener() {
            @Override
            public void onError(Throwable e) {

            }
        }).smartNsSetup1(map);
    }

    public void smartNsSetup2() {
        try {
            if (outputFiles.isEmpty()) {
                return;
            }

            if (outputFiles.size() < 3) {
                return;
            }

            File idFrontFile = outputFiles.get(0);
            File idReverseFile = outputFiles.get(1);
            File idHoldFile = outputFiles.get(2);
            Map<String, String> map = new HashMap<>();

            map.put("ssoId", "13167066861");
            map.put("idFront", base64File(idFrontFile));
            map.put("idReverse", base64File(idReverseFile));
            map.put("idHold", base64File(idHoldFile));

            new ApiBuilder().context(getContext()).nextListener(new SubscriberOnNextListener<Entity>() {
                @Override
                public void next(Entity entity) {
                    Toast.makeText(getContext(), entity.toString(), Toast.LENGTH_SHORT).show();
                }
            }).errorListener(new SubscriberOnErrorListener() {
                @Override
                public void onError(Throwable e) {
                    Log.e(TAG,"smartNsSetup2", e);
                }
            }).smartNsSetup2(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String base64File(File file) throws Exception {
        FileInputStream in = new FileInputStream(file);
        byte[] b = new byte[in.available()];
        in.read(b);
        return Base64.encodeToString(b, Base64.NO_WRAP);
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
