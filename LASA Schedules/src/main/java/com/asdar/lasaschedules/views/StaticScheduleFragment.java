package com.asdar.lasaschedules.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asdar.lasaschedules.R;
import com.asdar.lasaschedules.util.Resources;
import com.astuetz.PagerSlidingTabStrip;


public class StaticScheduleFragment extends Fragment {
    ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scheduleframe, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public void onResume() {
        super.onResume();
        pager = (ViewPager) getActivity().findViewById(R.id.pager);
        pager.setAdapter(new Adapter(getChildFragmentManager()));
        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) getActivity().findViewById(R.id.tabs);
        tabs.setViewPager(pager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class Adapter extends FragmentPagerAdapter {
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt("request", i);
            ScheduleFragment fragment = new ScheduleFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return Resources.getAllSchedules(getContext()).get(0).getName();
        }

        @Override
        public int getCount() {
            return Resources.getAllSchedules(getContext()).size();
        }
    }
}
