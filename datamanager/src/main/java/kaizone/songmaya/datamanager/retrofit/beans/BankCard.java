package kaizone.songmaya.datamanager.retrofit.beans;

import java.util.List;

/**
 * Created by yuelibiao on 2017/11/23.
 */

public class BankCard extends Entity {

    public List<Info> info;

    public static class Info extends Entity{
        public String bankCode;
        public String isDefaultCard;//是否是默认卡
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
        public String isRealnameCard;//是否客户实名认证时的绑定卡
        public String accBchName;
    }
}
