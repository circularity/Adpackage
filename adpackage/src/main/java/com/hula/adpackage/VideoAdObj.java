package com.hula.adpackage;

import android.app.Activity;
import android.os.CountDownTimer;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;


public class VideoAdObj{

    private final String TAG = VideoAdObj.class.getSimpleName();
    private RewardedVideoAd rewardedVideoAd;
    private boolean isTimeOut=false;
    private Activity activity;
    private boolean isLoad=false;

    public  VideoAdObj(Activity context){
        activity=context;
    }

    public void LoadVideo(String id) {
        isLoad=false;
        isTimeOut=false;
        rewardedVideoAd = new RewardedVideoAd(activity, id);
        final CountDownTimer timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                isTimeOut=true;
                AdManager.getInstance().onVideoFailed("TimeOut");
            }
        };
        rewardedVideoAd.setAdListener(new RewardedVideoAdListener() {
            @Override

            public void onError(Ad ad, AdError error) {
                Log.e("0", "错误信息" + error.getErrorMessage());
                timer.cancel();
                if (!isTimeOut) {
                    AdManager.getInstance().onVideoFailed(error.getErrorMessage());
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                timer.cancel();
                if(rewardedVideoAd.isAdInvalidated()) {
                    Log.d(TAG, "加载失败");
                } else {
                    if (!AdManager.getInstance().getVidoeAutoPlay()&&!isTimeOut){
                        isLoad=true;
                        AdManager.getInstance().onVideoLoad();
                        return;
                    }
                    if (!isTimeOut) {
                        rewardedVideoAd.show();
                    }
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Rewarded video ad clicked
                Log.d(TAG, "Rewarded video ad clicked!");
                AdManager.getInstance().onVideoOpened();
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d(TAG, "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                Log.d(TAG, "Rewarded video completed!");
                // Call method to give reward
                // giveReward();
                AdManager.getInstance().onVideoCompleted();
            }

            @Override
            public void onRewardedVideoClosed() {
                if (rewardedVideoAd != null) {
                    rewardedVideoAd.destroy();
                    rewardedVideoAd = null;
                }
                AdManager.getInstance().onVideoColosed();
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
            }
        });
        rewardedVideoAd.loadAd();
        timer.start();
    }

    public boolean isLoad() {
        return isLoad;
    }

    public  boolean  showVideo(){
        if (!isTimeOut&&rewardedVideoAd!=null) {
            if (!rewardedVideoAd.isAdInvalidated())
            rewardedVideoAd.show();
            return  true;
        }
        return  false;
    }

}