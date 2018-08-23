package kaizone.songmaya.woo.fragment.a;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import kaizone.songmaya.woo.ItemDetailActivity;
import kaizone.songmaya.woo.R;
import kaizone.songmaya.woo.util.MediaUtils;
import kaizone.songmaya.woo.util.RecyclerViewAdapterTemplate;
import kaizone.songmaya.woo.util.Tips;

/**
 * Created by yuekaizone on 2017/6/7.
 */

public class GoFragment extends Fragment {
    public static final int ID = GoFragment.class.hashCode();
    public static final String NAME = GoFragment.class.getSimpleName();

    Uri mUriImageCapture;
    RecyclerViewAdapterTemplate adapter;
    int mIndex;

    ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
        }

        @Override
        public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
            super.onIntermediateImageSet(id, imageInfo);
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            super.onFailure(id, throwable);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return view2(inflater);
        return view1();
    }

    @Override
    public void onStart() {
        super.onStart();
//        sendGetViewpoint(0);
    }

    public void updateContainerCompent(Viewpoint data) {
        if (ItemDetailActivity.class.isInstance(getActivity())) {
            ItemDetailActivity activity = (ItemDetailActivity) getActivity();

            activity.getFloatingActionButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendSaveViewpint();
                }
            });
            List<String> urls = new ArrayList<>();
            urls.add(data.img1);
            urls.add(data.img2);
            urls.add(data.img3);
            urls.add(data.img4);
            urls.add(data.img5);
            urls.add(data.img6);
            activity.getBanner().setImages(urls);
//            mBanner.setBannerTitles(bannerTitle);
            activity.getBanner().start();
        }
    }

    private void sendSaveViewpint(){
        List<String> data = adapter.getData();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < data.size(); i++) {
            String item = data.get(i);
            if (item.startsWith("file:")) {
                item = item.replace("file:", "");
                list.add(item);
            }
        }
    }

    public View view2(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.a_go, null);
        final PDFView pdfView = (PDFView) view.findViewById(R.id.text);
        pdfView.fromUri(Uri.parse("content://downloads/my_downloads/9"))   //设置pdf文件地址
                .defaultPage(0)         //设置默认显示第1页
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {

                    }
                })     //设置翻页监听
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {

                    }
                })           //设置加载监听
                .onDraw(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                    }
                })            //绘图监听
                .swipeHorizontal(false)  //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                .enableSwipe(true)   //是否允许翻页，默认是允许翻
                .load();

        Button button1 = (Button) view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        ArrayList<String> data = new ArrayList<>();
        data.add("http://www.easyicon.net/api/resizeApi.php?id=1205834&size=128");
        adapter = new RecyclerViewAdapterTemplate(data, R.layout.a_item);
        adapter.setDataBindView(new RecyclerViewAdapterTemplate.DataBindView() {
            @Override
            public void bind(RecyclerViewAdapterTemplate.ViewHolder holder, final int position, List data) {
                SimpleDraweeView draweeView = (SimpleDraweeView) holder.findViewId(R.id.drawee);
                TextView itemTextView = (TextView) holder.findViewId(R.id.text);
                String url = data.get(position).toString();
                draweeView.setImageURI(url, getContext());
                itemTextView.setText(url);
                draweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        proessImage(position);
                    }
                });
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                mUriImageCapture = data.getData();
                String path = MediaUtils.uriToImageMediaPath(getActivity(), mUriImageCapture);
                addItem(mIndex, "file:" + path);
            }
            //
            else if (requestCode == 1) {
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap b = (Bitmap) extras.get("data");

                }

                if (mUriImageCapture != null) {
                    String path = MediaUtils.uriToImageMediaPath(getActivity(), mUriImageCapture);
                    addItem(mIndex, "file:" + path);
                }
            }
        }
    }

    public void addItem(int index, String item) {
        adapter.add(index, item);
//        adapter.add("http://www.easyicon.net/api/resizeApi.php?id=1205834&size=128");
    }

    void proessImage(int position) {
        mIndex = position;
        final String[] actions = {
                "本地照片", "拍照"
        };
        Tips.toDiaologList(getContext(), "请选择", actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    MediaUtils.pickImage(which, GoFragment.this);
                }//
                else if (which == 1) {
                    mUriImageCapture = MediaUtils.imageCapturegetUri(which, GoFragment.this);
                }
            }
        });
    }

    public View view1() {
        LinearLayout panel = new LinearLayout(getContext());
        panel.setOrientation(LinearLayout.VERTICAL);
        final TextView textView = new TextView(getContext());

        final EditText editText1 = new EditText(getContext());
        final EditText editText2 = new EditText(getContext());
        final EditText editText3 = new EditText(getContext());

        editText1.setText("yanglu");
        editText2.setText("yanglu");
        editText3.setText("我是杨璐");

        Button button1 = new Button(getContext());
        button1.setText("notice");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button button2 = new Button(getContext());
        button2.setText("create");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        LinearLayout subPanel1 = new LinearLayout(getContext());
        subPanel1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 400, 1);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        final SimpleDraweeView imageView1 = new SimpleDraweeView(getContext());
        final SimpleDraweeView imageView2 = new SimpleDraweeView(getContext());
        final SimpleDraweeView imageView3 = new SimpleDraweeView(getContext());
        subPanel1.addView(imageView1, params1);
        subPanel1.addView(imageView2, params2);
        subPanel1.addView(imageView3, params3);

        Button button3 = new Button(getContext());
        button3.setText("loading");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        panel.addView(editText1);
        panel.addView(editText2);
        panel.addView(editText3);
        panel.addView(button1);
        panel.addView(button2);
        panel.addView(button3);
        panel.addView(textView);
        panel.addView(subPanel1);
        return panel;
    }


//    /**
//     * 从网络下载图片
//     * 1、根据提供的图片URL，获取图片数据流
//     * 2、将得到的数据流写入指定路径的本地文件
//     *
//     * @param url            URL
//     * @param loadFileResult LoadFileResult
//     */
//    public static void downloadImage(Context context, String url, final DownloadImageResult loadFileResult) {
//        if (TextUtils.isEmpty(url)) {
//            return;
//        }
//
//        Uri uri = Uri.parse(url);
//        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri);
//        ImageRequest imageRequest = builder.build();
//
//        // 获取未解码的图片数据
//        DataSource<CloseableReference<PooledByteBuffer>> dataSource = imagePipeline.fetchEncodedImage(imageRequest, context);
//        dataSource.subscribe(new BaseDataSubscriber<CloseableReference<PooledByteBuffer>>() {
//            @Override
//            public void onNewResultImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
//                if (!dataSource.isFinished() || loadFileResult == null) {
//                    return;
//                }
//
//                CloseableReference<PooledByteBuffer> imageReference = dataSource.getResult();
//                if (imageReference != null) {
//                    final CloseableReference<PooledByteBuffer> closeableReference = imageReference.clone();
//                    try {
//                        PooledByteBuffer pooledByteBuffer = closeableReference.get();
//                        InputStream inputStream = new PooledByteBufferInputStream(pooledByteBuffer);
//                        String photoPath = loadFileResult.getFilePath();
//                        Log.i("ImageLoader", "photoPath = " + photoPath);
//
//                        byte[] data = StreamTool.read(inputStream);
//                        StreamTool.write(photoPath, data);
//                        loadFileResult.onResult(photoPath);
//                    } catch (IOException e) {
//                        loadFileResult.onFail();
//                        e.printStackTrace();
//                    } finally {
//                        imageReference.close();
//                        closeableReference.close();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailureImpl(DataSource dataSource) {
//                if (loadFileResult != null) {
//                    loadFileResult.onFail();
//                }
//
//                Throwable throwable = dataSource.getFailureCause();
//                if (throwable != null) {
//                    Log.e("ImageLoader", "onFailureImpl = " + throwable.toString());
//                }
//            }
//        }, Executors.newSingleThreadExecutor());
//    }

    public static GoFragment newInstance(Bundle bd) {
        final GoFragment f = new GoFragment();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }

}
