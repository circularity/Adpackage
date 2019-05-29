package com.hula.adpackage;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;


public class BannerAdObj{

    private final String TAG = VideoAdObj.class.getSimpleName();
    private AdView adView;
    LinearLayout bottom;
    BannerAdMob bannerAdMob;
    public BannerAdObj(final Activity context) {
        String id=context.getString(R.string.facebook_banner);
        adView = new AdView(context, id, AdSize.BANNER_HEIGHT_50);
        FrameLayout.LayoutParams params3=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params3.gravity= Gravity.BOTTOM|Gravity.RIGHT;
        if (bottom == null) {
            bottom=new LinearLayout(context);
        }
        else {
            bottom.setVisibility(View.GONE);
            bottom.removeAllViews();
        }

        bottom.addView(adView);
        context.addContentView(bottom,params3);
        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                bannerAdMob = new BannerAdMob(context, bottom);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
                hideBanner();
                AdManager.getInstance().onBannerLoad();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                AdManager.getInstance().onBannerOpened();
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback


            }
        });
        adView.loadAd();
    }
    public void onDestroy(){
        if (bottom!=null){
            bottom.setVisibility(View.GONE);
            bottom.removeAllViews();
        }
    }

    public  boolean isBannerLoad(){
        if (adView == null) {
            return  false;
        }
        if (bannerAdMob!=null){
           return bannerAdMob.isLoadBanner();
        }

        return !adView.isAdInvalidated();
    }

    public  void showBanner(){
        if (bottom!=null){
            bottom.setVisibility(View.VISIBLE);
            AdManager.getInstance().onBannerShow();
        }

    }
    public void hideBanner(){
       if (bottom!=null){
           bottom.setVisibility(View.GONE);
           AdManager.getInstance().onBannerHide();
       }

    }
}