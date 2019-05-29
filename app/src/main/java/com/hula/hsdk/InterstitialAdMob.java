package com.hula.hsdk;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class InterstitialAdMob {

    private InterstitialAd mInterstitialAd;
    private  boolean isLoad;

    public InterstitialAdMob(Context context) {
        isLoad=false;
        mInterstitialAd = new InterstitialAd(context);
        String id=context.getString(R.string.admob_interstitial);
        mInterstitialAd.setAdUnitId(id);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                isLoad=true;
                AdManager.getInstance().onInterstitialLoad();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                AdManager.getInstance().onInterstitialLoadFailed();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                AdManager.getInstance().onInterstitialOpened();

            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                AdManager.getInstance().onInterstitialClose();
            }
        });
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public boolean showInterstitialAd(){
        if (mInterstitialAd!=null&&mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            return true;
        }

        return  false;

    }

    public boolean isLoad() {
        return isLoad;
    }
}
