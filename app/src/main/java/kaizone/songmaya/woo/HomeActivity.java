package kaizone.songmaya.woo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import kaizone.songmaya.jsyl.retrofitutil.bean.Result;
import kaizone.songmaya.jsyl.retrofitutil.bean.Viewpoint;
import kaizone.songmaya.jsyl.retrofitutil.user.APIFactory;
import kaizone.songmaya.jsyl.retrofitutil.util.SubscriberOnErrorListener;
import kaizone.songmaya.jsyl.retrofitutil.util.SubscriberOnNextListenter;
import kaizone.songmaya.woo.fragment.FragmentController;
import kaizone.songmaya.woo.fragment.a.GoFragment;
import kaizone.songmaya.woo.fragment.a.GoWebFragment;
import kaizone.songmaya.woo.fragment.a.GouHuaApiTest;
import kaizone.songmaya.woo.fragment.a.LocalFunc;
import kaizone.songmaya.woo.fragment.a.TestData;
import kaizone.songmaya.woo.util.RecyclerViewAdapterTemplate;
import kaizone.songmaya.woo.util.Tips;

/**
 * Created by yuekaizhong on 2016/5/5.
 */
public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    private final static int REQUEST_CODE_1 = 1001;

    private RecyclerViewAdapterTemplate mAdapter;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;

    private ProgressBar mProgressBar;
    private FloatingActionButton mFabButton;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private View mPanel1;
    private View mPanel2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Handle ProgressBar
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Fab Button
        mFabButton = (FloatingActionButton) findViewById(R.id.fab_normal);
        mFabButton.setOnClickListener(fabClickListener);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerViewAdapterTemplate(new ArrayList(), R.layout.a_item);
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
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                send();
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        setupDrawerContent(mNavigationView);
        setUpProfileImage();
        setUpPanel();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int r : grantResults) {
            if (r == PackageManager.PERMISSION_DENIED) {
                finish();
                return;
            }
        }

    }

    public void checkPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                },
                120);
    }

    public void setUpPanel() {
        findViewById(R.id.panel1).setVisibility(View.GONE);
        findViewById(R.id.panel2).setVisibility(View.VISIBLE);
        replaceFragment(LocalFunc.ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + String.format("requestCode=%s,resultCode=%s", requestCode, resultCode));
    }

    private void setUpProfileImage() {
        View headerView = mNavigationView.inflateHeaderView(R.layout.navigation_header);
        View profileView = headerView.findViewById(R.id.profile_image);
        if (profileView != null) {
            profileView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.closeDrawers();
                    mNavigationView.getMenu().getItem(1).setChecked(true);
                }
            });
        }

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_item_home:
                                replaceFragment(GouHuaApiTest.ID);
                                break;
                            case R.id.navigation_item_profile:
                                ItemDetailActivity.to(HomeActivity.this, GoFragment.ID);
                                break;
                            case R.id.navigation_item_message:
//                                Intent intent = new Intent(HomeActivity.this, HaierLoanMainActivity.class);
//                                HomeActivity.this.startActivity(intent);
                                break;
                            case R.id.navigation_item_setting:
                                ContainerActivity.toForResult(HomeActivity.this, GoWebFragment.ID, REQUEST_CODE_1);
                                break;

                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    public void send() {
        mAdapter.addData(TestData.getViewpoints());
        mSwipeRefreshLayout.setRefreshing(true);
        mProgressBar.setVisibility(View.VISIBLE);

        APIFactory.getInstance().getViewpointAll(new SubscriberOnNextListenter<Result<List<Viewpoint>>>() {
            @Override
            public void next(Result<List<Viewpoint>> result) {
                Tips.toToast(HomeActivity.this, result.message);
                mSwipeRefreshLayout.setRefreshing(false);
                mProgressBar.setVisibility(View.GONE);

                mAdapter.clear();
                mAdapter.addData(result.data);
            }
        }, new SubscriberOnErrorListener() {
            @Override
            public void onError(Throwable e) {
                mSwipeRefreshLayout.setRefreshing(false);
                mProgressBar.setVisibility(View.GONE);
            }
        }, this);
    }

    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private void replaceFragment(int fragmentId) {
        Fragment f = FragmentController.obtain(fragmentId, null);
        replaceContent(R.id.panel2, f);
    }

    private void replaceContent(int layoutId, Fragment bf) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layoutId, bf);
        ft.commit();
    }
}
