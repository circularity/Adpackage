package com.hula;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hula.hsdk.AdManager;
import com.hula.hsdk.R;
import com.hula.hsdk.listener.BannerListener;
import com.hula.hsdk.listener.InterstitialListener;
import com.hula.hsdk.listener.VideoListener;

public class MainActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdManager.getInstance().init(this);
        text = findViewById(R.id.tv);
        View loadVideo = findViewById(R.id.loadvideo);
        View showVideo = findViewById(R.id.showvideo);
        loadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        AdManager.getInstance().loadVideoAd();

                    }
                }.start();
                text.setText("loadVideo");

            }
        });

        showVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    @Override
                    public void run() {
                        Log.e("线程",android.os.Process.myTid()+"");
                        AdManager.getInstance().showCacheVideo();

                    }
                }.start();
            }
        });

        View loadInterstitial  = findViewById(R.id.laodinterstitial);
        loadInterstitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        Log.e("线程",android.os.Process.myTid()+"");
                        AdManager.getInstance().loadInterstitial();
                    }
                }.start();
                text.setText("loadInterstitial");
            }
        });
        View showInterstitial = findViewById(R.id.showinterstitial);
        showInterstitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        Log.e("线程",android.os.Process.myTid()+"");
                        if (AdManager.getInstance().isInterstitiaLoad()) {
                            AdManager.getInstance().showInterstitial();
                        }


                    }
                }.start();
            }
        });
        View loadBanner = findViewById(R.id.laodbanner);
        View showBanner = findViewById(R.id.showbanner);
        loadBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    @Override
                    public void run() {
                        Log.e("线程",android.os.Process.myTid()+"");
                        AdManager.getInstance().loadBanner();
                    }
                }.start();
                text.setText("loadBanner");
            }
        });

        showBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    @Override
                    public void run() {
                        Log.e("线程",android.os.Process.myTid()+"");
                        if (AdManager.getInstance().bannerIsLoad()) {
                            AdManager.getInstance().showBanner();
                        }

                    }
                }.start();
            }
        });

        View hideBanner = findViewById(R.id.hiedbanner);
        hideBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    @Override
                    public void run() {
                        Log.e("线程",android.os.Process.myTid()+"");
                        AdManager.getInstance().hideBanner();

                    }
                }.start();
            }
        });


        //添加广告回调
        AdManager.getInstance().setBannerListener(new BannerListener() {
            @Override
            public void onBannerLoad() {
                //加载成功
                Log.e("BannerListener","onBannerLoad");
                text.setText("Banner is load");
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
                text.setText("Banner is FailedToLoad");
            }

            @Override
            public void onBannerOpened() {
                //点击banner
                Log.e("BannerListener","onBannerOpened");
            }
        });

        AdManager.getInstance().setInterstitialListener(new InterstitialListener() {
            @Override
            public void onLoad() {
                Log.e("InterstitialListener","onLoad");
                text.setText("Interstitial is Load");
            }

            @Override
            public void onClose() {
                Log.e("InterstitialListener","onClose");
            }

            @Override
            public void onFailedToLoad() {
                Log.e("InterstitialListener","onFailedToLoad");
                text.setText("Interstitial is onFailedToLoad");

            }

            @Override
            public void onOpened() {
                Log.e("InterstitialListener","onOpened");
            }
        });
        AdManager.getInstance().setVideoListener(new VideoListener() {
            @Override
            public void onVideoLoad() {
                Log.e("VideoListener","onVideoLoad");
                text.setText("Video is Load");
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
                text.setText("Video is FailedToLoad");
            }

            @Override
            public void onVideoOpened() {
                Log.e("VideoListener","onVideoOpened");
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
