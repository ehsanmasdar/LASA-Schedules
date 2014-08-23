package com.asdar.lyschedules;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Ehsan on 4/20/2014.
 */
public class AlarmRespondIntentService extends IntentService{

    public AlarmRespondIntentService() {
        super("AlarmRespondIntentService");
    }

    public static String convertinputStreamToString(InputStream ists)
            throws IOException {
        if (ists != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader r1 = new BufferedReader(new InputStreamReader(
                        ists, "UTF-8"));
                while ((line = r1.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                ists.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }
    protected void onHandleIntent(Intent intent) {
        Log.d("com.asdar.lasaschedules", "Alarm reciever called, pulling new schedule");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String mon = getDayJson(sp,"MON");
        String tue = getDayJson(sp,"TUE");
        String wed = getDayJson(sp,"WED");
        String thu = getDayJson(sp,"THU");
        String fri = getDayJson(sp,"FRI");
        if (mon != null && tue != null && wed != null && thu != null && fri != null && !mon.contains("nostudent")){
            SharedPreferences.Editor e = sp.edit();
            e.putString("mon", mon);
            e.putString("tue", tue);
            e.putString("wed", wed);
            e.putString("thu", thu);
            e.putString("fri", fri);
            e.commit();
        }
        Intent service = new Intent(getApplicationContext(), NotificationService.class);
        if (sp.getBoolean("notification", true)) {
            getApplicationContext().stopService(service);
            getApplicationContext().startService(service);
        } else {
            getApplicationContext().stopService(service);
        }
        try {
            HomeFragment.setSchedule(getApplicationContext());
        }
        catch (Exception ex){

        }
    }
    public String getDayJson(SharedPreferences sp ,String day){
        try {
            URL url = new URL("http://ehsandev.com/lyschedules/fetchschedule.php?email=" + sp.getString("gr","").trim()+"&dw=" + day);
            Log.d("com.asdar.lasaschedules", url.toString());
            URLConnection conn = url.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            InputStream is = httpConn.getInputStream();
            return convertinputStreamToString(is);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
