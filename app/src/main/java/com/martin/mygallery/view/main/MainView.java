package com.martin.mygallery.view.main;

import com.arellomobile.mvp.MvpView;

public interface MainView extends MvpView {
    void showImgurFragment();

    void showFavoriteFragment();

    void showDownloadedFragment();
}
