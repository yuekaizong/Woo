package com.haiercash.gouhua.retrofit.beans;

import java.util.List;

/**
 * Created by Kaizo on 2017/11/23.
 */

public class BankCard extends Entity {

    public List<Info> info;

    public static class Info {
        public String bankCode;
        public String isDefaultCard;
        public String cardTypeName;
        public String mobile;
        public String bankName;
        public String singleCollLimited;
        public String acctProvince;
        public String acctArea;
        public String cardNo;
        public String accBchCde;
        public String singleTransLimited;
        public String acctCity;
        public String isRealnameCard;
        public String accBchName;
    }
}
