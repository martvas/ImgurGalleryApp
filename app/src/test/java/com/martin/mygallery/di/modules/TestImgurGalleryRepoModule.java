package com.martin.mygallery.di.modules;

import com.martin.mygallery.model.repo.ImgurGalleryRepo;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class TestImgurGalleryRepoModule {
    @Provides
    public ImgurGalleryRepo imgurGalleryRepo() {
        return Mockito.mock(ImgurGalleryRepo.class);
    }
}
