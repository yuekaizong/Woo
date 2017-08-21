package com.livedetect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.livedetect.data.ConstantValues;
import com.livedetect.utils.FileUtils;
import com.livedetect.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 人脸识别成功
 */
public class SuccessActivity extends AppCompatActivity {
    private final String TAG = SuccessActivity.class.getSimpleName();
    private ImageView mReturnBtn, success_img;
    private ImageView returnImg;
    private ImageView mAgainImg;
    private String livedetection_image_path;//图片地址
    private byte[] pic_result;//图片的字节
    private String msg = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileUtils.init(this);
        try {
            setContentView(FileUtils.getResIdByTypeAndName(getApplicationContext(), ConstantValues.RES_TYPE_LAYOUT, "htjc_activity_success"));
        } catch (Exception e) {
            Log.d(TAG, "onCreate Exception : ", e);
            return;
        }

        LogUtil.i(TAG, "success  1 ");
        initView();

        Intent mIntent = getIntent();
        Bundle result = mIntent.getBundleExtra("result");

        boolean check_pass = result.getBoolean("check_pass");
        if(check_pass){
            pic_result = result.getByteArray("pic_result");
            if(pic_result != null){
                Bitmap bitmap = FileUtils.getBitmapByBytesAndScale(pic_result, 1);
                success_img.setImageBitmap(bitmap);
            }
        }

    }

    private void initView() {
        success_img = (ImageView) findViewById(FileUtils.getResIdByTypeAndName(getApplicationContext(), ConstantValues.RES_TYPE_ID, "success_img"));
        mReturnBtn = (ImageView) findViewById(FileUtils.getResIdByTypeAndName(getApplicationContext(), ConstantValues.RES_TYPE_ID, "btn_return"));
        mReturnBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传照片
                finish();
            }
        });

        mAgainImg = (ImageView) findViewById(FileUtils.getResIdByTypeAndName(getApplicationContext(), ConstantValues.RES_TYPE_ID, "btn_again"));
        mAgainImg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, LiveDetectActivity.class);
                startActivity(intent);
                finish();
            }
        });

        returnImg = (ImageView) findViewById(FileUtils.getResIdByTypeAndName(getApplicationContext(), ConstantValues.RES_TYPE_ID, "iv_return"));
        returnImg.setVisibility(View.INVISIBLE);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //FileUtils.setmContext(null);
        //android.os.Process.killProcess(android.os.Process.myPid());
    }




    private void save_image() {
        String path = this.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES).getAbsolutePath();
        File files = new File(path);
        if (!files.exists()) {
            files.mkdirs();
        }

        livedetection_image_path = SuccessActivity.this.//图片地址
                getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + "/bestPict.jpg";
        try {
            File file = new File(livedetection_image_path);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fops = new FileOutputStream(file);
            fops.write(pic_result);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}