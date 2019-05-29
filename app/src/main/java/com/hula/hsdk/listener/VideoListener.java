package com.hula.hsdk.listener;

public interface VideoListener {
    void onVideoLoad();
    void onVideoClose();
    void onVideoCompleted();
    void onVideoFailedToLoad();
    void onVideoOpened();
}
