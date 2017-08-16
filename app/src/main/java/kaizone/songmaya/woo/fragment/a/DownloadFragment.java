package kaizone.songmaya.woo.fragment.a;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kaizone.songmaya.woo.R;
import kaizone.songmaya.woo.util.Downloader;
import kaizone.songmaya.woo.util.RecyclerViewAdapterTemplate;
import kaizone.songmaya.woo.util.Tips;

/**
 * Created by Administrator on 2017/6/28.
 */

public class DownloadFragment extends Fragment {

    RecyclerView mRecyclerView;
    Button mQueryView;
    RecyclerViewAdapterTemplate mAdapter;

    List<String> mData = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mData.add("http://shouji.360tpcdn.com/170711/2229c6442e1fddc77d0b0ef603a45155/com.tencent.tmgp.sgame_20011705.apk");
        mData.add("http://shouji.360tpcdn.com/170711/2229c6442e1fddc77d0b0ef603a45155/com.tencent.tmgp.sgame_20011705.apk");
        mData.add("https://12.shouji.com.cn/wb/soft/2016/20161102/4924627780.apk?filename=com.tencent.eim_3.3.3_167.apk&auth_key=115da7ac5ed12e0fb7bbfdcf3ccd2115");
        mData.add("https://12.shouji.com.cn/wb/soft/2016/20161102/4924627780.apk?filename=com.tencent.eim_3.3.3_167.apk&auth_key=115da7ac5ed12e0fb7bbfdcf3ccd2115");
        mData.add("https://www.tencent.com/zh-cn/articles/8003431495014482.pdf");
        mData.add("https://www.tencent.com/zh-cn/articles/8003431495014482.pdf");
        mData.add("http://www.tencent.com/zh-cn/articles/8003251479983154.pdf");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_download, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mQueryView = (Button) view.findViewById(R.id.button1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerViewAdapterTemplate(mData, R.layout.a_download_item);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDataBindView(new RecyclerViewAdapterTemplate.DataBindView() {
            @Override
            public void bind(RecyclerViewAdapterTemplate.ViewHolder holder, int position, List data) {
                final String url = (String) data.get(position);
                Button b = (Button) holder.findViewId(R.id.button1);
                String name = data.get(position).toString().substring(url.length()-10, url.length());
                b.setText(name);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        download(url);
                    }
                });
            }
        });

        mQueryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryDownaload();
            }
        });

        return view;
    }

    public void download(String url) {

        Downloader.enqueueOnly(getActivity(), url, new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "正在下载中", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void queryDownaload(){
        Tips.toToast(getContext(), Downloader.queryRunning(getContext()).toString());
    }

    private static final String TAG = "DownloadFragment";
    public static final int ID = DownloadFragment.class.hashCode();
    public static final String NAME = DownloadFragment.class.getSimpleName();

    public static DownloadFragment newInstance(Bundle bd) {
        final DownloadFragment f = new DownloadFragment();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }
}
