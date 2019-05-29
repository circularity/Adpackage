package com.hula.hsdk.listener;

public interface InterstitialListener {
    void onLoad();
    void onClose();
    void onFailedToLoad();
    void onOpened();
}
