package com.martin.mygallery.di;

import com.martin.mygallery.di.modules.ModulesForTest;
import com.martin.mygallery.di.modules.TestImgurGalleryRepoModule;
import com.martin.mygallery.presenter.ImgurPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestImgurGalleryRepoModule.class, ModulesForTest.class})
public interface TestComponent {
    void inject(ImgurPresenter imgurPresenter);
}
