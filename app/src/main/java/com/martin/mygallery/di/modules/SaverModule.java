package com.martin.mygallery.di.modules;

import com.martin.mygallery.model.savers.DownloadedListSaver;
import com.martin.mygallery.model.savers.FavoriteListSaver;
import com.martin.mygallery.model.savers.LastSearchTagSaver;
import com.martin.mygallery.model.savers.PaperDownloadedListSaver;
import com.martin.mygallery.model.savers.PaperFavoriteListSaver;
import com.martin.mygallery.model.savers.PaperLastSearchTagSaver;

import dagger.Module;
import dagger.Provides;

@Module
public class SaverModule {
    @Provides
    DownloadedListSaver downloadedListSaver() {
        return new PaperDownloadedListSaver();
    }

    @Provides
    FavoriteListSaver favoriteListSaver() {
        return new PaperFavoriteListSaver();
    }

    @Provides
    LastSearchTagSaver lastSearchTagSaver() {
        return new PaperLastSearchTagSaver();
    }
}
