package com.mobilepaywall.core.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.mobilepaywall.core.webrequests.PLog;

/**
 * Created by aco228 on 10/31/2016.
 */

public class InternetConnectionReceiver extends BroadcastReceiver {

  public static final String TAG = "NETConnRecevicr";
  public static final String PTAG = ".";

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.d("app", "Network connectivity change");

    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    boolean hasInternet = activeNetworkInfo != null && activeNetworkInfo.isConnected();
    if(!hasInternet)
      return;

    PLog.d(".", ".");
  }

}
