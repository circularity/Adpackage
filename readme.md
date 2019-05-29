#说明

###一．在app级别下的build gradle下导入需要的库，添加完该代码后，请保存文件并执行“Gradle sync”。
```
implementation 'com.facebook.android:audience-network-sdk:5.1.1'
implementation 'com.facebook.android:facebook-core:[4,5]'

implementation 'com.appsflyer:af-android-sdk:4.9.0@aar'
implementation 'com.android.installreferrer:installreferrer:1.0'

implementation 'com.google.android.gms:play-services-ads:17.2.0'
```



###二．导入hsdk包源码到src代码目录下


###三．更新您的 AndroidManifest.xml

选择AFApplication作为应用的Application
如遇已有Application情况可将AFApplication代码copy现有Application类下

选择networkSecurityConfig标签（可参考demo或https://developers.facebook.com/docs/audience-network/android-network-security-config）

####配置需要的权限
```
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="com.google.android.finsky.permission.BIND_GET_INSTALL_REFERRER_SERVICE" />

<application
    android:name=".AFApplication"
    android:networkSecurityConfig="@xml/network_security_config"

```

####替换Facebook和谷歌的应用id
```
<meta-data
    android:name="com.google.android.gms.ads.APPLICATION_ID"
    android:value="ca-app-pub-3940256099942544~3347511713"/>
<meta-data android:name="com.facebook.sdk.ApplicationId" android:value="@string/facebook_app_id"/>

<receiver android:name="com.appsflyer.MultipleInstallBroadcastReceiver" android:exported="true">
    <intent-filter>
        <action android:name="com.android.vending.INSTALL_REFERRER" />
    </intent-filter>
</receiver>
```

###四．更新res目录下 string文件
####替换应用id 和广告id
```
<string name="facebook_app_id">652302905240772</string>
<string name="admob_interstitial">ca-app-pub-3940256099942544/1033173712</string>
<string name="facebook_interstitial">2270479669896140_2270548789889228</string>
<string name="admob_banner">ca-app-pub-3477755457457214/8723778345</string>
<string name="facebook_banner">2270479669896140_2273624722914968</string>
```

####替换AdManager中广告id



####视频广告
```
//加载视频广告
AdManager.getInstance().loadVideoAd();
```

```
//单独加载admob广告
AdManager.getInstance().loadMobVideoAd();
```

```

//判断是否加载广告
if (AdManager.getInstance().isLoadVideo()) {
    AdManager.getInstance().showCacheVideo();
}
```

```
//显示已经加载的广告
AdManager.getInstance().showCacheVideo();
```

####Banner广告
```
//加载Banner广告
AdManager.getInstance().loadBanner();
```

```
//显示Banner广告
AdManager.getInstance().showBanner();
```
```
//隐藏banner广告
AdManager.getInstance().hideBanner();
```
####插屏广告
```
//加载插屏广告
AdManager.getInstance().loadInterstitial();
```
```
//显示插屏广告
if (AdManager.getInstance().isInterstitiaLoad()) {
    AdManager.getInstance().showInterstitial();
}
```


####相关回调
```

//添加广告回调
AdManager.getInstance().setBannerListener(new BannerListener() {
    @Override
    public void onBannerLoad() {
        //加载成功
        Log.e("BannerListener","onBannerLoad");
    }

    @Override
    public void onBannerHide() {
        //隐藏banner
        Log.e("BannerListener","onBannerHide");
    }

    @Override
    public void onBannerShow() {
        //显示banner
        Log.e("BannerListener","onBannerShow");
    }

    @Override
    public void onBannerFailedToLoad() {
        //加载失败
        Log.e("BannerListener","onBannerFailedToLoad");
    }

    @Override
    public void onBannerOpened() {
        //点击banner
        Log.e("BannerListener","onBannerOpened");
    }
});
```
```
AdManager.getInstance().setInterstitialListener(new InterstitialListener() {
    @Override
    public void onLoad() {
        Log.e("InterstitialListener","onLoad");
    }

    @Override
    public void onClose() {
        Log.e("InterstitialListener","onClose");
    }

    @Override
    public void onFailedToLoad() {
        Log.e("InterstitialListener","onFailedToLoad");
    }

    @Override
    public void onOpened() {
        Log.e("InterstitialListener","onOpened");
    }
});
```
```
AdManager.getInstance().setVideoListener(new VideoListener() {
    @Override
    public void onVideoLoad() {
        Log.e("VideoListener","onVideoLoad");
    }

    @Override
    public void onVideoClose() {
        //视频被关闭
        Log.e("VideoListener","onVideoClose");
    }

    @Override
    public void onVideoCompleted() {
        //发放视频奖励
        Log.e("VideoListener","onVideoCompleted");
    }

    @Override
    public void onVideoFailedToLoad() {
        Log.e("VideoListener","onVideoFailedToLoad");
    }

    @Override
    public void onVideoOpened() {
        Log.e("VideoListener","onVideoOpened");
    }
});

```
可通过AdManager中相关回调调取游戏引擎相关方法执行交互逻辑








###四.如何测试
####1.测试Admob 通过查看log添加设备到测试列表
log格式如下
```
I/Ads: Use AdRequest.Builder.addTestDevice("A903DDFCF6FFDD6FD076C25212D21F91") to get test ads on this device.
```

然后在GoogleAdScript脚本CreateAdRequest 中加入设备到测试列表


####2.使用Admob测试id

#####Android
```
横幅广告	ca-app-pub-3940256099942544/6300978111
插页式广告	ca-app-pub-3940256099942544/1033173712
激励视频广告	ca-app-pub-3940256099942544/5224354917
```

#####iOS
```
横幅广告	ca-app-pub-3940256099942544/2934735716
插页式广告	ca-app-pub-3940256099942544/4411468910
激励视频广告	ca-app-pub-3940256099942544/1712485313
```

####3.测试Facebook广告
3.1.提供设备id由我们加入到后台

ios为Identifier for Advertising (IDFA) for iOS devices

Android为Google Advertising ID (AAID) for Android devices
Android 可以在谷歌服务中广告页面查看，也可下载infinia.optout查看

https://play.google.com/store/apps/details?id=com.infinia.optout

####3.2添加设备id到测试列表
//添加facebook测试设备id
//可在log中查看  D/AdInternalSettings: Test mode device hash: 95a42086-7454-4862-b166-191f351e53b9

```
AdSettings.addTestDevice("95a42086-7454-4862-b166-191f351e53b9";
```
