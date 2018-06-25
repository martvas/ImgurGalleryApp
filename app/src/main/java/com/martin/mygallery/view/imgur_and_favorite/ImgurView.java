package com.martin.mygallery.view.imgur_and_favorite;

import com.arellomobile.mvp.MvpView;

//@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface ImgurView extends MvpView {
    void init();

    void showLoading();

    void hideLoading();

    void showError(String errorStr);

    void checkPermissions();

    void showToast(String toastText);

    void updateRVList();
}
