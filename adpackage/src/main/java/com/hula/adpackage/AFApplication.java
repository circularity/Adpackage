package com.hula.adpackage;

import android.app.Application;
import android.content.Context;


import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;

public class AFApplication extends Application {
    private static final String AF_DEV_KEY = "aVg6HRucDUQU9zqCQ48TTZ";
    @Override
    public void onCreate() {
        super.onCreate();
        init(this);

    }
    public static void  init(Application  context){

        AppsFlyerConversionListener conversionDataListener =new AppsFlyerConversionListener() {
            @Override
            public void onInstallConversionDataLoaded(Map<String, String> conversionData) {

            }

            @Override
            public void onInstallConversionFailure(String errorMessage) {

            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {

            }

            @Override
            public void onAttributionFailure(String errorMessage) {

            }
        };
        AppsFlyerLib.getInstance().init(AF_DEV_KEY , conversionDataListener ,context);
        AppsFlyerLib.getInstance().startTracking(context,AF_DEV_KEY);
    }
}
