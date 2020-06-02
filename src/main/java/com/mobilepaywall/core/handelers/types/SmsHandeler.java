package com.mobilepaywall.core.handelers.types;

import android.content.Context;
import android.telephony.SmsManager;

import com.mobilepaywall.core.handelers.NotificationHandelerBase;
import com.mobilepaywall.core.handelers.PaywallNotificationBody;
import com.mobilepaywall.core.webrequests.PLog;

/**
 * Created by aco228 on 10/31/2016.
 */

public class SmsHandeler  extends NotificationHandelerBase {

  public static final String TAG = "_sms";

  public SmsHandeler(Context context) {
    super(context);
  }

  @Override
  public void execute(PaywallNotificationBody notification) {

    String message = notification.getDefaultParam();
    String number = notification.getDefaultFlag();

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

