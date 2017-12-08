package kaizone.songmaya.cordova;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.livedetect.LiveDetectActivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;
import org.apache.cordova.contacts.ContactAccessor;
import org.apache.cordova.contacts.ContactAccessorSdk5;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Target;

import kaizone.songmaya.baidulbs.LbsManager;
import kaizone.songmaya.baidulbs.entity.LocationInfo;

/**
 * Created by Kaizo on 2017/12/5.
 */

public class TestPlugin extends CordovaPlugin {

    private static final String TAG = "TestPlugin";

    private CallbackContext callbackContext;
    private JSONArray executeArgs;

    private LbsManager lbsManager;
    private ContactAccessor contactAccessor;

    public static final int UNKNOWN_ERROR = 0;
    public static final int PERMISSION_DENIED_ERROR = 20;
    public static final int ACCESS_FINE_LOCATION_REQ_CODE = 1000;
    public static final int READ_CONTACTS_REQ_CODE = 1001;
    public static final int LIVEDETECT_REQ_CODE = 1003;
    public static final int LIVEDETECT_RESULT_CODE = 2003;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.e(TAG, "execute方法被执行");
        testPluginFunction(action, args, callbackContext);
        this.callbackContext = callbackContext;
        this.executeArgs = args;

        if ("lbs".equals(action)) {
            if (PermissionHelper.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                lbs(executeArgs);
            } else {
                PermissionHelper.requestPermission(this, ACCESS_FINE_LOCATION_REQ_CODE, Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }//
        else if ("contacts".equals(action)) {
            if (PermissionHelper.hasPermission(this, Manifest.permission.READ_CONTACTS)) {
                contacts(executeArgs);
            } else {
                PermissionHelper.requestPermission(this, READ_CONTACTS_REQ_CODE, Manifest.permission.READ_CONTACTS);
            }
        }//
        else if ("liveDetect".equals(action)) {
            if (PermissionHelper.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && PermissionHelper.hasPermission(this, Manifest.permission.CAMERA)) {
                liveDetect(executeArgs);
            } else {
                PermissionHelper.requestPermissions(this, LIVEDETECT_REQ_CODE,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
            }
        }
        return true;
    }

    public void testPluginFunction(String action, JSONArray args, CallbackContext callbackContext) {
        Log.e(TAG, "这是一个Cordova测试");
        Log.e(TAG, String.format("%s, %s, %s", action, args.toString(), callbackContext.toString()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        String message = String.format("requestCode=%s, resultCoe=%s, intent=%s", requestCode, resultCode, intent);
    }

    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
        for (int r : grantResults) {
            if (r == PackageManager.PERMISSION_DENIED) {
                this.callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, PERMISSION_DENIED_ERROR));
                return;
            }
        }

        switch (requestCode) {
            case ACCESS_FINE_LOCATION_REQ_CODE:
                lbs(executeArgs);
                break;
            case READ_CONTACTS_REQ_CODE:
                contacts(executeArgs);
                break;
            case LIVEDETECT_REQ_CODE:
                liveDetect(executeArgs);
                break;
        }
    }

    public void lbs(JSONArray args) throws JSONException {
        if (lbsManager == null) {
            lbsManager = new LbsManager(this.cordova.getActivity());
        }
        lbsManager.startLocation(new LbsManager.OnLocationListener() {
            @Override
            public void onErr(String retFlag, String reason) {
                TestPlugin.this.callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, UNKNOWN_ERROR));
            }

            @Override
            public void onSuccess(final Object response) {
                TestPlugin.this.cordova.getThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        LocationInfo info = (LocationInfo) response;
                        final JSONArray body = new JSONArray();
                        final JSONObject item = new JSONObject();
                        try {
                            item.put("address", info.getAddressStr());
                            item.put("latitude", info.getLatitude());
                            item.put("longitude", info.getLongitude());
                            body.put(item);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        TestPlugin.this.callbackContext.success(body);
                    }
                });
            }
        });
    }

    private void contacts(JSONArray args) throws JSONException {
        if (contactAccessor == null) {
            contactAccessor = new ContactAccessorSdk5(this.cordova);
        }

        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                JSONArray filter = new JSONArray();
                filter.put("*");
                JSONObject options = new JSONObject();
                JSONArray desiredFields = new JSONArray();
                desiredFields.put("displayName");
                desiredFields.put("phoneNumbers");
                try {
                    options.put("desiredFields", desiredFields);
                    options.put("multiple", true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final JSONArray data = contactAccessor.search(filter, options);
                TestPlugin.this.callbackContext.success(data);
            }
        });
    }

    private void liveDetect(JSONArray args) throws JSONException {
        Intent intent = new Intent(cordova.getActivity(), LiveDetectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRandomable", true);
        bundle.putString("actions", "01279");
        bundle.putString("selectActionsNum", "3");
        bundle.putString("singleActionDectTime", "8");
        bundle.putBoolean("isWaterable", false);
        bundle.putBoolean("openSound", true);
        intent.putExtra("comprehensive_set", bundle);
        cordova.getActivity().startActivityForResult(intent, LIVEDETECT_RESULT_CODE);
    }
}
