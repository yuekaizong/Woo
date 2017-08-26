package kaizone.songmaya.woo.util;

import android.Manifest;

/**
 * Created by yuekaizone on 2017/8/26.
 */

public interface PermissionConfig {

    int requestCode = 123;

    String[] need = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.DISABLE_KEYGUARD,
            Manifest.permission.ACCESS_WIFI_STATE
    };
}
