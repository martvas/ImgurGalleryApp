package com.martin.mygallery.di.modules;

import com.martin.mygallery.model.network.ImgurApi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Module
public class ApiModule {
    @Provides
    public ImgurApi api(Retrofit retrofit) {
        return retrofit.create(ImgurApi.class);
    }

    @Named("baseurl")
    @Provides
    public String baseUrl() {
        return "https://api.imgur.com/";
    }

    @Provides
    public Retrofit retrofit(@Named("baseurl") String baseUrl, OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    public HttpLoggingInterceptor interceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    public RxJava2CallAdapterFactory rxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }
}
