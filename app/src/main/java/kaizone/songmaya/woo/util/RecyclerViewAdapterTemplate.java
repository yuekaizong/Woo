package kaizone.songmaya.woo.util;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yuelibiao on 2016/12/9.
 */
public class RecyclerViewAdapterTemplate<T> extends RecyclerView.Adapter<RecyclerViewAdapterTemplate.ViewHolder> {

    private List<T> data;
    private int rowLayout;

    DataBindView dataBindView;

    public interface DataBindView<T> {
        void bind(final ViewHolder holder, final int position, List<T> data);
    }

    public void setDataBindView(DataBindView dataBindView) {
        this.dataBindView = dataBindView;
    }

    public RecyclerViewAdapterTemplate(List<T> data, int rowLayout) {
        this.data = data;
        this.rowLayout = rowLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (this.dataBindView != null) {
            dataBindView.bind(holder, position, data);
        }
    }

    public void clear() {
        int size = this.data.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                data.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void addData(List<T> data) {
        this.data.addAll(data);
        this.notifyItemRangeChanged(0, data.size() - 1);
    }

    public void add(T t){
        this.data.add(t);
        this.notifyItemRangeChanged(0, data.size() - 1);
    }

    public void add(int index, T t){
        this.data.add(index, t);
        this.notifyItemRangeChanged(0, data.size() - 1);
    }

    public T getItem(int i){
        return this.data.get(i);
    }

    public List<T> getData(){
        return this.data;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ArrayMap<Integer, View> map;

        public ViewHolder(View itemView) {
            super(itemView);
            map = new ArrayMap<>();
            fillMap(itemView);
        }

        private void fillMap(View view) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                map.put(viewGroup.getId(), viewGroup);
                final int count = viewGroup.getChildCount();
                for (int i = 0; i < count; i++) {
                    fillMap(viewGroup.getChildAt(i));
                }
            } else {
                map.put(view.getId(), view);
            }
        }

        public View findViewId(int id) {
            return map.get(id);
        }


    }
}