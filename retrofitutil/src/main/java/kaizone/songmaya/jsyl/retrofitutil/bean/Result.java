package kaizone.songmaya.jsyl.retrofitutil.bean;

/**
 * Created by yuekaizone on 2017/6/7.
 */

public class Result<T> {
    public boolean success;
    public String message;
    public T data;

    @Override
    public String toString() {
        return String.format("success=%s, message=%s, data={%s}",
                success, message, data.toString());
    }
}
