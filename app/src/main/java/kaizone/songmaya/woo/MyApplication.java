package kaizone.songmaya.woo;

import android.app.Application;
import android.util.Log;

import com.idcard.TRECAPIImpl;
import com.idcard.TStatus;
import com.tendcloud.tenddata.TCAgent;

/**
 * Created by Administrator on 2017/7/5.
 */

public class MyApplication extends Application{

    private static final String TAG = "MyApplication";
    public static final TRECAPIImpl engin = new TRECAPIImpl();

    @Override
    public void onCreate() {
        super.onCreate();
        registerTakingdata();
        initTrecapIImpl();
    }

    private void initTrecapIImpl(){
        TStatus status = engin.TR_StartUP(this, engin.TR_GetEngineTimeKey());
        if (status == TStatus.TR_TIME_OUT) {
            Log.e(TAG, "initTrecapIImpl: 扫描引擎已过期");
        } else if (status == TStatus.TR_FAIL) {
            Log.e(TAG, "initTrecapIImpl: 扫描引擎初始化失败，请退出重试");
        }
    }

    public void registerTakingdata(){
//        TCAgent.init(this.getApplicationContext(), "DE40FB8A722D454B8981E2F842E6AAB6", "TalkingData");
        TCAgent.init(this.getApplicationContext());
        TCAgent.setReportUncaughtExceptions(true);
//        TalkingDataSMS.init(this.getApplicationContext(),"DE40FB8A722D454B8981E2F842E6AAB6","");
        Log.e(TAG, "registerTakingdata: 初始化TaklingData");
    }

}
