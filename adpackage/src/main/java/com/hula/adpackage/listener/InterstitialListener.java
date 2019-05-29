package com.hula.adpackage.listener;

public interface InterstitialListener {
    void onLoad();
    void onClose();
    void onFailedToLoad();
    void onOpened();
}
