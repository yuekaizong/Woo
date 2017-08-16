package kaizone.songmaya.jsyl.retrofitutil.user;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kaizone.songmaya.jsyl.retrofitutil.bean.NewsDetailBean;
import kaizone.songmaya.jsyl.retrofitutil.bean.NewsListBean;
import kaizone.songmaya.jsyl.retrofitutil.bean.Notice;
import kaizone.songmaya.jsyl.retrofitutil.bean.Result;
import kaizone.songmaya.jsyl.retrofitutil.bean.User;
import kaizone.songmaya.jsyl.retrofitutil.bean.VersionBean;
import kaizone.songmaya.jsyl.retrofitutil.bean.Viewpoint;
import kaizone.songmaya.jsyl.retrofitutil.util.HttpUtil;
import kaizone.songmaya.jsyl.retrofitutil.util.ProgressCancelListener;
import kaizone.songmaya.jsyl.retrofitutil.util.ProgressSubscriber;
import kaizone.songmaya.jsyl.retrofitutil.util.SubscriberOnErrorListener;
import kaizone.songmaya.jsyl.retrofitutil.util.SubscriberOnNextListenter;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * 建议将网络的API接口都放在这个类里
 */
public class APIFactory {
    //单例模式
    public static APIFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }
    private static class SingletonHolder {
        private static final APIFactory INSTANCE = new APIFactory();
    }


    /**
     * @param nextListenter
     * @param mContext
     *
     * APIFactory.getInstance().getVersion(new SubscriberOnNextListenter<VersionBean>() {
            @Override
            public void next(VersionBean versionBean) {
                Toast.makeText(mContext,"封装：\n"+ versionBean.toString(), Toast.LENGTH_SHORT).show();
            }
        }, mContext);
     */
    //如果有需要，传入更多参数para 用于网络请求
    public void getVersion(SubscriberOnNextListenter<VersionBean> nextListenter, Context mContext) {
        //Rerofit中封装了超时处理机制，拦截器
        Observable<VersionBean> call = HttpUtil.getApiService().getVersionRxjava();
//        HttpUtil.toSubscribe(call, new ProgressSubscriber<VersionBean>(nextListenter, mContext));
        HttpUtil.toSubscribe(call, new ProgressSubscriber<VersionBean>(nextListenter, new ProgressCancelListener() {
            @Override
            public void onCancelProgress() {
                Log.w("ZHT","dialog cancle");
            }
        }, mContext));
    }


    public void getNewsList(SubscriberOnNextListenter<NewsListBean> nextListenter, Context mContext) {
        Observable<NewsListBean> call = HttpUtil.getApiService().getNewsList();
        HttpUtil.toSubscribe(call, new ProgressSubscriber<NewsListBean>(nextListenter, mContext,false));
    }

    public void getNewsDetail(SubscriberOnNextListenter<NewsDetailBean> nextListenter, Context mContext
        ,String id) {
        Observable<NewsDetailBean> call = HttpUtil.getApiService().getNewsDetail(id);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<NewsDetailBean>(nextListenter, mContext));
    }

    public void getNotice(SubscriberOnNextListenter<Result<Notice>> nextListenter, Context context){
        Observable<Result<Notice>> call = HttpUtil.getApiService().getNotice();
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<Notice>>(nextListenter, new ProgressCancelListener() {
            @Override
            public void onCancelProgress() {
                Log.w("ZHT","dialog cancle");
            }
        }, context));
    }

    public void getCreate(SubscriberOnNextListenter<Result> nextListenter, Context context, User user){
//        Observable<Result> call = HttpUtil.getApiService().doCreate(user, "android");
        Observable<Result> call = HttpUtil.getApiService().doCreate(user.name,
                user.loginName, user.password, user.email, user.description, "android");
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result>(nextListenter, context));
    }

    public void getViewpointAll(SubscriberOnNextListenter<Result<List<Viewpoint>>> nextListenter,
                                SubscriberOnErrorListener errorListener,
                                Context context){
        Observable<Result<List<Viewpoint>>> call = HttpUtil.getApiService().getViewpointAll();
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<List<Viewpoint>>>(nextListenter, errorListener, context));
    }

    public void getViewpointId(SubscriberOnNextListenter<Result<Viewpoint>> nextListenter, Context context, Integer id){
        Observable<Result<Viewpoint>> call = HttpUtil.getApiService().getViewpointId(id);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<Viewpoint>>(nextListenter, context));
    }

    public void updateViewpoint(SubscriberOnNextListenter<Result<Viewpoint>> nextListenter, Context context, String uri, String name){
        File file = new File(uri);
        RequestBody body = RequestBody.create(MediaType.parse("image/png"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), body);
        Observable<Result<Viewpoint>> call = HttpUtil.getApiService().updateViewpoint(part, name);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<Viewpoint>>(nextListenter, context));
    }

    public void updateViewpoint(SubscriberOnNextListenter<Result<Viewpoint>> nextListenter, Context context, List<String> uris, String name){
        Iterator<String> it = uris.iterator();
        List<MultipartBody.Part> parts = new ArrayList<>();
        while (it.hasNext()) {
            String entry = it.next();
            File file = new File(entry);
            RequestBody body = RequestBody.create(MediaType.parse("image/png"),file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), body);
            parts.add(part);
        }
        Observable<Result<Viewpoint>> call = HttpUtil.getApiService().updateViewpoint(parts, name);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<Viewpoint>>(nextListenter, context));
    }

    public void updateViewpoint(SubscriberOnNextListenter<Result<Viewpoint>> nextListenter, Context context, Map<String, String> map, String name){
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        Map<String, MultipartBody.Part> partMap = new HashMap<>();

        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            File file = new File(value);
            RequestBody body = RequestBody.create(MediaType.parse("image/png"),file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), body);
            partMap.put(key, part);
        }
        Observable<Result<Viewpoint>> call = HttpUtil.getApiService().updateViewpoint(partMap, name);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<Viewpoint>>(nextListenter, context));
    }

    public void updateViewpoint1(SubscriberOnNextListenter<Result<Viewpoint>> nextListenter, Context context, Map<String, String> map, String name){
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        Map<String, RequestBody> partMap = new HashMap<>();

        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            File file = new File(value);
            RequestBody body = RequestBody.create(MediaType.parse("image/png"),file);

            partMap.put("photos\"; filename=\"icon.png", body);
            partMap.put("username",  RequestBody.create(null, "abc"));
        }
        Observable<Result<Viewpoint>> call = HttpUtil.getApiService().updateViewpoint1(partMap, name);
        HttpUtil.toSubscribe(call, new ProgressSubscriber<Result<Viewpoint>>(nextListenter, context));
    }
}
