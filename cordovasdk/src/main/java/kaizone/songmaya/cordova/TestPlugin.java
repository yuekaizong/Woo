package kaizone.songmaya.cordova;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

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
        }
        return true;
    }

    public void testPluginFunction(String action, JSONArray args, CallbackContext callbackContext) {
        Log.e(TAG, "这是一个Cordova测试");
        Log.e(TAG, String.format("%s, %s, %s", action, args.toString(), callbackContext.toString()));
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
            public void onSuccess(Object response) {
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
                TestPlugin.this.cordova.getThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
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
        JSONArray filter = new JSONArray();
        filter.put("*");
        JSONObject options = new JSONObject();
        JSONArray desiredFields = new JSONArray();
        desiredFields.put("displayName");
        desiredFields.put("phoneNumbers");
        options.put("desiredFields", desiredFields);
        options.put("multiple", true);

        final JSONArray data = contactAccessor.search(filter, options);
        this.cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                TestPlugin.this.callbackContext.success(data);
            }
        });

    }
}
