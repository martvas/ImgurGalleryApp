package com.martin.mygallery.view.main;

import com.arellomobile.mvp.MvpView;

//@StateStrategyType(value = SingleStateStrategy.class)
public interface MainView extends MvpView {
    void showImgurFragment();

    void showFavoriteFragment();

    void showDownloadedFragment();
}
