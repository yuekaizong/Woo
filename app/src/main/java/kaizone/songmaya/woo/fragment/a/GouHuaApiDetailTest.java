package kaizone.songmaya.woo.fragment.a;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import kaizone.songmaya.woo.MyApplication;
import kaizone.songmaya.woo.R;
import kaizone.songmaya.woo.util.DES;
import kaizone.songmaya.woo.util.SystemUtils;
import kaizone.songmaya.woo.util.Tips;
import kaizone.songmaya.woo.widget.CardInputEditText;
import kaizone.songmaya.woo.widget.DelEditText;
import kaizone.songmaya.woo.widget.TimeTextView;

/**
 * Created by Administrator on 2017/8/9.
 */

public class GouHuaApiDetailTest extends Fragment {

    public static final int ID = GouHuaApiDetailTest.class.hashCode();
    public static final String NAME = GouHuaApiDetailTest.class.getSimpleName();
    public static final String TAG = "GouHuaApiTest";
    private static final int REQUEST_CODE_TAKEPHOTO = 200;

    EditText bankEditText;
    CardInputEditText cardinputEditText;
    EditText autoDelEditText;
    EditText autoDelEditText2;
    TimeTextView timeTextView;

    TextInputLayout textInputLayout;
    TextInputEditText textInputEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gouhua_apidetail, null);
        textInputLayout = (TextInputLayout) view.findViewById(R.id.textInputWrapper);
        textInputEditText = (TextInputEditText) view.findViewById(R.id.input1);
        textInputEditText.addTextChangedListener(new TextChangedWatcher());

        bankEditText = (EditText) view.findViewById(R.id.et);

        autoDelEditText = (DelEditText) view.findViewById(R.id.del_et);
        autoDelEditText2 = (DelEditText) view.findViewById(R.id.del_et2);

        cardinputEditText = (CardInputEditText) view.findViewById(R.id.cardinputEditText);

        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryContent();
                autoDelEditText.setText("1234567");
                autoDelEditText2.setText("qwert");
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
        ((Button) view.findViewById(R.id.pushData)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushData();
            }
        });

        timeTextView = (TimeTextView) view.findViewById(R.id.timeTextView);
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });

        view.findViewById(R.id.takePhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        view.findViewById(R.id.layoutChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Rect rect = new Rect();
//                getView().getWindowVisibleDisplayFrame(rect);
//                int rootInvisiableH = getView().getRootView().getHeight() - rect.bottom;
//                if (rootInvisiableH <= 100) {
//                    reset();
//                } else {
//                    move();
//                }
//            }
//        });
        return view;
    }

    public void getCode() {
        timeTextView.setLastTime(new TimeTextView.SendMsgListener() {
            @Override
            public void countStart() {
                //倒计时之前调用
                timeTextView.setClickable(false);
            }

            @Override
            public void countFinished() {
                //倒计时完后秒后调用
                timeTextView.setClickable(true);
            }
        });
    }

    void queryContent() {
        StringBuilder sb = new StringBuilder();

        String str1 = bankEditText.getText().toString().trim();
        sb.append("bank:").append(str1).append("\n");
        String str2 = cardinputEditText.getTextWithoutSpace();
        sb.append("carinput:").append(str2).append("\n");

        Tips.toDialog(getContext(), sb.toString());
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
//            Intent intent = new Intent(getActivity(), LiveDetectActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("isRandomable", true);
//            bundle.putString("actions", "01279");
//            bundle.putString("selectActionsNum", "3");
//            bundle.putString("singleActionDectTime", "8");
//            bundle.putBoolean("isWaterable", false);
//            bundle.putBoolean("openSound", true);
//            intent.putExtra("comprehensive_set", bundle);
//            startActivityForResult(intent, 3);
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
        if (requestCode == REQUEST_CODE_TAKEPHOTO) {
            File photoFile = new File(getActivity().getExternalCacheDir() + "/www", "photo.jpg");
            ImageView draweeView = new ImageView(getActivity());
            draweeView.setImageURI(Uri.fromFile(photoFile));
            Tips.show(draweeView);
        }

    }

    private void startScan(int requestCode) {
    }


    void openPermission() {
        Intent in = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        in.setData(uri);
        startActivity(in);
    }

    void pushData() {
        String key = DES.ENCRYPT_KEY;
        // 指定需要加密的明文
        String text = "这是一段加密后的文字为什么要加空格就加密错误这个是什么原因呢，经过多次调试终于将Android Base64代码放在服务器上，解决这个问题";
        try {
            // 调用DES加密方法
            String encryString = DES.encryptDES(text, key);
            System.out.println("DES加密结果： " + encryString);
            // 调用DES解密方法
            String decryString = DES.decryptDES(encryString, key);
            System.out.println("DES解密结果： " + decryString);

            Map map = new HashMap();
//            map.put("id", "1");
            map.put("jm", encryString);
            TestData.post("http://kaizone.xyz:18080/jsyl/pc/u", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void takePhoto() {
        File photoDir = new File(getActivity().getExternalCacheDir() + "/www");
        if (!photoDir.exists()) {
            photoDir.mkdir();
        }

        String photoPath = photoDir + "/photo.jpg";

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri photoURI = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider",
                    new File(photoPath));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        } else {
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            Uri uri = Uri.parse("file://" + photoPath);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        startActivityForResult(intent, REQUEST_CODE_TAKEPHOTO);
    }

    void move() {
        if (getView() != null) {
            Log.e(TAG, String.format("view y=%s,", getView().getY()));
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(getView(), "y", 0, -100);
            objectAnimator.setDuration(500);
            objectAnimator.start();
        }
    }

    void reset() {
        if (getView() != null) {
            Log.e(TAG, String.format("view y=%s,", getView().getY()));
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(getView(), "y", getView().getY(), 0);
            objectAnimator.setDuration(500);
            objectAnimator.start();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getView() == null) return;
        getView().getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                Rect rect = new Rect();
                getView().getWindowVisibleDisplayFrame(rect);
                int rootInvisiableH = getView().getRootView().getHeight() - rect.bottom;
                if (rootInvisiableH <= 100) {
                    reset();
                } else {
                    move();
                }
            }
        });

    }

    public class TextChangedWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if ("error".equals(s.toString())) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError("出错啦！！！");
            } else if ("count".equals(s.toString())) {
                textInputLayout.setCounterEnabled(true);
                textInputLayout.setCounterMaxLength(100);
            } else if ("appearance".equals(s.toString())) {
                textInputLayout.setHintTextAppearance(R.style.FloatingStyle);
            } else if ("password".equals(s.toString())) {
                textInputLayout.setPasswordVisibilityToggleEnabled(true);
                textInputLayout.setPasswordVisibilityToggleDrawable(R.mipmap.ic_launcher);
                textInputLayout.setPasswordVisibilityToggleContentDescription("这是密码");
            } else if ("false".equals(s.toString())) {
                textInputLayout.setPasswordVisibilityToggleContentDescription("");
                textInputLayout.setErrorEnabled(false);
                textInputLayout.setCounterEnabled(false);
                textInputLayout.setPasswordVisibilityToggleEnabled(false);
            }
        }
    }

    public static GouHuaApiDetailTest newInstance(Bundle bd) {
        final GouHuaApiDetailTest f = new GouHuaApiDetailTest();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }
}
