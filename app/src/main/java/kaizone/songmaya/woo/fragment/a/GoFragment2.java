package kaizone.songmaya.woo.fragment.a;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kaizone.songmaya.woo.R;

/**
 * Created by yuekaizone on 2017/6/12.
 */

public class GoFragment2 extends Fragment {

    ViewPager mViewPager;
    TabLayout mTabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_go2, null);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] titles = {
                "Title1",
                "Title2"
        };
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(GoFragment3.newInstance(getArguments()));
        fragments.add(GoFragment.newInstance(getArguments()));
        mViewPager.setAdapter(new TheAdapter(fragments, titles));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    protected class TheAdapter extends FragmentPagerAdapter {

        String[] titles;
        List<Fragment> fragments;

        public TheAdapter(List<Fragment> fragments, String[] titles) {
            super(getChildFragmentManager());
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles != null && titles.length > position ? titles[position] : null;
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int itemId) {
            return fragments.get(itemId);
            // FragmentHolder.get().createMenuFragment(arg0);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

    }

    public static final int ID = GoFragment2.class.hashCode();
    public static final String NAME = GoFragment2.class.getSimpleName();

    public static GoFragment2 newInstance(Bundle bd) {
        final GoFragment2 f = new GoFragment2();
        if (bd != null) {
            f.setArguments(bd);
        }
        return f;
    }

}
