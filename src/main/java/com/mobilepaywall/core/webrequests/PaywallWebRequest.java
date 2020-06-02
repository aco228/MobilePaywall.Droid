package com.mobilepaywall.core.webrequests;

import android.util.Log;

import com.mobilepaywall.core.PaywallContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by aco228 on 10/31/2016.
 */

public class PaywallWebRequest {
  public static final String TAG = "PaywallWebRequest";

  public static String run(PaywalRequestData data) throws IOException
  {
    URL url;
    String response = "";
    HttpURLConnection connection;

    try
    {
      url = new URL(data.getUrl());
      Log.d(TAG, "run: url=" + data.getUrl());
      connection = (HttpURLConnection)url.openConnection();
      connection.setReadTimeout(15000);
      connection.setConnectTimeout(15000);
      connection.setRequestMethod(data.getMethod());
      connection.setDoInput(true);
      connection.setDoOutput(true);
      Log.d(TAG, "run: query=" + data.getQuery());

      OutputStream os = connection.getOutputStream();
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
      writer.write(data.getQuery());
      writer.flush();
      writer.close();
      os.close();

      int responseCode = connection.getResponseCode();

      if(responseCode == HttpURLConnection.HTTP_OK)
      {
        String line;
        BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((line=br.readLine()) != null)
          response+=line;
      }
      else
        response = "";

      connection.disconnect();
    }
    catch (Exception e)
    {
      Log.d(TAG, "run: Exception = " + e);
      Log.d(TAG, "run: Exception = " + PaywallContext.stackTraceToString(e));
      return "";
    }

    return response;
  }

  public static String getSyncString(PaywalRequestData data)
  {
    try
    {
      String response = PaywallWebRequest.run(data);
      return response;
    }
    catch (IOException e)
    {
      Log.d(TAG, "getSyncString: EXCEPTION = " + e);
      Log.d(TAG, "getSyncString: EXCEPTION = " + PaywallContext.stackTraceToString(e));
      return null;
    }
  }

  public static void getString(PaywalRequestData data)
  {
    final PaywalRequestData inData = data;
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try
        {
          String response = PaywallWebRequest.run(inData);
          if(inData.getReceiver() != null)
            inData.getReceiver().onReceiveString(response);
        }
        catch (IOException e)
        {
          Log.d(TAG, "getJsonArray: Exception=" + PaywallContext.stackTraceToString(e));
          if(inData.getReceiver() != null)
            inData.getReceiver().onError(e);
        }
      }
    });
    thread.start();
  }

  public static void getJsonObject(PaywalRequestData data)
  {
    final PaywalRequestData inData = data;
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try
        {
          String response = PaywallWebRequest.run(inData);
          if(inData.getReceiver() != null)
            inData.getReceiver().onReceiveJson(new JSONObject(response));
        }
        catch (IOException e)
        {
          Log.d(TAG, "getJsonArray: Exception=" + PaywallContext.stackTraceToString(e));
          if(inData.getReceiver() != null)
            inData.getReceiver().onError(e);
        }
        catch (JSONException e)
        {
          Log.d(TAG, "getJsonArray: Exception=" + PaywallContext.stackTraceToString(e));
          if(inData.getReceiver() != null)
            inData.getReceiver().onError(e);
        }
      }
    });
    thread.start();
  }

  public static void getJsonArray(PaywalRequestData data)
  {
    final PaywalRequestData inData = data;
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try
        {
          String response = PaywallWebRequest.run(inData);
          if(inData.getReceiver() != null)
            inData.getReceiver().onReceiveJsonArray(new JSONArray(response));
        }
        catch (IOException e)
        {
          Log.d(TAG, "getJsonArray: Exception=" + PaywallContext.stackTraceToString(e));
          if(inData.getReceiver() != null)
            inData.getReceiver().onError(e);
        }
        catch (JSONException e)
        {
          Log.d(TAG, "getJsonArray: Exception=" + PaywallContext.stackTraceToString(e));
          if(inData.getReceiver() != null)
            inData.getReceiver().onError(e);
        }
      }
    });
    thread.start();
  }
}

