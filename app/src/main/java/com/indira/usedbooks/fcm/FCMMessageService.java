package com.indira.usedbooks.fcm;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.indira.usedbooks.ContactActivity;
import com.indira.usedbooks.Utils;
import com.indira.usedbooks.entity.Book;
import com.indira.usedbooks.entity.User;
import okhttp3.internal.Util;

/**
 * Created by jotishsuthar on 24/04/17.
 */

public class FCMMessageService extends FirebaseMessagingService {


  @Override
  public void onMessageReceived(final RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
    if (remoteMessage.getData().size() > 0) {
      String message = remoteMessage.getData().get("message");
      String requester = remoteMessage.getData().get("requester");
      Gson gson = new Gson();
      User requesterUser = gson.fromJson(requester, User.class);
      Gson bookGson = new Gson();
      Book book = bookGson.fromJson(remoteMessage.getData().get("book"), Book.class);
      Intent myIntent = new Intent(this, ContactActivity.class);
      myIntent.putExtra("book", book);
      myIntent.putExtra("requester", requesterUser);
      PendingIntent pendingIntent = PendingIntent.getActivity(
          this,
          0,
          myIntent,
          0);
      Utils.showNotification(this, message, pendingIntent);
    }
  }
}
