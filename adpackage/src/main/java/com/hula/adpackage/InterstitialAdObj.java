package com.hula.adpackage;

import android.content.Context;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class InterstitialAdObj {
    private InterstitialAd interstitialAd;
    private InterstitialAdMob InterstitialAdObj;
    private  static  final  String  TAG="InterstitialAdObj";
    private  boolean isLoad;

    public InterstitialAdObj(final Context context) {
        isLoad=false;
        String id=context.getString(R.string.facebook_interstitial);
        interstitialAd = new InterstitialAd(context,id);
        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
                AdManager.getInstance().onInterstitialClose();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                InterstitialAdObj=new InterstitialAdMob(context);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                isLoad=true;
                AdManager.getInstance().onInterstitialLoad();

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
                AdManager.getInstance().onInterstitialOpened();
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();
    }

    public boolean  showInterstitialAd(){
        if (interstitialAd!=null&&!interstitialAd.isAdInvalidated()){
            interstitialAd.show();
            return true;
        }
        if (InterstitialAdObj!=null&&InterstitialAdObj.isLoad()){
            InterstitialAdObj.showInterstitialAd();
            return  true;
        }
        return  false;
    }

    public  boolean  InterstitiaLoad(){
        if (InterstitialAdObj != null) {
            return InterstitialAdObj.isLoad();
        }
        return  isLoad;
    }
    }
