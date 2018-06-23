package com.martin.mygallery.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.martin.mygallery.NetworkStatus;
import com.martin.mygallery.view.main.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        if (NetworkStatus.isOnline()) {
            getViewState().showImgurFragment();
        } else if (NetworkStatus.isOffline()) {
            getViewState().showDownloadedFragment();
        }
    }

    public void showImgurFragment() {
        getViewState().showImgurFragment();
    }

    public void showFavoriteFragment() {
        getViewState().showFavoriteFragment();
    }

    public void showDownloadedFragment() {
        getViewState().showDownloadedFragment();
    }

}
