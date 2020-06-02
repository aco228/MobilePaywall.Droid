package com.mobilepaywall.core.webrequests;

import android.content.SharedPreferences;
import android.util.Log;

import com.mobilepaywall.core.PaywallContext;

/**
 * Created by aco228 on 10/31/2016.
 */

public class PLog {
  public static String TAG = "PLog";

  public static void Log(String sessionID, String tag, String text)
  {
    PaywalRequestData postData = new PaywalRequestData("POST", "Logger", "Index");
    postData.addData("applicationID", Integer.toString(PaywallContext.ApplicationID));
    postData.addData("sessionID", sessionID);
    postData.addData("tag", tag);
    postData.addData("text", text);
    PaywallWebRequest.getString(postData);
  }

  public static void d(String tag, String text)
  {
    Log.d(tag, text);
    String sessionID = PaywallContext.SessionID;
    PLog.Log(sessionID, tag, text);
  }

  public static void s(String tag, String text)
  {
    Log.d(tag, text);
    String sessionID = "0";
    PLog.Log(sessionID, tag, text);
  }
}