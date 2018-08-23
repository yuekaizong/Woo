package kaizone.songmaya.woo.util;

import android.content.Context;
import android.util.Log;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.File;

/**
 * Created by yuekaizone on 2017/6/9.
 */

public class FrescoUtils {

    private static final String TAG = "FrescoUtils";

    public static void config(Context context){
        String fileInfo = String.format("cacheDir=%s, externalCache=%s",
                context.getCacheDir().getPath(),
                context.getExternalCacheDir().getPath());
        Log.e(TAG, fileInfo);

//        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context).
//                setBaseDirectoryPath(context.getExternalCacheDir()).build();

//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
//                .setMainDiskCacheConfig(diskCacheConfig)
//                .build();
//        Fresco.initialize(getContext(), config);
        Fresco.initialize(context);
    }

    public static boolean isImageDownloaded(String loadUri, Context context) {
        if (loadUri == null) {
            return false;
        }
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(loadUri), context);
        return ImagePipelineFactory.getInstance().getMainFileCache().hasKey(cacheKey) || ImagePipelineFactory.getInstance().getSmallImageFileCache().hasKey(cacheKey);
    }

    //return file or null
    public static File getCachedImageOnDisk(String loadUri, Context context) {
        File localFile = null;
        if (loadUri != null) {
            CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(loadUri), context);
            if (ImagePipelineFactory.getInstance().getMainFileCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            } else if (ImagePipelineFactory.getInstance().getSmallImageFileCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getSmallImageFileCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            }
        }
        return localFile;
    }
}
