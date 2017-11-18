package kaizone.songmaya.woo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;

import com.tendcloud.tenddata.TCAgent;

import kaizone.songmaya.woo.util.FrescoUtils;
import kaizone.songmaya.woo.util.PermissionConfig;
import kaizone.songmaya.woo.util.SystemUtils;
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
    private void checkHashPermission() {
        ActivityCompat.requestPermissions(this, PermissionConfig.need, PermissionConfig.requestCode);
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
        if (requestCode == PermissionConfig.requestCode) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    sb.append(permissions[i]).append("\n");
                }
            }
            if(!TextUtils.isEmpty(sb.toString())){
                Tips.toDialog(this, sb.toString());
            }
        }
    }
}
