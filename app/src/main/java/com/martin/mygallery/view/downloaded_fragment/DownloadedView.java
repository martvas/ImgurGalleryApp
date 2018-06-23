package com.martin.mygallery.view.downloaded_fragment;

import com.arellomobile.mvp.MvpView;

public interface DownloadedView extends MvpView {
    void init();

    void showLoading();

    void hideLoading();

    void showError(String errorStr);

    void showToast(String toastText);

    void updateRVList();
}
