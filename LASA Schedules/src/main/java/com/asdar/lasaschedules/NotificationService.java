package com.asdar.lasaschedules;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ehsan on 4/17/2014.
 */


public class NotificationService extends Service {
    private final IBinder binder = new NotificationBinder();
    private Schedule s;
    private int id = 111111;
    private ScheduledExecutorService t;
    NotificationCompat.Builder builder;
    Notification.Builder noncompat;
    public int onStartCommand(Intent intent, int flags, int startID) {
       serviceRunner();
       if (Build.VERSION.SDK_INT >= 20){
           noncompat = new Notification.Builder(this);
       }
       else{
           builder = new NotificationCompat.Builder(this);
       }
       return 1;
    }

    public void serviceRunner() {
        final NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        t = Executors.newSingleThreadScheduledExecutor();
        t.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                    /* temp1.get(0) = Current Class name
                       temp1.get(1) = Current Class start time
                       temp2.get(0) = Time left in current class
                       temp2.get(1) = End time of current class
                       temp2.get(2) = Next Class start time
                     */
                s = Resources.getSchedule(getApplicationContext());
                Calendar c = Calendar.getInstance();
                ArrayList<String> temp1 = s.getCurrent();
                ArrayList<String> temp2 = s.getTimeTillNext();
                if (s == null) {
                    mNotificationManager.cancel(id);
                } else if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                    mNotificationManager.cancel(id);
                } else {
                    if (temp1.size() == 0 || temp2.size() == 0) {
                        mNotificationManager.cancel(id);
                    } else {
                        sendNotification(temp1.get(0), temp2.get(0));
                    }
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    public class NotificationBinder extends Binder {
        public NotificationBinder() {
        }

        NotificationService getService() {
            return NotificationService.this;
        }
    }

    public void sendNotification(String place, String min) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(Build.VERSION.SDK_INT >= 20){
            noncompat.setContentTitle("In " + place);
            noncompat.setSmallIcon(R.drawable.ic_stat_notification);
            //Plural/singular
            if (min.equals("1")){
                noncompat.setContentText(min + " minute remains");
            }
            else{
                noncompat.setContentText(min + " minutes remain");
            }
            noncompat.setPriority(Notification.PRIORITY_HIGH);
            noncompat.setContentIntent(localPendingIntent);
            noncompat.setOngoing(true);
            noncompat.setOnlyAlertOnce(true);
            noncompat.setVisibility(Notification.VISIBILITY_PUBLIC);
            noncompat.setColor(getResources().getColor(R.color.primary));
            noncompat.setCategory(Notification.CATEGORY_ALARM);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(id, noncompat.build());
        }
        else {
            builder.setContentTitle("In " + place);
            builder.setSmallIcon(R.drawable.ic_stat_notification);
            //Plural/singular
            if (min.equals("1")){
                builder.setContentText(min + " minute remains");
            }
            else{
                builder.setContentText(min + " minutes remain");
            }
            builder.setPriority(-2);
            builder.setContentIntent(localPendingIntent);
            builder.setOngoing(true);
            builder.setOnlyAlertOnce(true);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(id, builder.build());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        t.shutdown();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(id);
    }
}
