package com.martin.mygallery;

import com.martin.mygallery.di.DaggerTestComponent;
import com.martin.mygallery.di.TestComponent;
import com.martin.mygallery.di.modules.ModulesForTest;
import com.martin.mygallery.di.modules.TestImgurGalleryRepoModule;
import com.martin.mygallery.model.repo.ImgurGalleryRepo;
import com.martin.mygallery.model.savers.LastSearchTagSaver;
import com.martin.mygallery.presenter.ImgurPresenter;
import com.martin.mygallery.view.imgur_and_favorite.ImgurView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;


public class ImgurPresenterUnitTest {

    @Mock
    ImgurView imgurView;
    private ImgurPresenter imgurPresenter;
    private TestScheduler testScheduler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        imgurPresenter = Mockito.spy(new ImgurPresenter(testScheduler));
        imgurPresenter.attachView(imgurView);

    }

    @Test
    public void onFirstViewAttach() {
        Mockito.verify(imgurView).init();
    }

    @Test
    public void loadImgurGalleryByTagSuccess() {
        String searchTag = "dog";
        TestComponent testComponent = DaggerTestComponent.builder()
                .testImgurGalleryRepoModule(new TestImgurGalleryRepoModule() {
                    @Override
                    public ImgurGalleryRepo imgurGalleryRepo() {
                        ImgurGalleryRepo imgurGalleryRepo = super.imgurGalleryRepo();
                        Mockito.when(imgurGalleryRepo.getGalleryByTag(searchTag)).thenReturn(Observable.just(Arrays.asList("url")));
                        return imgurGalleryRepo;
                    }
                }).build();
        testComponent.inject(imgurPresenter);

        imgurPresenter.loadImgurImagesByTag(searchTag);
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        Mockito.verify(imgurView).hideLoading();
        Mockito.verify(imgurView).updateRVList();

    }

    @Test
    public void loadImgurGalleryByTagFail() {
        String searchTag = "dog";
        TestComponent testComponent = DaggerTestComponent.builder()
                .testImgurGalleryRepoModule(new TestImgurGalleryRepoModule() {
                    @Override
                    public ImgurGalleryRepo imgurGalleryRepo() {
                        ImgurGalleryRepo imgurGalleryRepo = super.imgurGalleryRepo();
                        Mockito.when(imgurGalleryRepo.getGalleryByTag(searchTag)).thenReturn(Observable.error(new RuntimeException("some error")));
                        return imgurGalleryRepo;
                    }
                }).build();
        testComponent.inject(imgurPresenter);

        imgurPresenter.loadImgurImagesByTag(searchTag);
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        Mockito.verify(imgurView).showToast("Failed to get images from Imgur");
    }

    @Test
    public void onPermissionsGrantedTestIfLastSearchTagSaverReturnNull() {
        TestComponent testComponent = DaggerTestComponent.builder()
                .modulesForTest(new ModulesForTest() {
                    @Override
                    public LastSearchTagSaver lastSearchTagSaver() {
                        LastSearchTagSaver lastSearchTagSaver = super.lastSearchTagSaver();
                        Mockito.when(lastSearchTagSaver.getLastSearchTag()).thenReturn(null);
                        return lastSearchTagSaver;
                    }
                }).build();
        testComponent.inject(imgurPresenter);

        imgurPresenter.onPermissionsGranted();

        Mockito.verify(imgurView).showError("Please enter some search query to see images");
    }

    @Test
    public void onPermissionsGrantedTestIfLastSearchTagSaverReturnNotNull() {
        String lastTagName = "dog";

        TestComponent testComponent = DaggerTestComponent.builder()
                .modulesForTest(new ModulesForTest() {
                    @Override
                    public LastSearchTagSaver lastSearchTagSaver() {
                        LastSearchTagSaver lastSearchTagSaver = super.lastSearchTagSaver();
                        Mockito.when(lastSearchTagSaver.getLastSearchTag()).thenReturn(lastTagName);
                        return lastSearchTagSaver;
                    }
                })
                .testImgurGalleryRepoModule(new TestImgurGalleryRepoModule() {
                    @Override
                    public ImgurGalleryRepo imgurGalleryRepo() {
                        ImgurGalleryRepo imgurGalleryRepo = super.imgurGalleryRepo();
                        Mockito.when(imgurGalleryRepo.getGalleryByTag(lastTagName)).thenReturn(Observable.just(Arrays.asList("url")));
                        return imgurGalleryRepo;
                    }
                }).build();
        testComponent.inject(imgurPresenter);

        imgurPresenter.onPermissionsGranted();

        Mockito.verify(imgurPresenter).loadImgurImagesByTag(lastTagName);
    }
}

