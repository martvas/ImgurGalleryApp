package com.martin.mygallery.di.modules;

import com.martin.mygallery.model.network.ImgurApi;
import com.martin.mygallery.model.repo.ImgurGalleryRepo;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class})
public class ImgurGalleryRepoModule {

    @Provides
    public ImgurGalleryRepo imgurGalleryRepo(ImgurApi imgurApi) {
        return new ImgurGalleryRepo(imgurApi);
    }
}
