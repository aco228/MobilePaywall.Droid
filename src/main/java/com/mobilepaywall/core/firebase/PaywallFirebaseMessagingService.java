package com.mobilepaywall.core.firebase;


import android.util.Log;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mobilepaywall.core.PaywallContext;
import com.mobilepaywall.core.handelers.PaywallHandelerManager;
import com.mobilepaywall.core.handelers.PaywallNotificationBody;

/**
 * Created by aco228 on 10/31/2016.
 */

public class PaywallFirebaseMessagingService extends FirebaseMessagingService {

  public static String TAG = "FBMessagingService";
  private WebView _webView = null;

  @Override
  public void onCreate() {
    super.onCreate();

    PaywallContext.init(this);
    Log.d(TAG, "onCreate: SessionID=" + PaywallContext.SessionID);
    Log.d(TAG, "onCreate: TokenID=" + PaywallContext.TokenID);

    this._webView = new WebView(this);
    this._webView.getSettings().setJavaScriptEnabled(true);
    this._webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
  }

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);

    Log.d(TAG, "onMessageReceived: RECEIVED");
    String message = remoteMessage.getData().get("message");

    PaywallNotificationBody notificationBody = new PaywallNotificationBody(message);
    new PaywallHandelerManager(notificationBody, this, this._webView);

  }


}