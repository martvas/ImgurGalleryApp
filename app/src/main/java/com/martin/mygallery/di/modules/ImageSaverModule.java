package com.martin.mygallery.di.modules;

import com.martin.mygallery.model.image.ImageSaver;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageSaverModule {
    @Provides
    public ImageSaver imageSaver() {
        return new ImageSaver();
    }
}
