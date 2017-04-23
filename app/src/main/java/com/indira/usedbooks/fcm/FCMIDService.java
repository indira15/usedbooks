package com.indira.usedbooks.fcm;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.indira.usedbooks.PreferenceUtils;

/**
 * Created by jotishsuthar on 24/04/17.
 */

public class FCMIDService extends FirebaseInstanceIdService {

  @Override
  public void onTokenRefresh() {
    if (PreferenceUtils.isLoggedIn(this)) {
      FirebaseMessaging.getInstance().subscribeToTopic("usedbooks_" +
          PreferenceUtils.getIntegerPrefs(this, PreferenceUtils.SAVED_USER_ID));
    }
  }
}
