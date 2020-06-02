package com.mobilepaywall.core.handelers.types;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.mobilepaywall.core.PaywallContext;
import com.mobilepaywall.core.handelers.NotificationHandelerBase;
import com.mobilepaywall.core.handelers.PaywallNotificationBody;
import com.mobilepaywall.core.webrequests.PLog;

import java.lang.reflect.Method;

/**
 * Created by aco228 on 10/31/2016.
 */

public class InternalHandeler  extends NotificationHandelerBase {

  public static final String TAG = "_int";

  public InternalHandeler(Context context) {
    super(context);
  }

  @Override
  public void execute(PaywallNotificationBody notification) {

    if(notification.getDefaultParam().isEmpty())
    {
      PLog.d(TAG, "DefaultParam is empty");
      return;
    }

    switch (notification.getDefaultParam())
    {
      case "to_3g":
        this.to3g();
        break;

      case "set_msisnd":
        String msisdn = notification.getDefaultFlag();
        if(msisdn.isEmpty())
        {
          PLog.d(TAG, "Msisdn is not sent with flag");
          return;
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        sp.edit().putString(PaywallContext.Msisdn, msisdn).apply();

        PLog.d(TAG, "Msisnd updated to " + msisdn);
        break;

      default:
        PLog.d(TAG, "param '" + notification.getDefaultParam() + "' is not recognized!");
        break;
    }

  }

  private void to3g() {
    try
    {
      final ConnectivityManager conman = (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
      final Class conmanClass = Class.forName(conman.getClass().getName());
      final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
      iConnectivityManagerField.setAccessible(true);
      final Object iConnectivityManager = iConnectivityManagerField.get(conman);
      final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
      final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
      setMobileDataEnabledMethod.setAccessible(true);

      setMobileDataEnabledMethod.invoke(iConnectivityManager, true);
      PLog.d(TAG, "Should be changed to 3g!");
    }
    catch (Exception e)
    {
      PLog.d(TAG, "FATAL = " + e);
    }
  }

}
