package com.martin.mygallery.di.modules;

import com.martin.mygallery.model.image.ImageSaver;
import com.martin.mygallery.model.savers.DownloadedListSaver;
import com.martin.mygallery.model.savers.FavoriteListSaver;
import com.martin.mygallery.model.savers.LastSearchTagSaver;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class ModulesForTest {

    @Provides
    public FavoriteListSaver favoriteListSaver() {
        return Mockito.mock(FavoriteListSaver.class);
    }

    @Provides
    public ImageSaver imageSaver() {
        return Mockito.mock(ImageSaver.class);
    }

    @Provides
    public DownloadedListSaver downloadedListSaver() {
        return Mockito.mock(DownloadedListSaver.class);
    }

    @Provides
    public LastSearchTagSaver lastSearchTagSaver() {
        return Mockito.mock(LastSearchTagSaver.class);

    }
}

