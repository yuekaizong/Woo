package kaizone.songmaya.baidulbs.entity;

import com.baidu.location.BDLocation;

import java.io.Serializable;

/**
 * Created by Kaizo on 2017/12/6.
 */

public class LocationInfo implements Serializable {
    private BDLocation bdLocation;


    public BDLocation getBdLocation() {
        return bdLocation;
    }

    public void setBdLocation(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
    }

    public String getAddressStr() {
        return bdLocation.getAddrStr();
    }

    public double getLatitude() {
        return bdLocation.getLatitude();
    }

    public double getLongitude() {
        return bdLocation.getLongitude();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Latitude").append("=").append(getLatitude()).append("\n");
        sb.append("Longitude").append("=").append(getLongitude()).append("\n");
        sb.append("AddrStr").append("=").append(getAddressStr()).append("\n");
        return sb.toString();
    }
}
