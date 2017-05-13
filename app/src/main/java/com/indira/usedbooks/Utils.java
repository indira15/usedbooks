package com.indira.usedbooks;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.widget.Toast;

import java.text.Normalizer;
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

    public static void showNotification(Context context, String text, PendingIntent intent) {
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
        mBuilder.setContentIntent(intent);
        mBuilder.setAutoCancel(true);
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
    public static CharSequence highlight(String search, String originalText) {
        // ignore case and accents
        // the same thing should have been done for the search text
        String normalizedText = Normalizer.normalize(originalText,
                Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        int start = normalizedText.indexOf(search);
        if (start < 0) {
            // not found, nothing to to
            return originalText;
        } else {
            Spannable spannable = new SpannableString(originalText);
            ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{
                    ContextCompat.getColor(UsedbooksApplication.getInstance(), R.color.colorPrimary)
            });
            TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD_ITALIC,
                    -1, blueColor, null);
            spannable.setSpan(highlightSpan, start, start + search.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannable;
        }
    }
}
