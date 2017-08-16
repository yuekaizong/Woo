package kaizone.songmaya.woo.fragment.a;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kaizone.songmaya.jsyl.retrofitutil.bean.Viewpoint;

/**
 * Created by yuekaizone on 2017/6/15.
 */

public class TestData {

    public static List<Viewpoint> viewpoints;

    public static List<Viewpoint> getViewpoints(){
        if(viewpoints == null){
            viewpoints = obtainViewpoints();
        }
        return viewpoints;
    }

    private static List<Viewpoint> obtainViewpoints(){

        Viewpoint viewpoint = new Viewpoint();
        viewpoint.img1 = "http://img.ivsky.com/img/tupian/pre/201704/28/haian_fengjing.jpg";
        viewpoint.img2 = "http://img.ivsky.com/img/tupian/pre/201704/28/haian_fengjing-001.jpg";
        viewpoint.img3 = "http://img.ivsky.com/img/tupian/pre/201704/28/haian_fengjing-002.jpg";
        viewpoint.img4 = "http://img.ivsky.com/img/tupian/pre/201704/28/haian_fengjing-003.jpg";
        viewpoint.img5 = "http://img.ivsky.com/img/tupian/pre/201704/28/haian_fengjing-004.jpg";
        viewpoint.img6 = "http://img.ivsky.com/img/tupian/pre/201704/28/haian_fengjing-005.jpg";
        viewpoint.name = "海岸风景";
        viewpoint.description = "这个海岸风景";

        Viewpoint viewpoint2 = new Viewpoint();
        viewpoint2.img1 = "http://img.ivsky.com/img/tupian/pre/201612/09/grand_canyon_national_park.jpg";
        viewpoint2.img2 = "http://img.ivsky.com/img/tupian/pre/201612/09/grand_canyon_national_park-001.jpg";
        viewpoint2.img3 = "http://img.ivsky.com/img/tupian/pre/201612/09/grand_canyon_national_park-002.jpg";
        viewpoint2.img4 = "http://img.ivsky.com/img/tupian/pre/201612/09/grand_canyon_national_park-004.jpg";
        viewpoint2.img5 = "http://img.ivsky.com/img/tupian/pre/201612/09/grand_canyon_national_park-005.jpg";
        viewpoint2.img6 = "http://img.ivsky.com/img/tupian/pre/201612/09/grand_canyon_national_park-006.jpg";
        viewpoint2.name = "海岸风景";
        viewpoint2.description = "这个海岸风景";

        Viewpoint viewpoint3 = new Viewpoint();
        viewpoint3.img1 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong.jpg";
        viewpoint3.img2 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong-001.jpg";
        viewpoint3.img3 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong-002.jpg";
        viewpoint3.img4 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong-003.jpg";
        viewpoint3.img5 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong-004.jpg";
        viewpoint3.img6 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong-005.jpg";
        viewpoint3.img6 = "http://img.ivsky.com/img/tupian/pre/201703/27/cuican_de_xingkong-006.jpg";
        viewpoint3.name = "璀璨星空";
        viewpoint3.description = "这个是催催星空";

        Viewpoint viewpoint4 = new Viewpoint();
        viewpoint4.img1 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye.jpg";
        viewpoint4.img2 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye-001.jpg";
        viewpoint4.img3 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye-002.jpg";
        viewpoint4.img4 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye-003.jpg";
        viewpoint4.img5 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye-004.jpg";
        viewpoint4.img6 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye-005.jpg";
        viewpoint4.img6 = "http://img.ivsky.com/img/tupian/pre/201704/30/weimei_de_fengye-006.jpg";
        viewpoint4.name = "枫叶";
        viewpoint4.description = "这个是枫叶美景";

        List<Viewpoint> data = new ArrayList<>();
        data.add(viewpoint);
        data.add(viewpoint2);
        data.add(viewpoint3);
        data.add(viewpoint4);
        return data;
    }

    public static Viewpoint getViewpoint(int i){
        return getViewpoints().get(0);
    }
}
