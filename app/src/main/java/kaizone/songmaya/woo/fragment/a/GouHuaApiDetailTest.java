package kaizone.songmaya.woo.fragment.a;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import kaizone.songmaya.woo.R;
import kaizone.songmaya.woo.util.Tips;
import kaizone.songmaya.woo.widget.DelEditText;

/**
 * Created by Administrator on 2017/8/9.
 */

public class GouHuaApiDetailTest extends Fragment{

    public static final int ID = GouHuaApiDetailTest.class.hashCode();
    public static final String NAME = GouHuaApiDetailTest.class.getSimpleName();
    public static final String TAG = "GouHuaApiTest";

    EditText bankEditText;
    EditText autoDelEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gouhua_apidetail, null);
        bankEditText = (EditText) view.findViewById(R.id.et);

        autoDelEditText = (DelEditText) view.findViewById(R.id.del_et);

        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bank = bankEditText.getText().toString().trim();
                Editable editable = bankEditText.getText();
                String str = editable.toString().replaceAll(" ", "");
                Editable editable2 = new SpannableStringBuilder(str);

                Tips.toDialog(getContext(), editable2.toString());
            }
        });
        return view;
    }

    public static GouHuaApiDetailTest newInstance(Bundle bd) {
        final GouHuaApiDetailTest f = new GouHuaApiDetailTest();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }
}
