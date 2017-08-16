package kaizone.songmaya.woo.fragment.a;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import kaizone.songmaya.jsyl.retrofitutil.bean.Viewpoint;
import kaizone.songmaya.woo.R;
import kaizone.songmaya.woo.util.RecyclerViewAdapterTemplate;

/**
 * Created by yuekaizone on 2017/6/15.
 */

public class GoMyPublishFragment extends Fragment{

    private RecyclerViewAdapterTemplate mAdapter;
    private RecyclerView mRecyclerView;
    private SimpleDraweeView mDraweeView;
    private SimpleDraweeView mLogoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_mypublish, null);

        mDraweeView = (SimpleDraweeView) view.findViewById(R.id.drawee);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mLogoView = (SimpleDraweeView) view.findViewById(R.id.logo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerViewAdapterTemplate(new ArrayList(), R.layout.a_item);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        send();
    }

    public void send(){
        mDraweeView.setImageURI("http://img.sj33.cn/uploads/allimg/201605/21053BR0-6.jpg");
        mLogoView.setImageURI("http://img1.touxiang.cn/uploads/20131121/21-074914_95.jpg");
        mAdapter.setDataBindView(new RecyclerViewAdapterTemplate.DataBindView() {
            @Override
            public void bind(RecyclerViewAdapterTemplate.ViewHolder holder, int position, List data) {
                Viewpoint item = (Viewpoint) data.get(position);
                TextView textView = (TextView) holder.findViewId(R.id.text);
                SimpleDraweeView draweeView = (SimpleDraweeView) holder.findViewId(R.id.drawee);
                textView.setText(item.name);
                draweeView.setImageURI(item.img1);
            }
        });
        mAdapter.addData(TestData.getViewpoints());
        mLogoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        });
    }

    public static final int ID = GoMyPublishFragment.class.hashCode();
    public static final String NAME = GoMyPublishFragment.class.getSimpleName();
    public static GoMyPublishFragment newInstance(Bundle bd) {
        final GoMyPublishFragment f = new GoMyPublishFragment();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }
}
