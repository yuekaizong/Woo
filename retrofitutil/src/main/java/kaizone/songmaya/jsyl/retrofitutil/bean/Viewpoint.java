package kaizone.songmaya.jsyl.retrofitutil.bean;

/**
 * Created by yuekaizone on 2017/6/8.
 */

public class Viewpoint {
    public Integer id;

    public String description;
    public String name;
    public String img1;
    public String img2;
    public String img3;
    public String img4;
    public String img5;
    public String img6;

    @Override
    public String toString() {
        return String.format("id=%s,name=%s,img1=%s,img2=%s,img3=%s",
                id, name, img1, img2, img3);
    }
}
