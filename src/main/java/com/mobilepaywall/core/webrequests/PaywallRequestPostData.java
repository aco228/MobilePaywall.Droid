package com.mobilepaywall.core.webrequests;

/**
 * Created by aco228 on 10/31/2016.
 */

public class PaywallRequestPostData {
  private String _key = null;
  private String _value = null;

  public String getKey(){ return this._key != null ? this._key : "";}
  public String getValue(){ return this._value != null ? this._value : ""; }

  public PaywallRequestPostData(String key, String value)
  {
    this._key = key;
    this._value = value;
  }
}