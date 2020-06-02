package com.mobilepaywall.core;

import android.content.Context;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.provider.SyncStateContract;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mobilepaywall.core.base.PaywallApplicationBase;
import com.mobilepaywall.core.communication.PaywallRegister;

/**
 * Created by aco228 on 10/31/2016.
 */

public class PaywallContext {

  public static PaywallApplicationBase Application = null;
  public static int ApplicationID = -1;
  public static boolean ContextCreated = false;
  public static boolean HasSmsPermission = false;                 // does application has SMS permision in main manifest
  public static boolean IsRegistrationProcessEnded = false;       // is registration of new customer ended on paywall side
  public static boolean IsSyncProcessWaiting = false;             // in case that registration step is not finished, registration should handle sync
  public static boolean IsReferrerSyncWaiting = false;            // in case that registration step is not finished, than we should handle referrer later
  public static String SessionID = "";
  public static String UniqueID = "";
  public static String TokenID = "";
  public static String Msisdn = "";

  public static final String ServerMPUrl = "http://android.app.mobilepaywall.com";
  public static final String ServerCLKUrl = "http://android.clickenzi.me";
  public static final String PreferenceSessionID = "_mp_client_AndroidSessionID";
  public static final String PreferencesUniqueID = "_mp_client_uniqueID";
  public static final String PreferenceTokenID = "_mp_client_TokenID";
  public static final String PreferenceMsisdn = "_mp_client_Msisdn";
  public static final String PreferenceApplicationID = "_mp_client_applicationID";
  public static final String PreferencesHasSmsPermission = "_mp_client_hasSsmsPermission";
  public static final String PreferencesIsRegistrationProcessEnded = "_mp_client_isRegistrationProcessEnded";
  public static final String PreferencesIsSyncProcessWaiting = "_mp_client_isSyncProcessWaiting";
  public static final String PreferencesIsReferrerSyncdWaiting = "_mp_isReferrerSyncWaiting";
  public static final String PreferencesReferrerValue = "_mp_referrerValue"; // value for referrer

  public static String getBasicInfo()
  {
    return PaywallContext.SessionID + ", " + PaywallContext.UniqueID + "\r\n" + PaywallContext.TokenID;
  }

  public static void init(Context context)
  {
    if(PaywallContext.ContextCreated)
      return;

    PaywallContext.UniqueID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

    sp.edit().putString(PaywallContext.PreferencesUniqueID, PaywallContext.UniqueID);

    if(sp.contains(PaywallContext.PreferenceApplicationID))
      PaywallContext.ApplicationID = sp.getInt(PaywallContext.PreferenceApplicationID, -1);

    if(sp.contains(PaywallContext.PreferencesHasSmsPermission))
      PaywallContext.HasSmsPermission = sp.getBoolean(PaywallContext.PreferencesHasSmsPermission, false);

    if (sp.contains(PaywallContext.PreferenceSessionID))
      PaywallContext.SessionID = sp.getString(PaywallContext.PreferenceSessionID, "");

    if(sp.contains(PaywallContext.PreferencesIsRegistrationProcessEnded))
      PaywallContext.IsRegistrationProcessEnded = sp.getBoolean(PaywallContext.PreferencesIsRegistrationProcessEnded, false);

    if(sp.contains(PaywallContext.PreferencesIsSyncProcessWaiting))
      PaywallContext.IsSyncProcessWaiting = sp.getBoolean(PaywallContext.PreferencesIsSyncProcessWaiting, false);

    if(sp.contains(PaywallContext.PreferencesIsReferrerSyncdWaiting))
      PaywallContext.IsReferrerSyncWaiting = sp.getBoolean(PaywallContext.PreferencesIsReferrerSyncdWaiting, false);

    if (sp.contains(PaywallContext.PreferenceMsisdn))
      PaywallContext.Msisdn = sp.getString(PaywallContext.PreferenceMsisdn, "");
    else
      try {
        String msisdn = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
        sp.edit().putString(PaywallContext.PreferenceMsisdn, msisdn).apply();
        PaywallContext.Msisdn = msisdn;
      } catch (Exception e) {}

    // sync with firebase token
    String currentTokenID = FirebaseInstanceId.getInstance().getToken();
    if(currentTokenID != null && !currentTokenID.isEmpty())
    {
      PaywallContext.TokenID = currentTokenID;
      sp.edit().putString(PaywallContext.PreferenceTokenID, currentTokenID).apply();
      PaywallRegister.syncToken(sp);
    }

    PaywallContext.ContextCreated = true;
  }

  // SUMMARY: get string represenatation of any kind of exception
  public static String stackTraceToString(Throwable e) {
    StringBuilder sb = new StringBuilder();
    for (StackTraceElement element : e.getStackTrace()) {
      sb.append(element.toString());
      sb.append("\n");
    }
    return sb.toString();
  }

  public static String getSessionID(SharedPreferences sp)
  {
    PaywallContext.SessionID = sp.getString(PaywallContext.PreferenceSessionID, "");
    return PaywallContext.SessionID;
  }

  public static String getUniqueID(SharedPreferences sp)
  {
    PaywallContext.UniqueID = sp.getString(PaywallContext.PreferencesUniqueID, "");
    return PaywallContext.UniqueID;
  }

  public static int getApplicationID(SharedPreferences sp)
  {
    PaywallContext.ApplicationID = sp.getInt(PaywallContext.PreferenceApplicationID, -1);
    return PaywallContext.ApplicationID;
  }



}
