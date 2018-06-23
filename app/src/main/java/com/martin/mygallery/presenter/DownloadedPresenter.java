package com.martin.mygallery.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.martin.mygallery.model.image.ImageSaver;
import com.martin.mygallery.model.savers.DownloadedListSaver;
import com.martin.mygallery.model.savers.PaperDownloadedListSaver;
import com.martin.mygallery.view.downloaded_fragment.DownloadedItemRowView;
import com.martin.mygallery.view.downloaded_fragment.DownloadedView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class DownloadedPresenter extends MvpPresenter<DownloadedView> implements DownloadedListPresenter {
    @Inject
    DownloadedListSaver downloadedListSaver;
    @Inject
    ImageSaver imageSaver;
    private Scheduler scheduler;
    private List<String> downloadedImagePathesList;

    public DownloadedPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
        downloadedListSaver = new PaperDownloadedListSaver();
        imageSaver = new ImageSaver();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
    }

    public void loadImagePathesAndUpdateRV() {
        getViewState().showLoading();
        loadDownloadedImagesPathes();
        if (downloadedImagePathesList != null && !downloadedImagePathesList.isEmpty()) {
            getViewState().hideLoading();
            getViewState().updateRVList();
        } else {
            getViewState().hideLoading();
            getViewState().showError("You don't have any downloaded images");
        }
    }


    @Override
    public void bindImageListRow(int pos, DownloadedItemRowView downloadedItemRowView) {
        if (downloadedImagePathesList != null) {
            downloadedItemRowView.setPictureToContainer(downloadedImagePathesList.get(pos));
        }
    }

    @Override
    public int getImagesCountToRv() {
        return downloadedImagePathesList == null ? 0 : downloadedImagePathesList.size();
    }

    @SuppressLint("CheckResult")
    @Override
    public void deleteFromStorage(int position) {
        String imageFilePath = downloadedImagePathesList.get(position);
        imageSaver.deleteImage(imageFilePath)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        getViewState().showToast("Image is deleted");
                        downloadedImagePathesList.remove(imageFilePath);
                        getViewState().updateRVList();
                    } else {
                        getViewState().showToast("Image is not deleted");
                    }
                }, throwable -> {
                    getViewState().showToast("Image is not deleted");
                    Timber.d(throwable);
                });
    }


    public void loadDownloadedImagesPathes() {
        this.downloadedImagePathesList = downloadedListSaver.getDownloadedFilePathesList();
    }


    public void saveDownloadedList() {
        if (downloadedImagePathesList != null)
            downloadedListSaver.saveDownloadedFilePathesList(downloadedImagePathesList);
    }

}
