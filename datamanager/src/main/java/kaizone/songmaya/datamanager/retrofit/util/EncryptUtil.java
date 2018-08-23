package kaizone.songmaya.datamanager.retrofit.util;

import android.util.Base64;


/**
 * @author liuhongbin
 * @date 2016/5/19
 * @description: 加解密工具类
 **/
public class EncryptUtil
{
    /**
     * MD5加密，和IOS保持一致
     *
     * @param md
     * @return
     */
    public static String MD5(byte[] md)
    {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};
        try
        {
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e)
        {
            return null;
        }
    }

//    // 加密
//    public static String AesEncrypt(String sSrc, String sKey) throws Exception {
//        if (sKey == null) {
//            System.out.print("Key为空null");
//            return null;
//        }
//        // 判断Key是否为16位
//        if (sKey.length() != 16) {
//            System.out.print("Key长度不是16位");
//            return null;
//        }
//        byte[] raw = sKey.getBytes("utf-8");
//        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
////        IvParameterSpec iv = new IvParameterSpec(sKey.getBytes());//CBC模式使用
//        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
//        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
//
////        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
//        return  Base64();
//    }
//
//    // 解密
//    public static String AesDecrypt(String sSrc, String sKey) throws Exception {
//        try {
//            // 判断Key是否正确
//            if (sKey == null) {
//                System.out.print("Key为空null");
//                return null;
//            }
//            // 判断Key是否为16位
//            if (sKey.length() != 16) {
//                System.out.print("Key长度不是16位");
//                return null;
//            }
//            byte[] raw = sKey.getBytes("utf-8");
//            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
////            IvParameterSpec iv = new IvParameterSpec(sKey.getBytes());//CBC模式使用
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
//            try {
//                byte[] original = cipher.doFinal(encrypted1);
//                String originalString = new String(original,"utf-8");
//                return originalString;
//            } catch (Exception e) {
//                System.out.println(e.toString());
//                return null;
//            }
//        } catch (Exception ex) {
//            System.out.println(ex.toString());
//            return null;
//        }
//    }

    /**
     * 加密
     *
     * @param sSrc
     * @return
     */
    public static String simpleEncrypt(String sSrc)
    {
        byte[] bas = sSrc.getBytes();
        //按位取反
        for (int i = 0; i < bas.length; i++)
        {
            bas[i] = (byte) ~bas[i];
        }
        int half = bas.length / 2;
        for (int i = 0; i < half; i++)
        {
            if (i % 2 == 1)
            {
                //奇数位调换
                byte b = bas[i];
                bas[i] = bas[i + half];
                bas[i + half] = b;
            }
        }
//        String  wrapStr= Base64.encodeToString(bas,  Base64.URL_SAFE);
        String wrapStr = Base64.encodeToString(bas, Base64.URL_SAFE);
        wrapStr = wrapStr.replaceAll("\\n", "");
//        wrapStr= wrapStr.replace("+","-");
//        wrapStr=wrapStr.replace("/","_");
        return wrapStr;

    }
/**
 * */
    public static String encrypt(String sSrc)
    {
        byte[] bas = sSrc.getBytes();

        String wrapStr = Base64.encodeToString(bas, Base64.DEFAULT);

        wrapStr= wrapStr.replace("+","%2B");

        return wrapStr;

    }



    /**
     * 加密
     *
     * @param sSrc
     * @return
     */
    public static String base64(String sSrc)
    {
        byte[] bas = sSrc.getBytes();

        return Base64.encodeToString(bas, Base64.DEFAULT);

    }


    // 加密
   /* public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }*/
    /**
     * 解密
     *
     * @param
     * @return
     */
    public static String Decrypt(String sSrc)
    {
        byte[] bas = Base64.decode(sSrc, Base64.URL_SAFE);
        return new String(bas);
    }

    /**
     * 解密
     *
     * @param sSrc
     * @return
     */
    public static String simpleDecrypt(String sSrc)
    {
        byte[] bas = Base64.decode(sSrc, Base64.URL_SAFE);
        int half = bas.length / 2;
        for (int i = 0; i < half; i++)
        {
            if (i % 2 == 1)
            {
                //奇数位调换
                byte b = bas[i];
                bas[i] = bas[i + half];
                bas[i + half] = b;
            }
        }
        //按位取反
        for (int i = 0; i < bas.length; i++)
        {
            bas[i] = (byte) ~bas[i];
        }
        return new String(bas);
    }

    public static void main(String[] args) throws Exception
    {
        System.out.println(simpleDecrypt("ttKsu83Kz7vNu8bK0s7JzszOy8fMx9LLzcjMsMzSysvJus7JycfMytLIzNLHycfHyb3JvA=="));
        System.out.println(simpleDecrypt("zsfOy8fHzMnHycg="));
        System.out.println(simpleDecrypt("nsvNzM7KyQ=="));


        String sSrc = "18653201加密2048机诶86532020480";
        System.out.println(sSrc);
        String sEnc = simpleEncrypt(sSrc);
        System.out.println("simpleEncrypt：" + sEnc);
        System.out.println("simpleDecrypt：" + simpleDecrypt(sEnc));

        //此处使用AES-128-ECB加密模式，key需要为16位。
        String cKey = "1234567890123456";
        // 需要加密的字串
        String cSrc = "18653202048";
        System.out.println(cSrc);
//        // 加密
//        String enString = AesEncrypt(cSrc, cKey);
//        System.out.println("加密后的字串是：" + enString);
//        // 解密
//        String DeString = AesDecrypt(enString, cKey);
//        System.out.println("解密后的字串是：" + DeString);
    }
}
