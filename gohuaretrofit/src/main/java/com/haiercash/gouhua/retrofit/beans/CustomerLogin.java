package com.haiercash.gouhua.retrofit.beans;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuelb on 2017/7/18.
 */

public class CustomerLogin extends Entity{
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


    public static class Token extends Entity{
        public String access_token;
        public String token_type;
        public String refresh_token;
        public String expires_in;
        public String scope;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    public static class RealInfo implements Serializable{
        public String custNo;
        public String certType;
        public String faceVal;
        public String mobile;
        public String dataFrom;
        public String providerNo;
        public String acctBankName;
        public String acctProvince;
        public String custName;
        public String acctName;
        public String acctBankNo;
        public String acctArea;
        public String cardNo;
        public String createDt;
        public String faceCount;
        public String accBchCde;
        public String certNo;
        public String acctCity;
        public String faceValue;
        public String accBchName;
        public String providerDesc;
        public String belongStore;
        public String passStandard;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }
}
