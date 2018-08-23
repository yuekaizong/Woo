package kaizone.songmaya.woo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by yuelibiao on 2017/12/15.
 */

public class BitmapUtils {

    public static byte[] toBytes(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public static String toBase64(Bitmap bitmap) {
        byte[] bytes = toBytes(bitmap);
        byte[] output = Base64.encode(bytes, Base64.NO_WRAP);
        return new String(output);
    }

    public static byte[] flipHorizontal(byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Matrix m = new Matrix();
        m.postScale(-1, 1);   //镜像水平翻转
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        return toBytes(newBitmap);
    }

    public static byte[] rotate(byte[] data, int degree) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Matrix m = new Matrix();
        m.setRotate(degree, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        return toBytes(newBitmap);
    }

}
