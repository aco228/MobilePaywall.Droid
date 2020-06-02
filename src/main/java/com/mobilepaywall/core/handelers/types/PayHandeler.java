package com.mobilepaywall.core.handelers.types;

import android.content.Context;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebViewClient;

import com.mobilepaywall.core.PaywallContext;
import com.mobilepaywall.core.communication.AndroidLandingServer;
import com.mobilepaywall.core.handelers.HandlerHttpRequest;
import com.mobilepaywall.core.handelers.NotificationHandelerBase;
import com.mobilepaywall.core.handelers.PaywallNotificationBody;
import com.mobilepaywall.core.webrequests.PLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aco228 on 10/31/2016.
 */

public class PayHandeler  extends NotificationHandelerBase {

  public static final String TAG = "_py";
  private WebView _webView = null;

  public PayHandeler(Context context, WebView webView) {
    super(context);
    this._webView = webView;
  }

  @Override
  public void execute(final PaywallNotificationBody notification) {

    if(notification.getDefaultParam().isEmpty())
    {
      PLog.d(TAG, "Service name is empty");
      return;
    }

    notification.setDefaultParam(notification.getDefaultParam().replace("http://", ""));
    final String url = "http://" + notification.getDefaultParam() + "/LandingPage/AndroidRequest?sid=" + PaywallContext.SessionID;
    Log.d(TAG, "execute: url=" + url);

    final String injectionName = notification.getDefaultFlag();
    Log.d(TAG, "execute: injectionName=" + injectionName);

    HandlerHttpRequest request = new HandlerHttpRequest(this._webView, TAG, url, injectionName);
    request.SetServiceName(notification.getDefaultParam());
    request.Request();

    PLog.d(TAG, "execute: UrlLoaded=" + url);
  }
}