package kaizone.songmaya.woo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import kaizone.songmaya.woo.fragment.FragmentController;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {

    public static final String ID_FRAGMENT = "id_fragment";
    public int mFragmentId;

    private Banner mBanner;
    private FloatingActionButton mFloatingActionButton;

    public static void to(Context context, int id) {
        Intent i = new Intent(context, ItemDetailActivity.class);
        i.putExtra(ID_FRAGMENT, id);
        context.startActivity(i);
    }


    public void replaceContent(int layoutId, Fragment bf) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layoutId, bf);
        ft.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        if (savedInstanceState == null) {
            initContainer(savedInstanceState);
            initBanner();
        }
    }

    public void initContainer(Bundle bd) {
        Fragment f = null;
        if (mFragmentId == 0) {
            mFragmentId = getIntent().getExtras() != null ? getIntent().getExtras().getInt(ID_FRAGMENT) : 0;
        }
        f = FragmentController.obtain(mFragmentId, bd);
        replaceContent(R.id.content, f);
    }

    private void initBanner() {
        mBanner = (Banner) findViewById(R.id.banner);

        mBanner.setImageLoader(new FrescoImageLoader());
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        mBanner.setViewPagerIsScroll(true);

        mBanner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });


    }

    public Banner getBanner() {
        return mBanner;
    }

    public FloatingActionButton getFloatingActionButton() {
        return mFloatingActionButton;
    }

    class FrescoImageLoader extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            SimpleDraweeView draweeView = (SimpleDraweeView)imageView;
            draweeView.setImageURI(path.toString());
        }

        @Override
        public ImageView createImageView(Context context) {
            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
            return simpleDraweeView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return super.onRetainCustomNonConfigurationInstance();
    }

    @Nullable
    @Override
    public Object getLastNonConfigurationInstance() {
        return super.getLastNonConfigurationInstance();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}
