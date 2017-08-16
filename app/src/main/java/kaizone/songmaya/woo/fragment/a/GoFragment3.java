package kaizone.songmaya.woo.fragment.a;

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
 * Created by yuekaizone on 2017/6/12.
 */

public class GoFragment3 extends Fragment{

    private RecyclerViewAdapterTemplate mAdapter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerViewAdapterTemplate(new ArrayList(), R.layout.a_item);
        mRecyclerView.setAdapter(mAdapter);
        return mRecyclerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        send();
    }

    public void send(){
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

    }

    public static final int ID = GoFragment3.class.hashCode();
    public static final String NAME = GoFragment3.class.getSimpleName();
    public static GoFragment3 newInstance(Bundle bd) {
        final GoFragment3 f = new GoFragment3();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }
}
