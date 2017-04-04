/*
       TCI Payment SDK for Cordova Projects

       Created by Mohammad Reza Maghoul (IVIR3zaM)

       Home Page: https://github.com/IVIR3zaM/TciSdk


       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package org.apache.cordova.plugin;

import android.util.Log;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Set;

import android.content.Intent;
import android.net.Uri;
import android.app.Activity;

import com.af.IPG.FragmentPlaceHolder;

public class TciSdk extends CordovaPlugin {
    private CallbackContext callback;
    private static final String TAG = "TciPaymentSdk";
    private static final int ACTIVITY_CODE_PAYMENT = 2017;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callback = callbackContext;
        if (action.equals("Buy")) {
            try{
                return Buy(args.getString(0), args.getString(1), args.getString(2));
            } catch (JSONException e) {
                callback.error(e.getMessage());
                return false;
            }
        }
        this.callback.error("Invalid Action");
        return false;
    }
    
    private boolean Buy(final String appName, final String itemName, final String customPrice) {
        Log.v(TAG, "Buy for appName= " + appName + " itemName = " + itemName);
        final CordovaInterface cordovaObj = cordova;
        final CordovaPlugin plugin = this;

        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Intent intent = new Intent(cordovaObj.getActivity().getApplicationContext(), FragmentPlaceHolder.class);
                intent.putExtra("appName", appName);
                intent.putExtra("itemName", itemName);
                if (Integer.parseInt(customPrice) > 0) {
                    intent.putExtra("customPrice", customPrice);
                }
                cordovaObj.startActivityForResult(plugin, intent, ACTIVITY_CODE_PAYMENT);
             }
        });
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(TAG, "requestCode = " + requestCode + " resultCode = " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (ACTIVITY_CODE_PAYMENT == requestCode) {
            Boolean error = false;
            String errorMessage = "";
            JSONObject json = new JSONObject();
            Set<String> keys = data.getExtras().keySet();
            for (String key : keys) {
                try {
                    json.put(key, JSONObject.wrap(data.getExtras().get(key)));
                } catch(JSONException e) {
                    errorMessage = e.getMessage();
                    error = true;
                }
            }
            if (error) {
                callback.error(errorMessage);
            } else if (resultCode == Activity.RESULT_OK) {
                callback.success(json);
            } else {
                callback.error(json);
            }
        }
    }
}