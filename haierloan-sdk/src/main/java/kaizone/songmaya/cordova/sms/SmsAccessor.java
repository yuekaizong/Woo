package kaizone.songmaya.cordova.sms;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kaizo on 2017/12/12.
 */

public class SmsAccessor {

    private static final String FLAG_ADDRESS = "address";
    private static final String FLAG_PERSON = "person";
    private static final String FLAG_BODY = "body";
    private static final String FLAG_DATE = "date";
    private static final String FLAG_TYPE = "type";

    private static final String TAG = "SmsAccessor";

    private Activity activity;

    public SmsAccessor(Activity activity) {
        this.activity = activity;
    }

    public JSONArray getSmsInPhone() throws JSONException {
        final String SMS_URI_ALL = "content://sms/"; // 所有短信
        final String SMS_URI_INBOX = "content://sms/inbox"; // 收件箱
        final String SMS_URI_SEND = "content://sms/sent"; // 已发送
        final String SMS_URI_DRAFT = "content://sms/draft"; // 草稿
        final String SMS_URI_OUTBOX = "content://sms/outbox"; // 发件箱
        final String SMS_URI_FAILED = "content://sms/failed"; // 发送失败
        final String SMS_URI_QUEUED = "content://sms/queued"; // 待发送列表

        JSONArray array = new JSONArray();

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss");

        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type",};
            Cursor cur = activity.getContentResolver().query(uri, projection, null,
                    null, "date desc"); // 获取手机内部短信
            // 获取短信中最新的未读短信
            // Cursor cur = getContentResolver().query(uri, projection,
            // "read = ?", new String[]{"0"}, "date desc");
            if (cur.moveToFirst()) {
                int index_Address = cur.getColumnIndex(FLAG_ADDRESS);
                int index_Person = cur.getColumnIndex(FLAG_PERSON);
                int index_Body = cur.getColumnIndex(FLAG_BODY);
                int index_Date = cur.getColumnIndex(FLAG_DATE);
                int index_Type = cur.getColumnIndex(FLAG_TYPE);

                do {
                    String address = cur.getString(index_Address);
                    int person = cur.getInt(index_Person);
                    String body = cur.getString(index_Body);
                    long date = cur.getLong(index_Date);
                    int type = cur.getInt(index_Type);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(FLAG_ADDRESS, address);
                    jsonObject.put(FLAG_PERSON, person);
                    jsonObject.put(FLAG_BODY, body);
                    jsonObject.put(FLAG_DATE, date);

                    Date d = new Date(date);
                    String strDate = dateFormat.format(d);

                    String strType = "";
                    if (type == 1) {
                        strType = "接收";
                    } else if (type == 2) {
                        strType = "发送";
                    } else if (type == 3) {
                        strType = "草稿";
                    } else if (type == 4) {
                        strType = "发件箱";
                    } else if (type == 5) {
                        strType = "发送失败";
                    } else if (type == 6) {
                        strType = "待发送列表";
                    } else if (type == 0) {
                        strType = "所有短信";
                    } else {
                        strType = "null";
                    }

                    jsonObject.put("strDate", strDate);
                    jsonObject.put("strType", strType);

                    array.put(jsonObject);
                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            } else {

            }
        } catch (SQLiteException ex) {
            Log.e(TAG, ex.getMessage());
        }

        return array;
    }
}
