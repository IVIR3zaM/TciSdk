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

var exec = require("cordova/exec");

var TciSdk = {
  Buy: function(appName, itemName, successCallback, errorCallback) {
    var success = (typeof successCallback == "function" ? successCallback : function(){});
    if (typeof errorCallback != "function") {
      errorCallback = function(){};
    }
    var error = function(data) {
      if (typeof data == "string") {
        data = {
          RRN: null,
          result: false,
          message: data
        };
      }
      errorCallback(data);
    };
    exec(success, error, "TciSdk", "Buy", [appName, itemName]);
  }
};

module.exports = TciSdk;