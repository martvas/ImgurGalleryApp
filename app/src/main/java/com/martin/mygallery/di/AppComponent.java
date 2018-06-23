package com.martin.mygallery.di;

import com.martin.mygallery.di.modules.AppModule;
import com.martin.mygallery.di.modules.ImageSaverModule;
import com.martin.mygallery.di.modules.ImgurGalleryRepoModule;
import com.martin.mygallery.di.modules.SaverModule;
import com.martin.mygallery.presenter.DownloadedPresenter;
import com.martin.mygallery.presenter.FavoritePresenter;
import com.martin.mygallery.presenter.ImgurPresenter;
import com.martin.mygallery.view.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ImageSaverModule.class, ImgurGalleryRepoModule.class, SaverModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(ImgurPresenter imgurPresenter);

    void inject(FavoritePresenter favoritePresenter);

    void inject(DownloadedPresenter downloadedPresenter);
}
