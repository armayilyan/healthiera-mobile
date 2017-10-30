package com.healthiera.mobile.notification_service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.healthiera.mobile.R;


import com.healthiera.mobile.Schedule.Appointment;
import com.healthiera.mobile.activitys.CarePlanActivity_1;
import com.healthiera.mobile.activitys.CarePlanActivityes.Apointment;
import com.healthiera.mobile.entity.ScheduleTime;
import com.healthiera.mobile.serivce.ScheduleTimeService;

import org.joda.time.LocalDate;

import java.util.List;

public class EventNotifiService extends IntentService {

    private static final int POLL_INTERVAL = 50;
    private static final String TAG = "PollService";
    private static final ScheduleTimeService ScheduleTimeService = new ScheduleTimeService();
    Context c;

    public static Intent newIntent(Context context, Class<CarePlanActivity_1> carePlanActivity_1Class) {
        return new Intent(context, EventNotifiService.class);
    }

    public EventNotifiService() {
        super(TAG);
    }

    @Override

    protected void onHandleIntent(Intent intent) {

        try {
           c = createPackageContext("com.healtiera.mobile", Context.CONTEXT_INCLUDE_CODE| Context.CONTEXT_IGNORE_SECURITY);

       }catch(Throwable t ){
           t.printStackTrace();
       }

       // notifiControl();
    }

    public void notifiControl(){
        List<ScheduleTime> notifis= ScheduleTimeService.getNearService(LocalDate.now());
       //     notifiRepeat(notifis);
    }


    public static void setServiceAlarm(Context context, boolean isOn) {

        Intent i = EventNotifiService.newIntent(context, CarePlanActivity_1.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(),POLL_INTERVAL, pi);//FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_HALF_DAY

        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    private void notifiRepeat(List<ScheduleTime> notifis){
        Resources resources = getResources();


        Intent intent = Apointment.newIntent(this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.care_icon)
                .setContentTitle("GCM Message")
                .setContentText("My awesome notification")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }
}