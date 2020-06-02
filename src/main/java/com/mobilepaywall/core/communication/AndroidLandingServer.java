package com.mobilepaywall.core.communication;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import com.mobilepaywall.core.PaywallContext;
import com.mobilepaywall.core.webrequests.PaywalRequestData;
import com.mobilepaywall.core.webrequests.PaywallWebRequest;

/**
 * Created by aco228 on 10/31/2016.
 */

public class AndroidLandingServer {

  public static final String TAG = "AndroidLandingServer";
  private static final String Separator = "//-sep-//";

  public static String getDefaultInjectionText()
  {
    PaywalRequestData sendData = new PaywalRequestData();
    sendData.setMethod("GET");
    sendData.setAbsoluthPath(PaywallContext.ServerCLKUrl);
    sendData.setController("_in");
    sendData.setAction("_def.js");
    String js = PaywallWebRequest.getSyncString(sendData);

    if(js == null)
    {
      Log.d(TAG, "getInjectionText: Could not load default js");
      return " if(typeof ___init === 'function') { ___init('[url]', '[appID]');  } ";
    }

    return js;
  }

  public static List<String> getInjectionText(String injectionName)
  {
    PaywalRequestData sendData = new PaywalRequestData();
    sendData.setMethod("GET");
    sendData.setAbsoluthPath(PaywallContext.ServerCLKUrl);
    sendData.setController("_in");
    sendData.setAction(injectionName + "/_.js");
    String js = PaywallWebRequest.getSyncString(sendData);

    if(js == null)
    {
      Log.d(TAG, "getInjectionText: Emptry array is returned due the .js is NULL");
      return new ArrayList<>();
    }

    List<String> result = new ArrayList<>();
    String[] data = js.split(AndroidLandingServer.Separator);
    for (String entry : data)
      result.add(entry.trim());

    return result;
  }
}
