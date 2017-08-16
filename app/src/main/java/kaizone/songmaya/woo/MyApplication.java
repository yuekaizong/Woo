package kaizone.songmaya.woo;

import android.app.Application;
import android.util.Log;

import com.tendcloud.tenddata.TCAgent;

/**
 * Created by Administrator on 2017/7/5.
 */

public class MyApplication extends Application{

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        registerTakingdata();
    }

    public void registerTakingdata(){
//        TCAgent.init(this.getApplicationContext(), "DE40FB8A722D454B8981E2F842E6AAB6", "TalkingData");
        TCAgent.init(this.getApplicationContext());
        TCAgent.setReportUncaughtExceptions(true);
//        TalkingDataSMS.init(this.getApplicationContext(),"DE40FB8A722D454B8981E2F842E6AAB6","");
        Log.e(TAG, "registerTakingdata: 初始化TaklingData");
    }

}
