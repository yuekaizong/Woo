package kaizone.songmaya.haiercash.retrofit.beans;

/**
 * Created by yuelb on 2017/7/15.
 */

public class Result<T> extends Entity{

    public Head head;
    public T body;

    //异常返回
    public int code;
    public String message;
//    public String retFlag;
//    public String retMsg;
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        if(!TextUtils.isEmpty(message)){
//            sb.append(String.format("code:%s,message:%s,retFlag:%s,retMsg:%s", code,message,retFlag,retMsg));
//        }
//        if(head != null){
//            sb.append("head:").append(head.toString()).append(",");
//        }
//        if(body != null){
//            sb.append("body:").append(body.toString());
//        }
//        return sb.toString();
//    }
}
