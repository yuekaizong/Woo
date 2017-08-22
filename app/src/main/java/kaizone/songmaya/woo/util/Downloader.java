package kaizone.songmaya.woo.util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by yuelb on 2015/05/29.
 */
public class Downloader {

    private static final String TAG = "Downloader";

    public static List<Info> query(Context context, int status) {
        DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterByStatus(status);
        Cursor cursor = dm.query(query);
        List<Info> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            Info obj = new Info();
            obj.setId(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID)));
            obj.setTitle(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE)));
            obj.setLocalUri(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
            obj.setBytesDownloadedSoFar(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
            obj.setTotalSizeBytes(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)));
            obj.setUri(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI)));
            obj.setStatus(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)));
            result.add(obj);
        }
        cursor.close();
        Log.e(TAG, "download tasks: " + result.toString());
        return result;
    }

    public static List<Info> queryRunning(Context context) {
        return query(context, DownloadManager.STATUS_RUNNING);
    }


    public static void enqueue(Context context, String uri) {
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(uri));
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, generateFileName(uri));
        r.allowScanningByMediaScanner();
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }

    public static void enqueueOnly(Context context, String uri, Runnable call) {
        List<Info> list = queryRunning(context);
        for (int i = 0; i < list.size(); i++) {
            Info info = list.get(i);
            if (uri.equals(info.getUri())) {
                call.run();
                Log.e(TAG, String.format("enqueueOnly: %s is download running", uri));
                return;
            }
        }
        enqueue(context, uri);
    }

    public static String generateFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.length());
    }

    public static class DownloadBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadId != -1) {
                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                //增加对小米MI 6下载自动安装提示:解析软件包出现问题
                if ("MI 6".equals(Build.MODEL)) {
                    Uri uri = manager.getUriForDownloadedFile(downloadId);
                    String path = MediaUtils.queryFilePath(context, uri);
                    File file = new File(path);
                    Log.e(TAG, "安装文件：" + file.getAbsolutePath());

                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()), "application/vnd.android.package-archive");
                    install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(install);
                } else {
                    Uri uri = manager.getUriForDownloadedFile(downloadId);
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setDataAndType(uri, "application/vnd.android.package-archive");
                    install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(install);

                }

            }
        }
    }

    public static class Info {
        private String id;
        private String title;
        private String localUri;
        private String bytesDownloadedSoFar;
        private String totalSizeBytes;
        private String uri;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLocalUri() {
            return localUri;
        }

        public void setLocalUri(String localUri) {
            this.localUri = localUri;
        }

        public String getBytesDownloadedSoFar() {
            return bytesDownloadedSoFar;
        }

        public void setBytesDownloadedSoFar(String bytesDownloadedSoFar) {
            this.bytesDownloadedSoFar = bytesDownloadedSoFar;
        }

        public String getTotalSizeBytes() {
            return totalSizeBytes;
        }

        public void setTotalSizeBytes(String totalSizeBytes) {
            this.totalSizeBytes = totalSizeBytes;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return String.format("id=%s,title=%s,local_uri=%s,size=%s,totalsize=%s.uri=%s",
                    id, title, localUri, bytesDownloadedSoFar, totalSizeBytes, uri);
        }
    }
}
