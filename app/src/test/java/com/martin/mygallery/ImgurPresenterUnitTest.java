package com.martin.mygallery;

import com.martin.mygallery.di.DaggerTestComponent;
import com.martin.mygallery.di.TestComponent;
import com.martin.mygallery.di.modules.TestImgurGalleryRepoModule;
import com.martin.mygallery.model.repo.ImgurGalleryRepo;
import com.martin.mygallery.presenter.ImgurPresenter;
import com.martin.mygallery.view.imgur_and_favorite.ImgurView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
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
    }

    @Test
    public void onFirstViewAttach() {
        imgurPresenter.attachView(imgurView);
        Mockito.verify(imgurView).init();
    }

    @Test
    public void loadImgurGalleryByTagSuccess() {
        String searchTag = "dog";
        List<String> stringList = Arrays.asList("url");
        TestComponent testComponent = DaggerTestComponent.builder()
                .testImgurGalleryRepoModule(new TestImgurGalleryRepoModule() {
                    @Override
                    public ImgurGalleryRepo imgurGalleryRepo() {
                        ImgurGalleryRepo imgurGalleryRepo = super.imgurGalleryRepo();
                        Mockito.when(imgurGalleryRepo.getGalleryByTag(searchTag)).thenReturn(Observable.just(stringList));
                        return imgurGalleryRepo;
                    }
                }).build();
        testComponent.inject(imgurPresenter);

        imgurPresenter.attachView(imgurView);
        imgurPresenter.loadImgurImagesByTag(searchTag);
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        Mockito.verify(imgurView).hideLoading();
        Mockito.verify(imgurView).setQueryToSearchView("dog");
        Mockito.verify(imgurView).updateRVList();
    }
}

