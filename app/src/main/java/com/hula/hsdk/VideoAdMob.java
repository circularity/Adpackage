package com.hula.hsdk;

import android.content.Context;
import android.os.CountDownTimer;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class VideoAdMob {

    private RewardedVideoAd mRewardedVideoAd;
    private boolean isTimeOut = false;
    private Context mContext;
    private boolean isLoad = false;

    public VideoAdMob(Context context) {
        mContext = context;
    }

    public void LoadVideoAdMob(String id) {
        LoadVideoAdMob(id, false);

    }

    public void LoadVideoAdMob(String id, final boolean isLoadCache) {
        isLoad = false;
        isTimeOut=false;
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(mContext);
        final CountDownTimer timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                isTimeOut = true;
                AdManager.getInstance().onVideoFailed("TimeOut");
            }
        };
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                timer.cancel();
                if (!AdManager.getInstance().getVidoeAutoPlay()&&!isTimeOut) {
                    isLoad = true;
                    AdManager.getInstance().onVideoLoad();
                    return;
                }
                if (mRewardedVideoAd.isLoaded()) {
                    if (!isTimeOut) {
                        mRewardedVideoAd.show();
                    }
                }
            }

            @Override
            public void onRewardedVideoAdOpened() {
                AdManager.getInstance().onVideoOpened();
            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                AdManager.getInstance().onVideoColosed();
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                timer.cancel();
                if (!isTimeOut) {
                    if (isLoadCache) {
                        AdManager.getInstance().onMobVideoFailed("Failed" + i);
                    } else {
                        AdManager.getInstance().onVideoFailed("Failed" + i);
                    }

                }

            }

            @Override
            public void onRewardedVideoCompleted() {
                AdManager.getInstance().onVideoCompleted();
            }
        });
        loadRewardedVideoAd(id);
    }

    private void loadRewardedVideoAd(String id) {
        mRewardedVideoAd.loadAd(id,
                new AdRequest.Builder().build());
    }

    public boolean isLoad() {
        return isLoad;
    }

    public boolean showVideo() {
        if (mRewardedVideoAd != null && mRewardedVideoAd.isLoaded()) {
            if (!isTimeOut) {
                mRewardedVideoAd.show();
                return true;
            }
        }
        return false;
    }

}