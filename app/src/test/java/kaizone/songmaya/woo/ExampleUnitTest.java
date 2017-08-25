package kaizone.songmaya.woo;

import com.haiercash.gouhua.retrofit.beans.Result;
import com.haiercash.gouhua.retrofit.service.APIFactory;
import com.haiercash.gouhua.retrofit.util.SubscriberOnNextListenter;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import kaizone.songmaya.woo.util.AESUtil;
import kaizone.songmaya.woo.util.DES;
import kaizone.songmaya.woo.util.SecretTool;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public String api1="https://suggest.taobao.com/sug?code=utf-8&q='女装'callback=cb";
    public String api2="http://gc.ditu.aliyun.com/regeocoding?l=39.938133,116.395739&type=001";
    public String api3="http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13167066861";
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    static String HOST = "14.215.177.37";
    static int POST = 80;

    @Test
    public void httpTest() throws Exception {
        Map map = new HashMap<>();
        map.put("key","app_Personal");

//        URL url = new URL("http://10.164.194.121:9000/app/appserver/appmanage/param/selectByParams");
        URL url = new URL("https://shop.haiercash.com/app/appserver/appmanage/param/selectByParams");
//        URL url = new URL("http://localhost:8080/jsyl/user/all");
//        URL url = new URL("http://10.164.194.121:9000");
//        URL url = new URL("http://14.215.177.37:80");
//        URL url = new URL("https://www.baidu.com/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");// 提交模式
        // conn.setConnectTimeout(10000);//连接超时 单位毫秒
        // conn.setReadTimeout(2000);//读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数
        conn.setRequestProperty("Connection","close");
        conn.setRequestProperty("APPVersion","IOS-P-1.0.0");
        conn.setRequestProperty("DeviceModel","IOS-P-BLN-AL10");
        conn.setRequestProperty("DeviceResolution", "IOS-P-1080,1812");
        conn.setRequestProperty("SysVersion", "IOS-P-7.0");
        conn.setRequestProperty("channel","18");
        conn.setRequestProperty("channel_no","42");

        conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");

        StringBuffer params = new StringBuffer();
        params.append("?");
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        InputStream is = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        is.close();
        System.out.println(sb.toString());

    }


    public String header(String host, int post, String path) {
        StringBuffer temp = new StringBuffer();
        temp.append(String.format("GET %s HTTP/1.1\r\n", path));
        temp.append(String.format("Host: %s:%s\r\n", host, post));
        temp.append("Connection: keep-alive\r\n");
        temp.append("Cache-Control: max-age=0\r\n");
        temp.append("User-Agent: Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.47 Safari/536.11\r\n");
        temp.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
        temp.append("Accept-Encoding: gzip,deflate,sdch\r\n");
        temp.append("Accept-Language: zh-CN,zh;q=0.8\r\n");
        temp.append("Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n");
        temp.append("\r\n");
        return temp.toString();
    }

    @Test
    public void nioTest() throws Exception {
        SocketChannel channel = null;
        Selector selector = null;
        channel = SocketChannel.open();
        channel.configureBlocking(true);
        channel.connect(new InetSocketAddress(HOST, POST));
        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_CONNECT);
        selector.select();
        Iterator ite = selector.selectedKeys().iterator();
        while (ite.hasNext()) {
            SelectionKey key = (SelectionKey) ite.next();
            ite.remove();
            if (key.isConnectable()) {
                if (channel.isConnectionPending()) {
                    if (channel.finishConnect()) {
                        //只有当连接成功后才能注册OP_READ事件
                        key.interestOps(SelectionKey.OP_READ);
                        channel.write(getByteBuffer("?sysTyp=app_Personal"));
                    }
                }
            } else if (key.isReadable()) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(10*1024);
                channel.read(byteBuffer);
                //回绕缓冲区
                byteBuffer.flip();
                CharBuffer charBuffer = getCharBuffer(byteBuffer);
                String answer = charBuffer.toString();
                System.out.println("answer:" + answer);

                channel.write(getByteBuffer("kaka=haha"));
            }
        }
        channel.close();
        selector.close();
    }

    @Test
    public void secretToolTest() throws Exception{
        String source = "This is for DES test";
        String password = "1234567890qwerty";
        String temp = AESUtil.simpleEncrypt(source, password);
        System.out.println(temp + ":" + temp.length());
        String result2 = AESUtil.simpleDecrypt(temp, password);
        System.out.println(result2 + ":" + result2.length());
        System.out.println(source.equals(result2));

    }

    @Test
    public void desTest() {
        // 指定密匙
        String key = "*()&^%$#";
        // 指定需要加密的明文
        String text = "4454069u =o 5h6u= bopregkljoj";
        try {
            // 调用DES加密方法
            String encryString = DES.encryptDES(text, key);
            System.out.println("DES加密结果： " + encryString);
            // 调用DES解密方法
            String decryString = DES.decryptDES(encryString, key);
            System.out.println("DES解密结果： " + decryString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ByteBuffer getByteBuffer(String body) {
        String host = HOST;
        int post = POST;
        String header = header(host, post, "app/appserver/appmanage/param/selectByParams");
        String content = header + body;
        return ByteBuffer.wrap(content.getBytes());
    }

    public CharBuffer getCharBuffer(ByteBuffer buffer) {
        return buffer.asCharBuffer();
    }
}