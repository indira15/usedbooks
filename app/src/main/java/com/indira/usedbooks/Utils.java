package com.indira.usedbooks;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Manish on 09-04-2017.
 */

public class Utils {

    public static boolean isActivityAlive(Activity activity){
        return !(null == activity || activity.isFinishing() || activity.isDestroyed());
    }
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showNotification(Context context, String text) {
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("User interested in your book!!")
                .setContentText(text);
        NotificationCompat.InboxStyle inboxStyle =
            new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("User interested in your book!!");
        inboxStyle.addLine(text);
        mBuilder.setStyle(inboxStyle);
        NotificationManager mNotificationManager =
            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       mNotificationManager.notify((new Random()).nextInt(), mBuilder.build());
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
