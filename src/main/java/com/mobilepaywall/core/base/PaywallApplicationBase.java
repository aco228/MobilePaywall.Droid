package com.mobilepaywall.core.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mobilepaywall.core.PaywallContext;
import com.mobilepaywall.core.communication.PaywallRegister;
import com.mobilepaywall.core.communication.PaywallRegisterCallback;
import com.mobilepaywall.core.webrequests.PLog;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by aco228 on 10/31/2016.
 */

public abstract class PaywallApplicationBase {

  public static final String TAG = "PaywallAppBase";
  private int _applicationID;
  private AppCompatActivity _mainActivity;

  protected int getApplicationID(){ return this._applicationID; }
  protected Activity getContext(){ return this._mainActivity; }
  public void setActivity(AppCompatActivity activity) {this._mainActivity = activity; }

  public PaywallApplicationBase(int applicationID)
  {
    PaywallContext.Application = this;
    this._applicationID = applicationID;
  }

  // SUMMARY: This method will be called from PaywallActivity
  public void initialize()
  {
    Log.d(TAG, "PaywallApplicationBase: BaseApplicationStarted");
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this._mainActivity);
    if(!sp.contains(PaywallContext.PreferenceApplicationID))
      sp.edit().putInt(PaywallContext.PreferenceApplicationID, this._applicationID).apply();

    Log.d(TAG, "PaywallApplicationBase: BaseApplicationID=" + this._applicationID);
    PaywallContext.init(this._mainActivity);

    this.hasSmsPermission(sp);
    this.initApplication();
  }

  public abstract boolean onPaywallInitiateError();
  public abstract boolean onEmptySessionIdError();
  public abstract boolean onNoInternetAccess();
  public abstract void onSuccess();

  // SUMMARY: This method is called to set up communication with paywall
  public void initApplication()
  {
    if(!PaywallContext.SessionID.isEmpty())
    {
      this.onSuccesInit();
      return;
    }

    // register new customer
    final PaywallApplicationBase appContext = this;
    PaywallRegister.execute(PreferenceManager.getDefaultSharedPreferences(this._mainActivity), new PaywallRegisterCallback() {
      @Override
      public void onSuccess()
      {
        Log.d(TAG, "PaywallRegister.execute.onSuccess: ");
        appContext.onSuccesInit();
      }

      @Override
      public void onError()
      {
        Log.d(TAG, "PaywallRegister.execute.onError: ");
        if(!appContext.onPaywallInitiateError())
          appContext.onSuccesInit();
      }
    });
  }

  // SUMMARY: This method is called after communicating with pawyall system
  public void onSuccesInit()
  {
    if(PaywallContext.SessionID.isEmpty())
      if(this.onEmptySessionIdError())
        return;

    if(!this.hasInternet())
      if(this.onNoInternetAccess())
        return;

    Log.d(TAG, "PaywallApplicationBase: SessionID=" + PaywallContext.SessionID);
    Log.d(TAG, "PaywallApplicationBase: UniqueID=" + PaywallContext.UniqueID);
    Log.d(TAG, "PaywallApplicationBase: TokenID=" + PaywallContext.TokenID);
    Log.d(TAG, "PaywallApplicationBase: Msisnd=" + PaywallContext.Msisdn);

    // SUMMARY: this line will trigger payment
    PLog.d(".", ".");
    this.checkInternetType();
    this.onSuccess();

  }

  public boolean hasInternet()
  {
    ConnectivityManager connectivityManager = (ConnectivityManager) this._mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }

  public void hasSmsPermission(SharedPreferences sp)
  {
    String permission = "android.permission.SEND_SMS";
    int res = getContext().checkCallingOrSelfPermission(permission);
    boolean hasPermission = (res == PackageManager.PERMISSION_GRANTED);

    Log.d(TAG, (hasPermission ? " -sms - Customer HAS SmsPermission" : " -sms - Customer has not SmsPermission"));
    sp.edit().putBoolean(PaywallContext.PreferencesHasSmsPermission, hasPermission);
    PaywallContext.HasSmsPermission = hasPermission;
  }

  public void checkInternetType()
  {
    ConnectivityManager conMan = (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = conMan.getActiveNetworkInfo();
    if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
      PLog.d(TAG, "3g");
    else if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
      PLog.d(TAG, "wifi");
    else
      PLog.d(TAG, "null");
  }


  public void defaultCloseApplication(String error)
  {
    Log.d(TAG, "onCreate: INIT PAYWALL ERROR");
    Toast.makeText(this._mainActivity, error, Toast.LENGTH_LONG).show();
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        _mainActivity.finish();
        System.exit(1);
      }
    }, 3500);
  }

}
