package com.martin.mygallery.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.martin.mygallery.model.image.ImageSaver;
import com.martin.mygallery.model.savers.DownloadedListSaver;
import com.martin.mygallery.model.savers.FavoriteListSaver;
import com.martin.mygallery.view.imgur_and_favorite.FavoriteView;
import com.martin.mygallery.view.imgur_and_favorite.ImgurRowView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class FavoritePresenter extends MvpPresenter<FavoriteView> implements ImgurListPresenter {
    @Inject
    FavoriteListSaver favoriteListSaver;
    @Inject
    ImageSaver imageSaver;
    @Inject
    DownloadedListSaver downloadedListSaver;

    private Scheduler scheduler;
    private List<String> favoriteImagesUrl;
    private List<String> downloadedImagePathes;

    public FavoritePresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }

    public void loadFavoriteImagesFromSaver() {
        getViewState().showLoading();
        if (favoriteImagesUrl != null && !favoriteImagesUrl.isEmpty()) {
            getViewState().hideLoading();
            getViewState().updateRVList();
        } else {
            getViewState().hideLoading();
            getViewState().showError("You don't have any favorite images");
        }

    }

    @Override
    public void bindImageListRow(int pos, ImgurRowView imgurRowView) {
        if (favoriteImagesUrl != null) {
            imgurRowView.setPictureToContainer(favoriteImagesUrl.get(pos));
            imgurRowView.makeFavoriteStarYellow();
        }
    }

    @Override
    public int getImagesCountToRV() {
        return favoriteImagesUrl.size();
    }

    @Override
    public void addOrRemoveFromFavorite(int position, ImgurRowView imgurRowView) {
        String thisImageUrl = favoriteImagesUrl.get(position);
        if (favoriteImagesUrl.contains(thisImageUrl)) {
            favoriteImagesUrl.remove(thisImageUrl);
            imgurRowView.makeFavoriteStarTransparent();
        } else {
            favoriteImagesUrl.add(thisImageUrl);
            imgurRowView.makeFavoriteStarYellow();
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveImage(int position, ImgurRowView imgurRowView) {
        imageSaver.saveImageToStorage(favoriteImagesUrl.get(position))
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(s -> {
                    downloadedImagePathes.add(s);
                    getViewState().showToast("Image downloaded");
                }, throwable -> {
                    imgurRowView.makeDownloadIconWhite();
                    getViewState().showToast("Can't download this image");
                });
    }

    public void loadFavoriteUrlList() {
        this.favoriteImagesUrl = favoriteListSaver.getFavavoriteImagesUrlList();
    }

    public void loadOrCreateDownloadedImagesList() {
        this.downloadedImagePathes = downloadedListSaver.getDownloadedFilePathesList();
        if (downloadedImagePathes == null) downloadedImagePathes = new ArrayList<>();
    }

    public void saveFavoriteImagesUrlList() {
        if (favoriteImagesUrl != null)
            favoriteListSaver.saveFavoriteImageUrlLIst(favoriteImagesUrl);

    }

    public void saveDownloadedList() {
        if (downloadedImagePathes != null)
            downloadedListSaver.saveDownloadedFilePathesList(downloadedImagePathes);
    }
}
