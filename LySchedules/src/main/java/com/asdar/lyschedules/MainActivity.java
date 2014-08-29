package com.asdar.lyschedules;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends ActionBarActivity {
    public ProgressDialog dialog;
    String[] mDrawerArray;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        mDrawerArray = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerArray));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_navigation_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(R.string.app_name);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            selectItem(0);
        }
        PushService.setDefaultPushCallback(this, MainActivity.class);
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(this);
        if (s.getBoolean("updates", true)) {
            PushService.subscribe(getApplicationContext(), "updates", MainActivity.class, R.drawable.notification);
        } else {
            PushService.unsubscribe(this, "updates");
        }
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseAnalytics.trackAppOpened(getIntent());
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sp.getString("gr", "").equals("")
                || sp.getString("mon", "nostudent").contains("nostudent")
                || sp.getString("tue", "nostudent").contains("nostudent")
                || sp.getString("wed", "nostudent").contains("nostudent")
                || sp.getString("thu", "nostudent").contains("nostudent")
                || sp.getString("fri", "nostudent").contains("nostudent")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Welcome");
            alert.setMessage("Enter your email to load your schedule");
            // Set an EditText view to get user input
            LayoutInflater inflater = getLayoutInflater();
            final View v = inflater.inflate(R.layout.email_dialog, null);
            alert.setView(v);
            alert.setCancelable(false);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    SharedPreferences.Editor e = sp.edit();
                    e.putString("gr", ((EditText) v.findViewById(R.id.emailtop)).getText().toString() + "@lyceumschool.edu.pk");
                    e.commit();
                    new ProgressdialogClass().execute();
                    Intent intent = new Intent(getApplicationContext(), AlarmRespondIntentService.class);
                    startService(intent);
                }
            });
            alert.show();
        }
        alarmMgr = (AlarmManager) getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmRespondIntentService.class);
        alarmIntent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1,
                AlarmManager.INTERVAL_HALF_HOUR, alarmIntent);
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (position == 0) {
            Fragment fragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
        if (position == 1) {
            StaticScheduleFragment fragment = new StaticScheduleFragment();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
        if (position == 2) {
            SettingsFragment fragment = new SettingsFragment();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        }
        if (position == 3) {
            String url = "http://www.lyceumschool.edu.pk/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        // update selected item and title, then close the drawer
        if (position != 3) {
            mDrawerList.setItemChecked(position, true);
            setTitle(getResources().getStringArray(R.array.drawer_array)[position]);
        }
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.today, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    class ProgressdialogClass extends AsyncTask<Void, Void, String> {
        boolean sucess = true;

        @Override
        protected String doInBackground(Void... unsued) {
            ConnectivityManager cm =
                    (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            if (isConnected) {
                String mon = getDayJson(sp, "MON");
                String tue = getDayJson(sp, "TUE");
                String wed = getDayJson(sp, "WED");
                String thu = getDayJson(sp, "THU");
                String fri = getDayJson(sp, "FRI");
                if (mon.contains("nostudent")) {
                    DialogFragment alert = (DialogFragment) ErrorThrower
                            .newInstance(
                                    "Schedule not found. \n Please make sure you have typed your email correctly and try again.",
                                    true);
                    alert.show(getSupportFragmentManager(), "interneterror");
                    SharedPreferences.Editor e = sp.edit();
                    e.remove("gr");
                    e.commit();
                } else {
                    SharedPreferences.Editor e = sp.edit();
                    e.putString("mon", mon);
                    e.putString("tue", tue);
                    e.putString("wed", wed);
                    e.putString("thu", thu);
                    e.putString("fri", fri);
                    e.commit();
                }
            }
            else {
                DialogFragment alert = (DialogFragment) ErrorThrower
                        .newInstance(
                                "Unable to load schedule. \n Please make sure you have an active internet connection and try again.",
                                true);
                alert.show(getSupportFragmentManager(), "interneterror");
                SharedPreferences.Editor e = sp.edit();
                e.remove("gr");
                e.commit();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String sResponse) {
            dialog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(MainActivity.this, "Loading Schedules",
                    "Please wait...", true);

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
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
