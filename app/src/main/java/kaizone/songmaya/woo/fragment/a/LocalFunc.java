package kaizone.songmaya.woo.fragment.a;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kaizone.songmaya.woo.AppConfig;
import kaizone.songmaya.woo.ContainerActivity;
import kaizone.songmaya.woo.R;
import kaizone.songmaya.woo.util.RecyclerViewAdapterTemplate;

/**
 * Created by yuekaizone on 2017/8/26.
 */

public class LocalFunc extends Fragment{

    public static final int ID = LocalFunc.class.hashCode();
    public static final String NAME = LocalFunc.class.getSimpleName();
    public static final String TAG = "LocalFunc";
    public static LocalFunc newInstance(Bundle bd) {
        final LocalFunc f = new LocalFunc();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    RecyclerViewAdapterTemplate mAdapter;

    List<String> mData = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i=0; i< AppConfig.a.length; i++){
            mData.add(AppConfig.a[i][0]);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.gouhua_apitest_panel, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerViewAdapterTemplate(mData, R.layout.gouhua_apitest_panel_item);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDataBindView(new RecyclerViewAdapterTemplate.DataBindView() {
            @Override
            public void bind(RecyclerViewAdapterTemplate.ViewHolder holder, final int position, List data) {
                final String obj = (String) data.get(position);
                String text = String.format("%s", obj);
                TextView textView = (TextView) holder.findViewId(R.id.text1);
                textView.setText(text);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int fragmentId = Integer.parseInt(AppConfig.a[position][1]);
                        ContainerActivity.to(getActivity(), fragmentId);
                    }
                });
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
}
