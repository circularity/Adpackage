package com.hula.adpackage.listener;

public interface BannerListener {
    void onBannerLoad();
    void onBannerHide();
    void onBannerShow();
    void onBannerFailedToLoad();
    void onBannerOpened();
}
