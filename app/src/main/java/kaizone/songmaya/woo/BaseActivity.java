package kaizone.songmaya.woo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;

import com.tendcloud.tenddata.TCAgent;

import kaizone.songmaya.woo.util.FrescoUtils;
import kaizone.songmaya.woo.util.Tips;

/**
 * Created by yuekaizone on 2017/6/11.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkHashPermission();
        FrescoUtils.config(this);
    }

    @TargetApi(23)
    private boolean checkHashPermission() {
        int permission = PermissionChecker.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.DISABLE_KEYGUARD,
                            Manifest.permission.ACCESS_WIFI_STATE
                    },
                    123);
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        TCAgent.onPageStart(this, this.getClass().getName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        TCAgent.onPageEnd(this, this.getClass().getName());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[0])
                    && Manifest.permission.READ_PHONE_STATE.equals(permissions[1])
                    && Manifest.permission.DISABLE_KEYGUARD.equals(permissions[2])
                    && Manifest.permission.ACCESS_WIFI_STATE.equals(permissions[3])
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Tips.toToast(this, "授权成功");
            } else {
                this.finish();
            }
        }
    }
}
