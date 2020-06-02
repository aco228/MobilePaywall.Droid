package com.mobilepaywall.core.webrequests;

import com.mobilepaywall.core.PaywallContext;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aco228 on 10/31/2016.
 */

public class PaywalRequestData  {
  private String _action = "";
  private String _controller = "";
  private String _getArguments = "";
  private String _method = "POST";
  private String _url = "";
  private PaywallOnReceive _receiver = null;
  private List<PaywallRequestPostData> _postData = null;

  public String getMethod(){ return this._method; }
  public String getAction() {return this._action;}
  public String getController() {return this._controller;}
  public String getGetArguments() { return  this._getArguments; }
  public String getAbsoluthUrl(){ return this._url;}
  public PaywallOnReceive getReceiver(){ return this._receiver; }
  public void setMethod(String method){ this._method= method;}
  public void addData(String key, String value){ this._postData.add(new PaywallRequestPostData(key, value)); }
  public void setAction(String action){ this._action= action;}
  public void setController(String controller){ this._controller= controller;}
  public void setGetArguments(String getArguments){ this._getArguments = getArguments; }
  public void setAbsoluthPath(String url){ this._url = url; }
  public void setReceiver(PaywallOnReceive receiver){ this._receiver = receiver; }

  public String getUrl()
  {
    return (this._url.isEmpty() ? PaywallContext.ServerMPUrl: this._url) + "/" + this._controller + "/" + this._action + this._getArguments;
  }


  public PaywalRequestData()
  {
    this._postData = new ArrayList<>();
  }

  public PaywalRequestData(String controller, String action)
  {
    this();
    this._postData = new ArrayList<>();
    this._action = action;
    this._controller = controller;
  }

  public PaywalRequestData(String method, String action, String controller)
  {
    this(action, controller);
    this._method = method;
  }

  public String getQuery() throws UnsupportedEncodingException
  {
    StringBuilder result = new StringBuilder();
    boolean first = true;

    if(this._postData == null || this._postData.size() == 0)
      return "";

    for(PaywallRequestPostData entry : this._postData)
    {
      if(first) first = false;
      else result.append("&");

      result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
      result.append("=");
      result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
    }
    return result.toString();
  }

}
