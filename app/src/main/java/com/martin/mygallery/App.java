package com.martin.mygallery;

import android.app.Application;

import com.martin.mygallery.di.AppComponent;
import com.martin.mygallery.di.DaggerAppComponent;
import com.martin.mygallery.di.modules.AppModule;

import io.paperdb.Paper;
import timber.log.Timber;

public class App extends Application {
    private static App instance = null;
    private AppComponent appComponent;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Timber.plant(new Timber.DebugTree());
        Paper.init(this);
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
