package kaizone.songmaya.jsyl.retrofitutil.bean;

/**
 * Created by yuekaizone on 2017/6/7.
 */

public class Notice {
    public int id;
    public int state;
    public String title;
    public String text;
    public String imageUrl;
    public String url;

    @Override
    public String toString() {
        return String.format("id=%s,state=%s,title=%s,text=%s,imageUrl=%s,url=%s",
                id,state,title,text,imageUrl,url);
    }
}
