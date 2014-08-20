package com.asdar.lyschedules;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.preference.PreferenceFragment;
import android.util.Log;

import com.parse.ParseInstallation;
import com.parse.PushService;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Ehsan on 4/2/14.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private Preference tempPref;
    private String newgr;
    private String oldgr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.fragment_settings);
        Preference about = findPreference("about");
        about.setOnPreferenceClickListener(this);
        Preference gr = findPreference("gr");
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        gr.setSummary(sp.getString("gr", ""));
        gr.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                SharedPreferences.Editor e = sp.edit();
                oldgr = sp.getString("gr", "");
                e.commit();
                tempPref = preference;
                newgr = (String) o;
                AsyncTask task = new ProgressdialogClass().execute();
                return true;
            }
        });
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
            PushService.unsubscribe(getActivity().getApplicationContext(), "updates");
        }
        if (key.equals("updates") && prefs.getBoolean("notification", true)) {
            PushService.subscribe(getActivity().getApplicationContext(), "updates", MainActivity.class, R.drawable.notification);
            ParseInstallation.getCurrentInstallation().saveInBackground();
        }
        if (key.equals("gr")) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
            Intent intent = new Intent(getActivity().getApplicationContext(), AlarmRespondIntentService.class);
            getActivity().startService(intent);
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

    class ProgressdialogClass extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog dialog;
        boolean success;
        @Override
        protected Boolean doInBackground(Void... unsued) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            String mon = getDayJson(sp, "MON");
            String tue = getDayJson(sp, "TUE");
            String wed = getDayJson(sp, "WED");
            String thu = getDayJson(sp, "THU");
            String fri = getDayJson(sp, "FRI");
            if (mon.contains("nostudent")){
                success = false;
                DialogFragment alert = (DialogFragment) ErrorThrower
                        .newInstance(
                                "Schedule not found. \n Please make sure you have typed your email correctly and try again.",
                                false);
                alert.show(getActivity().getSupportFragmentManager(), "loaderror");
            }
            else if (success == false){
                //Do nothing
                DialogFragment alert = (DialogFragment) ErrorThrower
                        .newInstance(
                                "The current user was not changed. \n Please make sure you have an active internet connection and try again.",
                                false);
                alert.show(getActivity().getSupportFragmentManager(), "interneterror");
            }
            else {
                SharedPreferences.Editor e = sp.edit();
                e.putString("mon", mon);
                e.putString("tue", tue);
                e.putString("wed", wed);
                e.putString("thu", thu);
                e.putString("fri", fri);
                e.commit();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tempPref.setSummary((String) newgr);
                    }
                });
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean sResponse) {
            dialog.dismiss();
            SharedPreferences.Editor e  = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
            if (success) {
                e.putString("gr",newgr);
                tempPref.setSummary(newgr);
            }
            else{
                e.putString("gr",oldgr);
                tempPref.setSummary(oldgr);
            }
            e.commit();
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(), "Loading Schedules",
                    "Please wait...", true);
            success = true;
        }

        public String getDayJson(SharedPreferences sp, String day) {
            try {
                URL url = new URL("http://ehsandev.com/lyschedules/fetchschedule.php?email=" + sp.getString("gr", "").trim() + "&dw=" + day);
                Log.d("com.asdar.lasaschedules", url.toString());
                URLConnection conn = url.openConnection();

                HttpURLConnection httpConn = (HttpURLConnection) conn;
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();

                InputStream is = httpConn.getInputStream();
                return AlarmRespondIntentService.convertinputStreamToString(is);


            } catch (Exception e) {
                e.printStackTrace();
                success = false;

            }
            return null;
        }
    }
}
