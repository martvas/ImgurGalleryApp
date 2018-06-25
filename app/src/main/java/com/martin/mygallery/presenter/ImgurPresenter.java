package com.martin.mygallery.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.martin.mygallery.model.image.ImageSaver;
import com.martin.mygallery.model.repo.ImgurGalleryRepo;
import com.martin.mygallery.model.savers.DownloadedListSaver;
import com.martin.mygallery.model.savers.FavoriteListSaver;
import com.martin.mygallery.model.savers.LastSearchTagSaver;
import com.martin.mygallery.view.imgur_and_favorite.ImgurRowView;
import com.martin.mygallery.view.imgur_and_favorite.ImgurView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class ImgurPresenter extends MvpPresenter<ImgurView> implements ImgurListPresenter {
    @Inject
    ImgurGalleryRepo imgurGalleryRepo;
    @Inject
    FavoriteListSaver favoriteListSaver;
    @Inject
    ImageSaver imageSaver;
    @Inject
    DownloadedListSaver downloadedListSaver;
    @Inject
    LastSearchTagSaver lastSearchTagSaver;
    private Scheduler scheduler;

    private List<String> imagesUrlList;
    private List<String> favoriteImagesUrlList;
    private List<String> downloadedImagePathesList;

    public ImgurPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }

    public void onPermissionsGranted() {
        String lastTagName = lastSearchTagSaver.getLastSearchTag();
        if (lastTagName == null) {
            getViewState().showError("Please enter some search query to see images");
        } else loadImgurImagesByTag(lastTagName);
    }

    @SuppressLint("CheckResult")
    public void loadImgurImagesByTag(String tag) {
        getViewState().showLoading();
        imgurGalleryRepo.getGalleryByTag(tag)
                .observeOn(scheduler)
                .subscribe(imagesUrl -> {
                            lastSearchTagSaver.saveLastSearchTag(tag);
                            this.imagesUrlList = imagesUrl;
                            getViewState().showError("");
                            getViewState().hideLoading();
                            getViewState().updateRVList();
                        }, throwable -> {
                            if (throwable.getMessage().equals(ImgurGalleryRepo.ITEM_EMPTY_EXCEPTION)) {
                                getViewState().showToast("We don't have images by this tag");
                            } else getViewState().showToast("Failed to get images from Imgur");
                        }
                );
    }

    @Override
    public void bindImageListRow(int pos, ImgurRowView imgurRowView) {
        if (imagesUrlList != null) {
            imgurRowView.setPictureToContainer(imagesUrlList.get(pos));
            if (favoriteImagesUrlList != null && favoriteImagesUrlList.size() > 0) {
                if (favoriteImagesUrlList.contains(imagesUrlList.get(pos)))
                    imgurRowView.makeFavoriteStarYellow();
                else imgurRowView.makeFavoriteStarTransparent();
            }
        }
    }

    @Override
    public int getImagesCountToRV() {
        return imagesUrlList == null ? 0 : imagesUrlList.size();
    }

    @Override
    public void addOrRemoveFromFavorite(int position, ImgurRowView imgurRowView) {
        String thisImageUrl = imagesUrlList.get(position);
        if (favoriteImagesUrlList.contains(thisImageUrl)) {
            favoriteImagesUrlList.remove(thisImageUrl);
            imgurRowView.makeFavoriteStarTransparent();
        } else {
            favoriteImagesUrlList.add(thisImageUrl);
            imgurRowView.makeFavoriteStarYellow();
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveImage(int position, ImgurRowView imgurRowView) {
        imageSaver.saveImageToStorage(imagesUrlList.get(position))
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(s -> {
                    downloadedImagePathesList.add(s);
                    getViewState().showToast("Image downloaded");
                    Timber.d("Image saved in: %s", s);
                }, throwable -> {
                    imgurRowView.makeDownloadIconWhite();
                    getViewState().showToast("Can't download this image");
                });
    }

    public void loadOrCreateFavoriteImagesUrlList() {
        this.favoriteImagesUrlList = favoriteListSaver.getFavavoriteImagesUrlList();
        if (favoriteImagesUrlList == null) favoriteImagesUrlList = new ArrayList<>();
    }

    public void saveFavoriteImagesUrlList() {
        favoriteListSaver.saveFavoriteImageUrlLIst(favoriteImagesUrlList);
    }

    public void loadOrCreateDownloadedImagesPathes() {
        this.downloadedImagePathesList = downloadedListSaver.getDownloadedFilePathesList();
        if (downloadedImagePathesList == null) downloadedImagePathesList = new ArrayList<>();
    }

    public void saveDownloadedList() {
        downloadedListSaver.saveDownloadedFilePathesList(downloadedImagePathesList);
    }
}
