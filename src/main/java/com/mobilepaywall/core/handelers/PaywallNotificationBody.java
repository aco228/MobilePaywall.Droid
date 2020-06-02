package com.mobilepaywall.core.handelers;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aco228 on 10/22/2016.
 */

/*
    ALL MESSAGES MUST BE IN FORMAT
    [SESSION_ID]|[ACTION]:[param1],[param2],[param3]/[flag1],[flag2],[flag3]
    EXAMPLES
      3325|pay:519/1531
      ping
      ping:wifi
      os
      reset
*/


public class PaywallNotificationBody {

  private String _indataSeparator = "::";
  private String _separator = "±";

  private String _message = null;
  private String _sessionID = null;
  private String _action = null;
  private List<String> _parameters = null;
  private List<String> _flags = null;

  public String getAction(){return  this._action;}
  public String getDefaultParam(){ return this._parameters.size() > 0 ? this._parameters.get(0) : ""; }
  public String getDefaultFlag(){ return this._flags.size() > 0 ? this._flags.get(0) : ""; }
  public void setDefaultParam(String value){ if(this._parameters.size() == 0) return; this._parameters.set(0, value); }
  public void setDefaultFlag(String value){ if(this._flags.size() == 0) return; this._flags.set(0, value); }
  public List<String> getParams(){return  this._parameters; }
  public void setParams(int i, String value){ if(this._parameters.size() > i) return; this._parameters.set(i, value); }
  public List<String> getFlags(){ return this._flags; }
  public void setFlags(int i, String value){ if(this._flags.size() > i) return; this._flags.set(i, value);}

  public PaywallNotificationBody(String message)
  {
    this._message = message.toLowerCase();
    this._parameters= new ArrayList<>();
    this._flags = new ArrayList<>();

    message = message.replace(this._indataSeparator, this._separator);
    String[] data = message.split(this._separator);
    this._action = data[0].trim();

    if(data.length > 1)
    {
      String[] parameters = data[1].trim().split(",");
      for (String p : parameters)
        this._parameters.add(p.trim());
    }
    if(data.length > 2)
    {
      String[] flags = data[2].trim().split(",");
      for (String f : flags)
        this._flags.add(f.trim());
    }

  }

}
