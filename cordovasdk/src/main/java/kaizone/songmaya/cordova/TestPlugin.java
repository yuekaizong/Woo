package kaizone.songmaya.cordova;

import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Target;

/**
 * Created by Kaizo on 2017/12/5.
 */

public class TestPlugin extends CordovaPlugin{

    private static final String TAG = "TestPlugin";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.e(TAG, "execute方法被执行");
        testPluginFunction(action, args, callbackContext);
        return super.execute(action, args, callbackContext);
    }

    public void testPluginFunction(String action, JSONArray args, CallbackContext callbackContext){
        Log.e(TAG, "这是一个Cordova测试");
        Log.e(TAG, String.format("%s, %s, %s", action, args.toString(), callbackContext.toString()));
    }
}
