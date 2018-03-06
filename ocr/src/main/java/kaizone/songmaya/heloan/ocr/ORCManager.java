package kaizone.songmaya.heloan.ocr;

import android.app.Activity;
import android.content.Intent;

import com.idcard.TParam;
import com.idcard.TRECAPIImpl;
import com.idcard.TengineID;
import com.turui.ocr.scanner.WOcrActivity;
import com.turui.ocr.scanner.common.WztUtils;

/**
 * Created by Kaizo on 2017/12/13.
 */

public class ORCManager {

    public final static TRECAPIImpl engine = new TRECAPIImpl();//加载引擎，识别前加载，识别后释放(可以多次识别，不必每次都实例)

    private Activity activity;

    public ORCManager(Activity activity) {
        this.activity = activity;
    }

    public void openScanIDBank(){
        Intent intent = new Intent(activity, WOcrActivity.class);//默认界面
        TengineID tengineID = TengineID.TIDBANK;
        engine.TR_SetSupportEngine(tengineID);//设置识别类型
        engine.TR_SetParam(TParam.T_SET_RECMODE, 0);//银行卡扫描
        intent.putExtra(TRECAPIImpl.class.getSimpleName(), engine);//传入引擎
        intent.putExtra(TengineID.class.getSimpleName(), tengineID);//传入类型
        intent.putExtra(WztUtils.MODE, WztUtils.SCAN_MODE);//模式(扫描或拍照)
        activity.startActivityForResult(intent, 600);
    }

    public void openScanIDCard2(){
        Intent intent = new Intent(activity, WOcrActivity.class);//默认界面
        TengineID tengineID = TengineID.TIDCARD2;
        engine.TR_SetSupportEngine(tengineID);//设置识别类型
        engine.TR_SetParam(TParam.T_SET_RECMODE, 1);////身份证扫描及其它证件
        intent.putExtra(TRECAPIImpl.class.getSimpleName(), engine);//传入引擎
        intent.putExtra(TengineID.class.getSimpleName(), tengineID);//传入类型
        intent.putExtra(WztUtils.MODE, WztUtils.SCAN_MODE);//模式(扫描或拍照)
        activity.startActivityForResult(intent, 600);
    }

}
