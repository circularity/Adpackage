package com.hula.hsdk;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.facebook.ads.AdSettings;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.ads.MobileAds;
import com.hula.hsdk.listener.BannerListener;
import com.hula.hsdk.listener.InterstitialListener;
import com.hula.hsdk.listener.VideoListener;


public class AdManager {
    private volatile static AdManager mAdManager = null;
    private Activity mActivity;
    private int fbLocation;
    private int mobLocation;
    private boolean isCompleted = false;
    private boolean vidoeAutoPlay;
    private VideoAdMob videoAdMob;
    private VideoAdObj videoAdObj;
    private BannerAdObj bannerAdObj;
    private InterstitialAdObj interstitialAdObj;
    private Handler handler;
    private String[] fbIds = {"YOUR_PLACEMENT_ID", "YOUR_PLACEMENT_ID"};
    private String[] mobIds = {"ca-app-pub-3940256099942544/5224354917"};

    private BannerListener bannerListener;
    private InterstitialListener interstitialListener;
    private VideoListener videoListener;

    private AdManager() {

    }


    public  void loadInterstitial(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                 interstitialAdObj = new InterstitialAdObj(mActivity);
            }
        });

    }
    public void showInterstitial() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (interstitialAdObj!=null){
                    interstitialAdObj.showInterstitialAd();
                }
                else {
                    Log.e("showInterstitial",   "is null");
                }

            }
        });

    }

    public boolean isInterstitiaLoad(){
        if (interstitialAdObj!=null) {
            return interstitialAdObj.InterstitiaLoad();
        }
        return false;
    }

    public void setVidoeAutoPlay(boolean autoPlay) {
        vidoeAutoPlay = autoPlay;

    }

    public boolean bannerIsLoad(){
        if (bannerAdObj != null) {
            return bannerAdObj.isBannerLoad();
        }
        return  false;

    }
    public  void loadBanner(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (bannerAdObj!=null){
                    bannerAdObj.onDestroy();
                }
                bannerAdObj = new BannerAdObj(mActivity);
            }
        });
    }

    public void showBanner() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (bannerAdObj != null) {
                    bannerAdObj.showBanner();
                }
            }
        });


    }

    public void hideBanner() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (bannerAdObj != null) {
                    bannerAdObj.hideBanner();
                }
            }
        });

    }

    public boolean getVidoeAutoPlay() {
        return vidoeAutoPlay;

    }

    public void init(Activity activity) {
        mActivity = activity;
        handler= new Handler(Looper.getMainLooper());
        MobileAds.initialize(activity, "ca-app-pub-3940256099942544~3347511713");
        AppEventsLogger logger = AppEventsLogger.newLogger(activity);
        if (logger != null)
            logger.logEvent("sentFriendRequest");

//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, getRunningActivityName(mActivity));
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "package");
//        FirebaseAnalytics.getInstance(activity).logEvent(FirebaseAnalytics.Event.APP_OPEN,bundle);
        videoAdMob = new VideoAdMob(activity);
        videoAdObj = new VideoAdObj(activity);
        //添加facebook测试设备id
        //可在log中查看  D/AdInternalSettings: Test mode device hash: 95a42086-7454-4862-b166-191f351e53b9
        AdSettings.addTestDevice("813d654a-dd95-4e6a-a463-6e18188f35f3");
    }

    public static AdManager getInstance() {
        if (mAdManager == null) {
            synchronized (AdManager.class) {
                if (mAdManager == null) {
                    // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
                    mAdManager = new AdManager();
                }
            }
        }
        return mAdManager;
    }

    public static String getRunningActivityName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //完整类名
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }


    public void loadVideoAd() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                isCompleted = false;
                mobLocation = 0;
                fbLocation = 0;
                if (fbIds.length > 0) {
                    showFbVideo();
                } else if (mobIds.length > 0) {
                    showMobVideo();
                } else {
                    closeLoading();
                }

            }
        });


    }

    public void loadMobVideoAd() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                isCompleted = false;
                mobLocation = 0;
                if (mobIds.length > 0) {
                    loadMobVideo();
                } else {
                    closeLoading();
                }
            }
        });



    }

    private void loadMobVideo() {
        Log.e("loadMobVideo", mobLocation + "");
        if (mobLocation >= mobIds.length) {
            closeLoading();
            return;
        }
        videoAdMob.LoadVideoAdMob(mobIds[mobLocation], true);
        mobLocation++;
        Log.e("showMobVideo--end", mobLocation + "");
    }

    private void showFbVideo() {
        Log.e("showFbVideo", fbLocation + "");
        if (fbLocation >= fbIds.length) {
            if (mobLocation >= mobIds.length) {
                closeLoading();
            } else {
                showMobVideo();
            }
            return;
        }
        videoAdObj.LoadVideo(fbIds[fbLocation]);
        fbLocation++;
    }

    private void showMobVideo() {
        Log.e("showMobVideo", mobLocation + "");
        if (mobLocation >= mobIds.length) {
            if (fbLocation >= fbIds.length) {
                closeLoading();
            } else {
                showFbVideo();
            }
            return;
        }
        videoAdMob.LoadVideoAdMob(mobIds[mobLocation]);
        mobLocation++;
        Log.e("showMobVideo--end", mobLocation + "");

    }


    public void onMobVideoFailed(String mgs) {
        Log.e("onMobVideoFailed", mgs);
        if (mobLocation < mobIds.length) {
            loadMobVideo();
        } else {
            closeLoading();
        }


    }

    public void onVideoFailed(String mgs) {
        Log.e("onVideoFailed", mgs);
        if (fbLocation <= mobLocation && fbLocation < fbIds.length) {
            showFbVideo();
        } else {
            if (mobLocation < mobIds.length) {
                showMobVideo();
            } else if (fbLocation < fbIds.length) {
                showFbVideo();
            } else {
                closeLoading();
            }

        }
    }

    public boolean isLoadVideo() {
        return videoAdObj.isLoad() || videoAdMob.isLoad();

    }

    public void showCacheVideo() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isLoadVideo()) {
                    boolean show = videoAdMob.showVideo() || videoAdObj.showVideo();
                    Log.e("showCacheVideo", "--" + show);
                }


            }
        });

    }


    /**
     * 插屏关闭回调，可在这处理loading框关闭等逻辑
     */
    public void onInterstitialClose() {
        Log.e("onInterstitialClose", "--");
        if (interstitialListener!=null) {
            interstitialListener.onClose();
        }

    }


    /**
     * 插屏加载回调，可在这处理loading框关闭等逻辑
     */
    public void onInterstitialLoadFailed() {
        Log.e("interstitialLoadFailed", "--");
        if (interstitialListener!=null) {
            interstitialListener.onFailedToLoad();
        }

    }


    /**
     * 视频加载成功回调，可在这直接显示视频广告
     */

    public void onVideoLoad() {
        Log.e("onVideoLoad", "--");
        if (videoListener != null) {
            videoListener.onVideoLoad();
        }
    }


    /**
     * 视频广告奖励发放回调，在此发放奖励
     */
    public void onVideoCompleted() {
        //回调游戏函数，发放奖励
        isCompleted = true;
        if (videoListener != null) {
            videoListener.onVideoCompleted();
        }

    }

    /**
     * 在此处调用关闭loading逻辑
     */
    public void closeLoading() {
        //回调游戏函数,关闭loading
        Log.e("closeLoading", "--");
        if (videoListener != null) {
            videoListener.onVideoFailedToLoad();
        }


    }

    /**
     * 视频关闭回调，可在此关闭loading等逻辑
     */
    public void onVideoColosed() {
        //回调游戏函数,关闭loading
        if (isCompleted) {

        } else {

        }
        if (videoListener != null) {
            videoListener.onVideoClose();
        }
    }

    public void  onVideoOpened(){
        if (videoListener != null) {
            videoListener.onVideoOpened();
        }

    }



    /**
     * 插屏加载成功回调
     */
    public void onInterstitialLoad() {
        if (interstitialListener!=null) {
            interstitialListener.onLoad();
        }
    }

    public void onInterstitialOpened() {
        if (interstitialListener!=null) {
            interstitialListener.onOpened();
        }
    }

    public void onBannerLoad() {
        if (bannerListener!=null) {
            bannerListener.onBannerLoad();
        }
    }

    public void onBannerOpened() {
        if (bannerListener!=null) {
            bannerListener.onBannerOpened();
        }
    }

    public void onBannerShow() {
        if (bannerListener!=null) {
            bannerListener.onBannerShow();
        }
    }

    public void onBannerHide() {
        if (bannerListener!=null) {
            bannerListener.onBannerHide();
        }
    }

    public void onBannerFailde() {
        if (bannerListener!=null) {
            bannerListener.onBannerFailedToLoad();
        }
    }

    public void setBannerListener(BannerListener bannerListener) {
        this.bannerListener = bannerListener;
    }

    public void setInterstitialListener(InterstitialListener interstitialListener) {
        this.interstitialListener = interstitialListener;
    }

    public void setVideoListener(VideoListener videoListener) {
        this.videoListener = videoListener;
    }
}
