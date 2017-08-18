package com.haiercash.gouhua.retrofit.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.haiercash.gouhua.retrofit.service.ApiService;

import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HttpUtil {
    private static final String TAG = "HttpUtil";
    private static Retrofit retrofit;
    private static Retrofit sRetrofit;
    private static HttpUtil httpUtil = new HttpUtil();
    public static String token;

    private static final String BASE_URL = "http://10.164.194.121:9000/"; //测试环境
//    private static final String BASE_URL = "http://10.164.194.121/"; //封试环境

    //Rerofit中封装了超时处理机制，拦截器
    private HttpUtil() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private HttpUtil(Context context) {
        sRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    static public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }

    static public ApiService getApiService(Context context) {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return sRetrofit.create(ApiService.class);
    }


    //设置事件注册
    static public <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWhenNetworkException())//超时处理机制
                .subscribe(s);
    }


    private final static long DEFAULT_TIMEOUT = 10;

    private OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        httpClientBuilder.retryOnConnectionFailure(true);//错误重连
        httpClientBuilder.addInterceptor(new LoggingInterceptor());
        //设置缓存
//        File httpCacheDirectory = new File(FileUtils.getCacheDir(SolidApplication.getInstance()), "OkHttpCache");
//        httpClientBuilder.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
        return httpClientBuilder.build();
    }

    private static OkHttpClient getOkHttpClient(Context context) {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        httpClientBuilder.retryOnConnectionFailure(true);//错误重连
        httpClientBuilder.addInterceptor(new MyInterceptor(context));
        //设置缓存
//        File httpCacheDirectory = new File(FileUtils.getCacheDir(SolidApplication.getInstance()), "OkHttpCache");
//        httpClientBuilder.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
        return httpClientBuilder.build();
    }


    //OKHTTP的拦截器
    //官方的栗子
    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //拿到request实例在此对请求做需要的设置
            Request request = chain.request();
            long t1 = System.nanoTime();
//            logger.info(String.format("Sending request %s on %s%n%s",
//                    request.url(), chain.connection(), request.headers()));
            //发送request请求
            Response response = chain.proceed(request);


            //得到请求后的response实例，做相应操作
            long t2 = System.nanoTime();
//            logger.info(String.format("Received response for %s in %.1fms%n%s",
//                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            Log.e("ZHT", (t2 - t1) / 1000_000 + " ms"); //计算请求时间
            return response;
        }
    }

    private static class MyInterceptor implements Interceptor {
        Context mContext;

        public MyInterceptor(Context context) {
            this.mContext = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Connection", "close")
                    .addHeader("APPVersion", "AND-P-" + appVersion(mContext))
                    .addHeader("DeviceModel", "AND-P-" + android.os.Build.MODEL)
                    .addHeader("DeviceResolution", "AND-P-" + getDeviceWidth(mContext) + "," + getDeviceHeight(mContext))
                    .addHeader("SysVersion", "AND-P-" + android.os.Build.VERSION.RELEASE)
                    .addHeader("channel", "18")
                    .addHeader("channel_no", "42");
            if (token != null) {
                builder.addHeader("Authorization", "Bearer" + token)
                        .addHeader("access_token", token);
            }
            Request request = builder.build();
            Response response = chain.proceed(request);
            long peekBodySize = 1024*1024;
            byte[] clone = response.peekBody(peekBodySize).bytes();
            Log.e(TAG, String.format("intercept: request url=%s, method=%s", request.url(), request.method()));
            String body = new String(clone);
            final int bodylength = body.getBytes().length;
            Log.e(TAG, String.format("intercept: response body =%s >>>body length=%s", body, bodylength));
            return response;
        }
    }

    public static String appVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.haiercash.gouhua", PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getDeviceWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowMgr.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getDeviceHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowMgr.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


    //缓存拦截器
    class CacheControlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
//            if (!AppUtils.isNetworkConnected(mContext)) {
//                request = request.newBuilder()
//                        .cacheControl(CacheControl.FORCE_CACHE)
//                        .build();
//            }
//
            Response response = chain.proceed(request);
//
//            if (AppUtils.isNetworkConnected(mContext)) {
//                int maxAge = 60 * 60; // read from cache for 1 minute
//                response.newBuilder()
//                        .removeHeader("Pragma")
//                        .header("Cache-Control", "public, max-age=" + maxAge)
//                        .build();
//            } else {
//                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//                response.newBuilder()
//                        .removeHeader("Pragma")
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .build();
//            }
            return response;
        }
    }

}
