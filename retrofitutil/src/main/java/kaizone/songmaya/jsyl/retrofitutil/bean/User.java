package kaizone.songmaya.jsyl.retrofitutil.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;

/**
 * Created by yuekaizone on 2017/6/7.
 */

public class User implements Serializable{
    public Integer id;
    public String loginName;
    public String name;
    public String password;
    public String plainPassword;
    public String salt;
    public long birthday;
    public Short gender;
    public String email;
    public String phone;
    public String icon;
    public long createDate;
    public String state;
    public String description;
    public Integer loginCount;
    public long previousVisit;
    public long lastVisit;
    public String delFlag;


    public static String encodeUser(User obj) {
        if (obj == null)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(obj);
        } catch (IOException e) {
            return null;
        }
        return byteArrayToHexString(os.toByteArray());
    }

    public static User decodeUser(String string) {
        byte[] bytes = hexStringToByteArray(string);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        User obj = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            obj = (User) objectInputStream.readObject();
        } catch (Exception e) {
            return null;
        }
        return obj;
    }

    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    protected static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    protected static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}
