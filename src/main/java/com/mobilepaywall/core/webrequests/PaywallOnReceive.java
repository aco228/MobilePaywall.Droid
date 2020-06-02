package com.mobilepaywall.core.webrequests;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by aco228 on 11/1/2016.
 */

public abstract class PaywallOnReceive {

  public void onReceiveString(String data){}
  public void onReceiveJson(JSONObject data){}
  public void onReceiveJsonArray(JSONArray data){}

  public abstract void onError(Exception e);

}
