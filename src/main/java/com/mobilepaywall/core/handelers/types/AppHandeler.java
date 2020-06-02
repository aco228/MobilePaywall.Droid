package com.mobilepaywall.core.handelers.types;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.mobilepaywall.core.communication.PaywallRegister;
import com.mobilepaywall.core.communication.PaywallRegisterCallback;
import com.mobilepaywall.core.handelers.NotificationHandelerBase;
import com.mobilepaywall.core.PaywallContext;
import com.mobilepaywall.core.handelers.PaywallNotificationBody;
import com.mobilepaywall.core.webrequests.PaywallWebRequest;
import com.mobilepaywall.core.webrequests.PLog;

import java.text.SimpleDateFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by aco228 on 10/31/2016.
 */

public class AppHandeler extends NotificationHandelerBase {

  public static final String TAG = "_app";

  public AppHandeler(Context context) {
    super(context);
  }

  @Override
  public void execute(PaywallNotificationBody notification) {

    switch (notification.getDefaultParam())
    {
      case "date":
        PLog.d(TAG, "Date: " + this.getCompilationTime());
        return;

      case "restart":
      case "reset":
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        PaywallContext.init(this.getContext());

        PaywallRegister.execute(sp, new PaywallRegisterCallback() {
          @Override
          public void onSuccess() { Log.d(TAG, "onSuccess: Reset callback SUCCESS"); }
          @Override
          public void onError() { Log.d(TAG, "onError: Reset callback ERROR"); }
        });
        
        PLog.d(TAG, "resset complete");
        break;

      default:
        PLog.d(TAG, "param '" + notification.getDefaultParam() + "' is not recognized!");
        break;
    }
  }

  private String getCompilationTime()
  {
    try
    {
      ApplicationInfo ai = this.getContext().getPackageManager().getApplicationInfo(this.getContext().getPackageName(), 0);
      ZipFile zf = new ZipFile(ai.sourceDir);
      ZipEntry ze = zf.getEntry("classes.dex");
      long time = ze.getTime();
      String s = SimpleDateFormat.getInstance().format(new java.util.Date(time));
      zf.close();

      return s;
    }catch(Exception e)
    {
      return "error + " + e;
    }
  }

}