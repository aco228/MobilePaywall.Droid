package com.mobilepaywall.core.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mobilepaywall.core.PaywallContext;
import com.mobilepaywall.core.communication.PaywallRegister;

/**
 * Created by ako on 6.2.2017..
 */

public class InstallReferrerReceiver extends BroadcastReceiver {

  public static final String TAG = "InstallReferrer";

  @Override
  public void onReceive(Context context, Intent intent) {
    String referrer = intent.getStringExtra("referrer");

    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    sp.edit().putString(PaywallContext.PreferencesReferrerValue, referrer).apply();

    PaywallRegister.syncReferrer(sp);
  }
}
