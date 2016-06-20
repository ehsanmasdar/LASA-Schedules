package com.asdar.lasaschedules.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.asdar.lasaschedules.R;
import com.asdar.lasaschedules.service.NotificationService;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.fragment_settings);
        Preference about = findPreference("about");
        about.setOnPreferenceClickListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals("notification") && !prefs.getBoolean("notification", true)) {
            Intent service = new Intent(getActivity(), NotificationService.class);
            getActivity().stopService(service);
        }
        if (key.equals("notification") && prefs.getBoolean("notification", true)) {
            Intent service = new Intent(getActivity(), NotificationService.class);
            getActivity().stopService(service);
            getActivity().startService(service);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("about")) {
            AboutDialog about = new AboutDialog(getActivity());
            about.setTitle("LASA Schdules");
            about.show();
        }
        return false;
    }
}
