package com.mobilepaywall.core.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.mobilepaywall.core.PaywallContext;
import com.mobilepaywall.core.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by aco228 on 11/1/2016.
 */

public abstract class PaywallActivity extends AppCompatActivity {

  public static final String TAG = "PActivityBase";
  protected PaywallApplicationBase _application = null;
  protected WebView _webView = null;

  public abstract void onPaywallSetApplication();
  public abstract void onPaywallStart();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.onPaywallSetApplication();

    if(this._application == null)
    {
      this.defaultCloseApplication("Default paywall application is not defined");
      return;
    }

    this._application.setActivity(this);
    this._application.initialize();
  }

  public void defaultCloseApplication(String error)
  {
    Log.d(TAG, "onCreate: INIT PAYWALL ERROR");
    Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        finish();
        System.exit(1);
      }
    }, 3500);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = this.getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int i = item.getItemId();
    if (i == R.id.btnGetInfo)
    {
      Toast.makeText(this, PaywallContext.getBasicInfo(), Toast.LENGTH_LONG).show();
      return true;
    }
    else
      return super.onOptionsItemSelected(item);
  }
}
