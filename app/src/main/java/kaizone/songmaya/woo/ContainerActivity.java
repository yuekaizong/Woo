package kaizone.songmaya.woo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import kaizone.songmaya.woo.fragment.FragmentController;

/**
 * Created by yuekaizone on 2017/6/13.
 */

public class ContainerActivity extends BaseActivity {

    public static final String ID_FRAGMENT = "id_fragment";
    public int mFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.a_container);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        initContainer(bd);
    }

    public void initContainer(Bundle bd) {
        Fragment f = null;
        if (mFragmentId == 0) {
            mFragmentId = getIntent().getExtras() != null ? getIntent().getExtras().getInt(ID_FRAGMENT) : 0;
        }
        f = FragmentController.obtain(mFragmentId, bd);
        replaceContent(R.id.content, f);
    }

    public void addContent(int layoutId, Fragment bf) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(layoutId, bf);
        ft.commit();
    }

    public void replaceContent(int layoutId, Fragment bf) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layoutId, bf);
        ft.commit();
    }

    public static void to(Context context, int id) {
        Intent i = new Intent(context, ContainerActivity.class);
        i.putExtra(ID_FRAGMENT, id);
        context.startActivity(i);
    }

    public static void toForResult(Context context, int id, int requestCode) {
        Intent i = new Intent(context, ContainerActivity.class);
        i.putExtra(ID_FRAGMENT, id);
        Activity activity = (Activity) context;
        activity.startActivityForResult(i, requestCode);
    }

    public static void to(Context context, int id, Bundle bd) {
        Intent i = new Intent(context, ContainerActivity.class);
        i.putExtra(ID_FRAGMENT, id);
        i.putExtras(bd);
        context.startActivity(i);
    }
}
