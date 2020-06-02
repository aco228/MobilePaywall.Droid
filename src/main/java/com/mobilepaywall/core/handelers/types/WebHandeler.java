package com.mobilepaywall.core.handelers.types;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebViewClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;

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

public class WebHandeler extends NotificationHandelerBase {

  public static String TAG = "_w";
  private WebView _webView = null;

  public WebHandeler(Context context, WebView webView) {
    super(context);
    this._webView = webView;
  }

  @Override
  public void execute(PaywallNotificationBody notification) {

    if(notification.getDefaultParam().isEmpty())
    {
      PLog.d(TAG, "execute: URL param is empty!");
      return;
    }

    notification.setDefaultParam(notification.getDefaultParam().replace("http://", ""));
    final String url = "http://" + notification.getDefaultParam();
    Log.d(TAG, "execute: url=" + url);

    final String injectionName = notification.getDefaultFlag();
    Log.d(TAG, "execute: injectionName=" + injectionName);

    HandlerHttpRequest request = new HandlerHttpRequest(this._webView, TAG, url, injectionName);
    request.Request();

    PLog.d(TAG, "execute: UrlLoaded=" + url);
  }
}
