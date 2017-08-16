package kaizone.songmaya.jsyl.retrofitutil.user;

import java.util.List;
import java.util.Map;

import kaizone.songmaya.jsyl.retrofitutil.bean.NewsDetailBean;
import kaizone.songmaya.jsyl.retrofitutil.bean.NewsListBean;
import kaizone.songmaya.jsyl.retrofitutil.bean.Notice;
import kaizone.songmaya.jsyl.retrofitutil.bean.Result;
import kaizone.songmaya.jsyl.retrofitutil.bean.User;
import kaizone.songmaya.jsyl.retrofitutil.bean.VersionBean;
import kaizone.songmaya.jsyl.retrofitutil.bean.Viewpoint;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiService {
    @GET("version/android/2.3.0")
    Call<ResponseBody>  getVersion();

    @GET("version/android/2.3.0")
    Call<VersionBean>  getVersionBean();

    @GET("version/android/2.3.0")
    Observable<VersionBean> getVersionRxjava();

    @GET("news/latest")
    Observable<NewsListBean> getNewsList();

    @GET("news/{id}")
    Observable<NewsDetailBean> getNewsDetail(@Path("id") String id);

    @GET("luckmoney/notice")
    Observable<Result<Notice>> getNotice();

    @FormUrlEncoded
    @POST("system/user/docreate")
    Observable<Result> doCreate(@Body User user, @Field("platform") String platform);

    @FormUrlEncoded
    @POST("system/user/docreate")
    Observable<Result> doCreate(@Field("name") String name,
                                @Field("loginName") String loginName,
                                @Field("plainPassword") String plainPassword,
                                @Field("email") String email,
                                @Field("description") String description,
                                @Field("platform") String platform
                                );

    @GET("lvu/viewpoint/all")
    Observable<Result<List<Viewpoint>>> getViewpointAll();

    @GET("lvu/viewpoint/{id}")
    Observable<Result<Viewpoint>> getViewpointId(@Path("id") Integer id);

    @Multipart
    @POST("lvu/viewpoint/update")
    Observable<Result<Viewpoint>> updateViewpoint(@Part MultipartBody.Part file, @Part("name") String name);

    @Multipart
    @POST("lvu/viewpoint/update")
    Observable<Result<Viewpoint>> updateViewpoint(@Part List<MultipartBody.Part> list, @Part("name") String name);

    @Deprecated
    @Multipart
    @POST("lvu/viewpoint/update")
    Observable<Result<Viewpoint>> updateViewpoint(@PartMap Map<String, MultipartBody.Part> map, @Part("name") String name);

    @Deprecated
    @Multipart
    @POST("lvu/viewpoint/update")
    Observable<Result<Viewpoint>> updateViewpoint1(@PartMap Map<String, RequestBody> map, @Part("name") String name);

}
