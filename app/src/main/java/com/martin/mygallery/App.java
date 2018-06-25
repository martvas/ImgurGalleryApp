package com.martin.mygallery;

import android.app.Application;

import com.martin.mygallery.di.AppComponent;
import com.martin.mygallery.di.DaggerAppComponent;
import com.martin.mygallery.di.modules.AppModule;
import com.squareup.leakcanary.LeakCanary;

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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
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
