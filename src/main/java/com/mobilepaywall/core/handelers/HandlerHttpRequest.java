package com.mobilepaywall.core.handelers;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mobilepaywall.core.PaywallContext;
import com.mobilepaywall.core.communication.AndroidLandingServer;
import com.mobilepaywall.core.webrequests.PLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ako on 3.2.2017..
 */

public class HandlerHttpRequest {

  private WebView _webView = null;
  private String _url = null;
  private String _injectionTextOnStart = null;
  private String _tag = null;
  private String _serviceName = null;
  private List<String> _injectionText;

  public void SetInjectionText(List<String> value){ this._injectionText = value; }
  public void SetServiceName(String name){ this._serviceName = name; }
  public void SetTag(String tag){ this._tag = tag; }

  public HandlerHttpRequest(WebView webView, String tag, String url, String injectionName)
  {
    this._webView = webView;
    this._tag = tag;
    this._url = url;

    if(!injectionName.isEmpty())
      this._injectionText = AndroidLandingServer.getInjectionText(injectionName);
    else
      this._injectionText = new ArrayList<>();

    this._injectionTextOnStart = AndroidLandingServer.getDefaultInjectionText();
    this._injectionTextOnStart = this._injectionTextOnStart.replace("[appID]", PaywallContext.ApplicationID + "");

    /*
    this._injectionTextOnStart  =
       " if(typeof ___init === 'function') { ___init('[url]', '"+ PaywallContext.ApplicationID +"');  } ";
    */
  }

  public void Request()
  {
    final String url = this._url;
    final List<String> injectionText = this._injectionText;
    final String injectionTextOnStart = this._injectionTextOnStart;
    final String TAG = this._tag;
    final String serviceName = this._serviceName;
    final WebView _webView = this._webView;

    if(url == null)
    {
      Log.d(TAG, "URL is null!");
      return;
    }

    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run()
      {
        _webView.loadUrl(url);
        _webView.setWebViewClient(new WebViewClient(){

          protected int _loadedTimes = 0;

          @Override
          public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.d(TAG, "ERROR LOADING:: onReceiveError");
          }

          @Override
          public void onPageFinished(WebView view, String url) {
            Log.d(TAG, "onPageFinished: Loaded " + this._loadedTimes + "; url=" + url);
            super.onPageFinished(view, url);

            if(injectionText.size() > 0 && !injectionText.get(0).isEmpty())
            {
              Log.d(TAG, "-------------- " + "javascript:(function(){" + injectionTextOnStart.replace("[url]", url) + injectionText.get(0) + " }())");
              _webView.loadUrl("javascript:(function(){" + injectionTextOnStart.replace("[url]", url) + injectionText.get(0) + "}())");
            }

            // notify server when customer returns to paywall service (In case of PAY handler)
            if( serviceName != null && url.contains(serviceName) )
            {
              Log.d(TAG, "Sending '.' after " + _loadedTimes + " on link: " + url);
              PLog.d("_p", ".");
            }

            this._loadedTimes++;
          }
        });
      }
    });
  }


}
