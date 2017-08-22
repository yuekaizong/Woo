package kaizone.songmaya.woo.fragment.a;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.idcard.CardInfo;
import com.idcard.TFieldID;
import com.idcard.TengineID;
import com.livedetect.LiveDetectActivity;
import com.ui.card.TRCardScan;

import kaizone.songmaya.woo.MyApplication;
import kaizone.songmaya.woo.R;
import kaizone.songmaya.woo.util.SystemUtils;
import kaizone.songmaya.woo.util.Tips;
import kaizone.songmaya.woo.widget.DelEditText;

/**
 * Created by Administrator on 2017/8/9.
 */

public class GouHuaApiDetailTest extends Fragment {

    public static final int ID = GouHuaApiDetailTest.class.hashCode();
    public static final String NAME = GouHuaApiDetailTest.class.getSimpleName();
    public static final String TAG = "GouHuaApiTest";

    EditText bankEditText;
    EditText autoDelEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gouhua_apidetail, null);
        bankEditText = (EditText) view.findViewById(R.id.et);

        autoDelEditText = (DelEditText) view.findViewById(R.id.del_et);

        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bank = bankEditText.getText().toString().trim();
                Editable editable = bankEditText.getText();
                String str = editable.toString().replaceAll(" ", "");
                Editable editable2 = new SpannableStringBuilder(str);

                Tips.toDialog(getContext(), editable2.toString());
            }
        });

        view.findViewById(R.id.idcard1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doScan(1);
            }
        });
        view.findViewById(R.id.idcard2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doScan(2);
            }
        });
        view.findViewById(R.id.liveDetect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLiveDetect();
            }
        });

        return view;
    }

    void doScan(int requestCode) {
        if (SystemUtils.checkHasPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && SystemUtils.checkHasPermission(getActivity(), Manifest.permission.CAMERA)) {
            startScan(requestCode);
        } else {
            Tips.toDialog(getActivity(), "开启存储和摄像头权限", "确定", new Runnable() {
                @Override
                public void run() {
                    openPermission();
                }
            });
        }
    }

    void doLiveDetect() {
        if (SystemUtils.checkHasPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && SystemUtils.checkHasPermission(getActivity(), Manifest.permission.CAMERA)) {
            Intent intent = new Intent(getActivity(), LiveDetectActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isRandomable", true);
            bundle.putString("actions", "01279");
            bundle.putString("selectActionsNum", "3");
            bundle.putString("singleActionDectTime", "8");
            bundle.putBoolean("isWaterable", false);
            bundle.putBoolean("openSound", true);
            intent.putExtra("comprehensive_set", bundle);
            startActivityForResult(intent, 3);
        } else {
            Tips.toDialog(getActivity(), "开启存储和摄像头权限", "确定", new Runnable() {
                @Override
                public void run() {
                    openPermission();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("返回", String.format("requestCode=%s,resultCode=%s,data=%s", requestCode, resultCode,
                data != null ? data.toString() : "null"));
        if (resultCode == TRCardScan.RESULT_GET_CARD_OK) {
            //身份证扫描返回
            if (requestCode == 1 || requestCode == 2) {
                final CardInfo cardInfo = (CardInfo) data.getSerializableExtra("cardinfo");
                if (cardInfo != null) {
                    Log.e(TAG, "onActivityResult: " + cardInfo.getAllinfo());
                    Tips.toToast(getActivity(), cardInfo.getAllinfo());
                    String name = cardInfo.getFieldString(TFieldID.NAME);
                } else {
                    Log.e(TAG, "onActivityResult: cardInfo == null");
                }
            }
        }

    }

    private void startScan(int requestCode) {
//        CaptureActivity.tengineID = TengineID.TIDCARD2;
//        CaptureActivity.isPortrait = true;
//        CaptureActivity.ShowCopyRightTxt = "";
//        Intent intent = new Intent(this, CaptureActivity.class);
//        intent.putExtra("engine", engin);
//        startActivityForResult(intent, requestCode);

        TRCardScan.SetEngineType(TengineID.TIDCARD2);
        TRCardScan.isPortrait = true;
        Intent intent = new Intent(getActivity(), TRCardScan.class);
        intent.putExtra("engine", MyApplication.engin);
        startActivityForResult(intent, requestCode);
    }


    void openPermission() {
        Intent in = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        in.setData(uri);
        startActivity(in);
    }

    public static GouHuaApiDetailTest newInstance(Bundle bd) {
        final GouHuaApiDetailTest f = new GouHuaApiDetailTest();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }
}
