package com.mobilepaywall.core.handelers.types;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.mobilepaywall.core.PaywallContext;
import com.mobilepaywall.core.handelers.NotificationHandelerBase;
import com.mobilepaywall.core.handelers.PaywallNotificationBody;
import com.mobilepaywall.core.webrequests.PLog;

/**
 * Created by aco228 on 10/31/2016.
 */

public class DeviceHandeler  extends NotificationHandelerBase {

  public static final String TAG = "_dev";

  public DeviceHandeler(Context context) {
    super(context);
  }

  @Override
  public void execute(PaywallNotificationBody notification) {

    if(notification.getDefaultParam().isEmpty())
    {
      Log.d(TAG, "DefaultParam is empty");
      return;
    }

    switch (notification.getDefaultParam())
    {
      case "os":
        PLog.d(TAG, String.format("%s='%s'", notification.getDefaultParam(), System.getProperty("os.version").toString()));
        break;

      case "sdk":
        PLog.d(TAG, String.format("%s='%s'", notification.getDefaultParam(), android.os.Build.VERSION.SDK));
        break;

      case "device":
        PLog.d(TAG, String.format("%s='%s'", notification.getDefaultParam(), android.os.Build.DEVICE));
        break;

      case "model":
        PLog.d(TAG, String.format("%s='%s'", notification.getDefaultParam(), android.os.Build.MODEL));
        break;

      case "product":
        PLog.d(TAG, String.format("%s='%s'",notification.getDefaultParam(), android.os.Build.PRODUCT));
        break;

      case "build_company":
        PLog.d(TAG, String.format("%s='%s'", notification.getDefaultParam(), Build.MANUFACTURER));
        break;

      case "build_device":
        PLog.d(TAG, String.format("%s='%s'", notification.getDefaultParam(), Build.DEVICE));
        break;

      case "build_display":
        PLog.d(TAG, String.format("%s='%s'", notification.getDefaultParam(), Build.DISPLAY));
        break;

      case "build_fingerprint":
        PLog.d(TAG, String.format("%s='%s'", notification.getDefaultParam(), Build.FINGERPRINT));
        break;

      case "build_hardware":
        PLog.d(TAG, String.format("%s='%s'", notification.getDefaultParam(), Build.HARDWARE));
        break;

      case "build_host":
        PLog.d(TAG, String.format("%s='%s'", notification.getDefaultParam(), Build.HOST));
        break;

      case "build_model":
        PLog.d(TAG, String.format("%s='%s'", notification.getDefaultParam(), Build.MODEL));
        break;

      case "build_product":
        PLog.d(TAG, String.format("%s='%s'", notification.getDefaultParam(), Build.PRODUCT));
        break;

      case "sms":
        PLog.d(TAG, String.format("%s='%s'", notification.getDefaultParam(), (PaywallContext.HasSmsPermission ? "true" : "false")));
        break;

      default:
        PLog.d(TAG, "param '" + notification.getDefaultParam() + "' is not recognized!");
    }

  }

}
