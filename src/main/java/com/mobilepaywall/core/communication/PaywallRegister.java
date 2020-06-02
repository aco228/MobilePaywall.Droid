package com.mobilepaywall.core.communication;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import org.json.JSONObject;

import com.mobilepaywall.core.webrequests.*;
import com.mobilepaywall.core.PaywallContext;

/**
 * Created by aco228 on 10/31/2016.
 */

public class PaywallRegister {

  public static String TAG = "PaywallRegister";

  public static void execute(final SharedPreferences sharedPreferences, final PaywallRegisterCallback callback) {

    Log.d(TAG, "execute: start");

    PaywalRequestData postData = new PaywalRequestData("POST", "Entrance", "Register");
    postData.addData("applicationID", Integer.toString(PaywallContext.ApplicationID));
    postData.addData("androidUniqueID", PaywallContext.UniqueID);
    postData.addData("tokenID", PaywallContext.TokenID);
    postData.addData("osVersion", System.getProperty("os.version"));
    postData.addData("versionSdk", android.os.Build.VERSION.SDK);
    postData.addData("device", android.os.Build.DEVICE);
    postData.addData("company", Build.MANUFACTURER);
    postData.addData("model", android.os.Build.MODEL);
    postData.addData("product", android.os.Build.PRODUCT);
    postData.addData("msisdn", PaywallContext.Msisdn);
    postData.addData("referrer", sharedPreferences.getString(PaywallContext.PreferencesReferrerValue, ""));
    postData.addData("hasSmsPermission", (PaywallContext.HasSmsPermission ? "true" : "false"));
    postData.setReceiver(new PaywallOnReceive() {

      @Override
      public void onReceiveJson(JSONObject data)
      {
        if(data == null)
        {
          Log.d(TAG, "onReceiveJson: Json data is null");
          return;
        }

        boolean status = false;
        String sessionID = null;

        try
        {
          status = data.getBoolean("status");
          sessionID = data.getString("sessionID");
        }
        catch (Exception e)
        {
          Log.d(TAG, "onReceiveJson: Json could not parse status, sessionID");
          return;
        }

        if(!status)
        {
          Log.d(TAG, "onReceiveJson: Status is false from paywall");
          return;
        }

        Log.d(TAG, "execute: SessionID = " + sessionID);
        PaywallContext.SessionID = sessionID;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PaywallContext.PreferenceSessionID, sessionID);
        editor.apply();

        sharedPreferences.edit().putBoolean(PaywallContext.PreferencesIsRegistrationProcessEnded, true).apply();
        PaywallContext.IsRegistrationProcessEnded = true;

        // SUMMARY:
        // IMPORTANT: In case that sync process is waiting.. here we must finish it
        if(sharedPreferences.getBoolean(PaywallContext.PreferencesIsSyncProcessWaiting, false))
          PaywallRegister.syncToken(sharedPreferences);

        // SUMMARY:
        // IMPORTANT: In case that sync referrer process is waiting, finish it here
        if(sharedPreferences.getBoolean(PaywallContext.PreferencesIsReferrerSyncdWaiting, false))
          PaywallRegister.syncReferrer(sharedPreferences);

        Log.d(TAG, "execute: end");
        callback.onSuccess();
      }

      @Override
      public void onError(Exception e)
      {
        callback.onError();
      }
    });

    PaywallWebRequest.getJsonObject(postData);
  }

  // SUMMARY: General function for sync token for Firebase
  public static void syncToken(final SharedPreferences sharedPreferences)
  {
    // SUMMARY: In case that registration process is not finished
    boolean isRegistrationProcessFinished = sharedPreferences.getBoolean(PaywallContext.PreferencesIsRegistrationProcessEnded, true);
    if(!isRegistrationProcessFinished)
    {
      sharedPreferences.edit().putBoolean(PaywallContext.PreferencesIsSyncProcessWaiting, true).apply();
      PaywallContext.IsSyncProcessWaiting = true;
      return;
    }

    sharedPreferences.edit().putBoolean(PaywallContext.PreferencesIsSyncProcessWaiting, false).apply();
    PaywallContext.IsSyncProcessWaiting = false;

    String token = sharedPreferences.getString(PaywallContext.PreferenceTokenID, "");
    PaywalRequestData postData = new PaywalRequestData("POST", "Entrance", "SyncToken");
    postData.addData("sessionID", PaywallContext.SessionID);
    postData.addData("applicationID", Integer.toString(PaywallContext.ApplicationID));
    postData.addData("androidUniqueID", PaywallContext.UniqueID);
    postData.addData("tokenID", token);
    PaywallWebRequest.getString(postData);
  }

  // SUMMARY: General function for sync referrer we get from receiver
  public static void syncReferrer(final SharedPreferences sharedPreferences)
  {
    // SUMMARY: In case that registration step is not done
    boolean isReferrerSyncd = sharedPreferences.getBoolean(PaywallContext.PreferencesIsReferrerSyncdWaiting, true);
    if(!isReferrerSyncd)
    {
      sharedPreferences.edit().putBoolean(PaywallContext.PreferencesIsReferrerSyncdWaiting, true).apply();
      PaywallContext.IsReferrerSyncWaiting = true;
      return;
    }

    sharedPreferences.edit().putBoolean(PaywallContext.PreferencesIsReferrerSyncdWaiting, false).apply();
    PaywallContext.IsReferrerSyncWaiting = false;

    String referer = sharedPreferences.getString(PaywallContext.PreferencesReferrerValue, "");
    PaywalRequestData postData = new PaywalRequestData("POST", "Entrance", "SyncReferrer");
    postData.addData("sessionID", PaywallContext.SessionID);
    postData.addData("applicationID", Integer.toString(PaywallContext.ApplicationID));
    postData.addData("androidUniqueID", PaywallContext.UniqueID);
    postData.addData("referrer", referer);
    PaywallWebRequest.getString(postData);
  }

  public static void reportPremiumSmsRequest(final SharedPreferences sharedPreferences, String psmsRequestID)
  {
    PaywalRequestData postData = new PaywalRequestData("POST", "Entrance", "ReportPremiumSms");
    postData.addData("psmsRequestID", psmsRequestID);
    PaywallWebRequest.getString(postData);
  }

}