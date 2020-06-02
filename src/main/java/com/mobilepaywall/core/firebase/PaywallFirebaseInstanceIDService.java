package com.mobilepaywall.core.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mobilepaywall.core.PaywallContext;
import com.mobilepaywall.core.communication.PaywallRegister;
import com.mobilepaywall.core.webrequests.PLog;

/**
 * Created by aco228 on 10/31/2016.
 */

public class PaywallFirebaseInstanceIDService  extends FirebaseInstanceIdService {

  public static String TAG = "FBInstanceIDService";

  @Override
  public void onTokenRefresh() {

    Log.d(TAG, "onTokenRefresh: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Inside");
    PaywallContext.init(this);

    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    PaywallContext.TokenID = refreshedToken;

    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
    sp.edit().putString(PaywallContext.PreferenceTokenID, refreshedToken).apply();
    PaywallRegister.syncToken(sp);

    PLog.d(TAG, "onTokenRefresh: token refreshed.");
    PLog.d(".", ".");

    Log.d(TAG, "onTokenRefresh:  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  " + refreshedToken);
  }
}