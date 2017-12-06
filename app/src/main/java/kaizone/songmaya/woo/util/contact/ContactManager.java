package kaizone.songmaya.woo.util.contact;

import android.support.v4.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kaizo on 2017/12/6.
 */

public class ContactManager {

    private Fragment fragment;
    private ContactAccessor contactAccessor;


    public ContactManager(Fragment fragment) {
        this.fragment = fragment;
        this.contactAccessor = new ContactAccessorSdk5(fragment);
    }

    public JSONArray search() {
        JSONArray filter = new JSONArray();
        filter.put("*");
        JSONObject options = new JSONObject();
        JSONArray desiredFields = new JSONArray();
        desiredFields.put("displayName");
        desiredFields.put("phoneNumbers");
        try {
            options.put("desiredFields", desiredFields);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contactAccessor.search(filter, options);
    }

}
