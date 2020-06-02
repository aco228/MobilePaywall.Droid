package com.mobilepaywall.core.handelers.types;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mobilepaywall.core.handelers.NotificationHandelerBase;
import com.mobilepaywall.core.handelers.PaywallNotificationBody;
import com.mobilepaywall.core.webrequests.PLog;

/**
 * Created by aco228 on 10/31/2016.
 */

public class PingHandeler extends NotificationHandelerBase {

  public static String TAG = "_p";

  private NetworkInfo _networkInfo = null;

  public PingHandeler(Context context)
  {
    super(context);
  }

  @Override
  public void execute(PaywallNotificationBody notification) {

    if(notification.getDefaultParam().isEmpty())
    {
      PLog.d(TAG, ".");
      return;
    }

    NetworkInfo activeNetwork = this.getNetworkInfo();

    switch (notification.getDefaultParam())
    {
      case "pp":
        PLog.d(TAG, "..");
        break;
      case "net":
        if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
          PLog.d(TAG, "3g");
        else if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
          PLog.d(TAG, "wifi");
        else
          PLog.d(TAG, "null");
        break;

      case "wifi":
        PLog.d(TAG, "wifi=" + ((activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) ? "true" : "false"));
        break;

      case "3g":
        PLog.d(TAG, "3g=" + ((activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) ? "true" : "false"));
        break;

      default:
        PLog.d(TAG, "param '" + notification.getDefaultParam() + "' is not recognized!");
        break;

    }
  }

  private NetworkInfo getNetworkInfo()
  {
    if(this._networkInfo != null)
      return this._networkInfo;

    ConnectivityManager conMan = (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = conMan.getActiveNetworkInfo();
    if(activeNetwork == null)
    {
      PLog.d(TAG, "activeNetwork is NULL, strange!");
      return null;
    }

    this._networkInfo = activeNetwork;
    return this._networkInfo;
  }



}