package com.indira.usedbooks.fcm;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.indira.usedbooks.Utils;
import okhttp3.internal.Util;

/**
 * Created by jotishsuthar on 24/04/17.
 */

public class FCMMessageService extends FirebaseMessagingService {


  @Override
  public void onMessageReceived(final RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
    if (remoteMessage.getData().size() > 0) {
      Utils.showNotification(this, remoteMessage.getData().get("message"));
    }
  }
}
