package com.sample.mvp.dagger_rxjava_retrofit.data;

import android.app.Application;

import com.sample.mvp.dagger_rxjava_retrofit.di.component.AppComponent;
import com.sample.mvp.dagger_rxjava_retrofit.di.component.DaggerAppComponent;
import com.sample.mvp.dagger_rxjava_retrofit.di.modules.AppModule;
import com.sample.mvp.dagger_rxjava_retrofit.di.modules.DataModule;

/**
 * Created by smenesid on 21/11/2016.
 */

public class MovieApplication extends Application {

    private static AppComponent mAppComponent;
    public static final String BASE_URL = "http://api.themoviedb.org";
    public static final String API_KEY = "8d0bfb53810fbf4ece39a23ffe265a60";


    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule(BASE_URL))
                .build();
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}