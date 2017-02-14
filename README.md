TCI (Telecommunication Ministry of Iran - tci.ir) Payment SDK Plugin for Android
================================================================================

TCI Payment SDK Plugin for Cordova 3.0+ Android

This plugin in based on documentation from http://my.tci.ir/Admin.html#!/admin/documents

there is an implementation of .aar module of TCI SDK for Cordova projects

Installation
===========

For Cordova CLI -
`cordova plugin add cordova-plugin-tci-sdk --save`


Usage (in javascript anywhere in your project)
==============================================

- TciSdk.Buy("appName", "itemName", successCallback, errorCallback);

the success callback and error callback have an `data` attribute that is a json object returned from the SDK. the sample code maybe like this:

```javascript
TciSdk.Buy("appName", "itemName", function(data) {
    // Optional: implement some api check purchase at http://my.tci.ir/api/v1/IAP/Consume/{data.token}
    alert("Success: Your purchase token is: " + data.token + ", and your RRN code is: " + data.RRN);
}, function(data) {
     if (data.RRN) {
         alert("Error: you didn't complete the payment, your RRN code is: " + data.RRN);
     } else {
         alert("Error: you didn't complete the payment");
     }
 });
```
