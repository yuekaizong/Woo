package kaizone.songmaya.woo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import kaizone.songmaya.woo.fragment.a.DownloadFragment;
import kaizone.songmaya.woo.fragment.a.GoFragment;
import kaizone.songmaya.woo.fragment.a.GoFragment2;
import kaizone.songmaya.woo.fragment.a.GoFragment3;
import kaizone.songmaya.woo.fragment.a.GoMyPublishFragment;
import kaizone.songmaya.woo.fragment.a.GoWebFragment;
import kaizone.songmaya.woo.fragment.a.GouHuaApiDetailTest;
import kaizone.songmaya.woo.fragment.a.GouHuaApiTest;

/**
 * Created by yuekaizone on 2017/6/7.
 */

public class FragmentController {
    public static Fragment obtain(int id, Bundle bd) {
        if(id == GoFragment.ID){
            return GoFragment.newInstance(bd);
        }
        else if(id == GoFragment2.ID){
            return GoFragment2.newInstance(bd);
        }
        else if(id == GoFragment3.ID){
            return GoFragment3.newInstance(bd);
        }
        else if(id == GoMyPublishFragment.ID){
            return GoMyPublishFragment.newInstance(bd);
        }
        else if(id == DownloadFragment.ID){
            return DownloadFragment.newInstance(bd);
        }
        else if(id == GoWebFragment.ID){
            return GoWebFragment.newInstance(bd);
        }
        else if(id == GouHuaApiTest.ID){
            return GouHuaApiTest.newInstance(bd);
        }
        else if(id == GouHuaApiDetailTest.ID){
            return GouHuaApiDetailTest.newInstance(bd);
        }
        return null;
    }
}
