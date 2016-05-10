package com.asdar.lasaschedules.service;

import com.asdar.lasaschedules.MainActivity;
import com.asdar.lasaschedules.R;
import com.asdar.lasaschedules.util.Event;
import com.asdar.lasaschedules.util.Resources;
import com.asdar.lasaschedules.util.Schedule;

import org.joda.time.DateTime;

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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationService extends Service {
    private final IBinder binder = new NotificationBinder();
    NotificationCompat.Builder builder;
    Notification.Builder noncompat;
    private Schedule s;
    private int id = 1231231;
    private ScheduledExecutorService t;

    public int onStartCommand(Intent intent, int flags, int startID) {
        serviceRunner();
        if (Build.VERSION.SDK_INT >= 20) {
            noncompat = new Notification.Builder(this);
        } else {
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
                s = Resources.getSchedule(getApplicationContext());
                DateTime now = new DateTime();

                if (s != null && (now.dayOfWeek().get() < 5)) {
                    Event e = s.getCurrent();
                    if (e != null) {
                        sendNotification(e.name, s.getTimeTillNext().toString());
                    }
                    else{
                        mNotificationManager.cancel(id);
                    }
                } else {
                    mNotificationManager.cancel(id);
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    public void sendNotification(String place, String min) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT > 20) {
            noncompat.setContentTitle("In " + place);
            noncompat.setSmallIcon(R.drawable.ic_stat_notification);
            //Plural/singular
            if (min.equals("1")) {
                noncompat.setContentText(min + " minute remains");
            } else {
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
        } else {
            builder.setContentTitle("In " + place);
            builder.setSmallIcon(R.drawable.ic_stat_notification);
            //Plural/singular
            if (min.equals("1")) {
                builder.setContentText(min + " minute remains");
            } else {
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

    public class NotificationBinder extends Binder {
        public NotificationBinder() {
        }

        NotificationService getService() {
            return NotificationService.this;
        }
    }
}
