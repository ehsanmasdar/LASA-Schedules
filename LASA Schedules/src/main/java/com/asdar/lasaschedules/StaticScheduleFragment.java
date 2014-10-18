package com.asdar.lasaschedules;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;


/**
 * Created by Ehsan on 4/21/2014.
 */
public class StaticScheduleFragment extends Fragment {
    ViewPager pager;
    ActionBar actionBar;
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
        pager = (ViewPager)getActivity().findViewById(R.id.pager);
        pager.setAdapter(new Adapter (getChildFragmentManager()));
        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) getActivity().findViewById(R.id.tabs);
        tabs.setViewPager(pager);
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
            switch(position){
                case 0:
                    return "Normal";
                case 1:
                    return "Advisory";
                case 2:
                    return "Late Start";
                case 3:
                    return "Pep Rally";
                default:
                    return "Normal";
            }
        }
        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
