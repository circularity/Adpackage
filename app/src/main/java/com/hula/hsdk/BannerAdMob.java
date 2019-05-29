package com.hula.hsdk;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class BannerAdMob {
    private AdView adView;
    public  boolean bannerLoad=false;
    public BannerAdMob(Activity context,  LinearLayout bottom) {
        bannerLoad=false;
        bottom.setVisibility(View.GONE);
        adView=new AdView(context);
        String id=context.getString(R.string.admob_banner);
        adView.setAdUnitId(id);
        adView.setAdSize(AdSize.SMART_BANNER);
        bottom.addView(adView);
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                bannerLoad=true;
                AdManager.getInstance().onBannerLoad();

            }
            @Override
            public void onAdFailedToLoad(int var1) {
                AdManager.getInstance().onBannerFailde();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                AdManager.getInstance().onBannerOpened();

            }
        });
        adView.loadAd(new AdRequest.Builder().build());
    }

    public boolean isLoadBanner(){
        return adView!=null&&bannerLoad;

    }




}
