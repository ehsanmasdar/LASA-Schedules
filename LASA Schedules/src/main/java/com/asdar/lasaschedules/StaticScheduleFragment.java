package com.asdar.lasaschedules;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Ehsan on 4/21/2014.
 */
public class StaticScheduleFragment extends Fragment {
    Adapter adapter;
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
        actionBar = ((FragmentActivity) getActivity()).getActionBar();
        adapter = new Adapter (getChildFragmentManager());
        pager = (ViewPager)getActivity().findViewById(R.id.pager);
        if (adapter == null)
            Log.d("com.asdar.lasaschedules", "adapter bad");
        if (pager == null)
            Log.d("com.asdar.lasaschedules", "pager bad");
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        actionBar.setSelectedNavigationItem(position);
                    }
                });
        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

            }
        };
        actionBar.addTab(actionBar.newTab().setText("Normal").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Forum").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Late Start").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Pep Rally").setTabListener(tabListener));
    }
    public class Adapter extends FragmentPagerAdapter {
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            ScheduleFragment fragment = new ScheduleFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt("request",i );
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (actionBar != null){
            actionBar.removeAllTabs();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        }
    }
}
