package com.asdar.lasaschedules.views;

import com.asdar.lasaschedules.R;
import com.asdar.lasaschedules.service.NotificationService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.preference.PreferenceFragment;

/**
 * Created by Ehsan on 4/2/14.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        if (key.equals("updates") && !prefs.getBoolean("updates", true)) {
        }
        if (key.equals("updates") && prefs.getBoolean("notification", true)) {

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
