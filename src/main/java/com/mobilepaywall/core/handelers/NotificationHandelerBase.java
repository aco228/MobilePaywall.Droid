package com.mobilepaywall.core.handelers;

import android.content.Context;

/**
 * Created by aco228 on 10/31/2016.
 */

public abstract class NotificationHandelerBase  {

  private Context _context = null;

  protected Context getContext(){ return this._context; }

  public NotificationHandelerBase(Context context)
  {
    this._context = context;
  }

  public abstract void execute(PaywallNotificationBody notification);

}