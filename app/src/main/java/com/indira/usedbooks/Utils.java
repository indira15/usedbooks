package com.indira.usedbooks;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

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
}
