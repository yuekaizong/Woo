package kaizone.songmaya.haiercash.retrofit.beans;

import java.util.List;

/**
 * Created by yuelb on 2017/7/18.
 */

public class CustomerLogin extends Entity {
    public String externUid;
    public List<String> deviceIdList;
    public String avatarUrl;
    public String nickName;
    public String mobile;
    public String userDesc;
    public String userName;
    public String userId;
    public String deviceId;
    public String alterPwd;
    public Token token;
    public Integer failedCount;
    public String provider;
    public String registDt;
    public String clientSecret;
    public RealInfo realInfo;
    public String state;
    public String isRealInfo;
    public String email;

}
