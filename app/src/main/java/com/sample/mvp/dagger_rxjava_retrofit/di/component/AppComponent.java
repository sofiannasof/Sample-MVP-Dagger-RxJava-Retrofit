package com.sample.mvp.dagger_rxjava_retrofit.di.component;

import com.sample.mvp.dagger_rxjava_retrofit.mvp.ListMovieActivity;
import com.sample.mvp.dagger_rxjava_retrofit.data.AppRemoteDataStore;
import com.sample.mvp.dagger_rxjava_retrofit.di.modules.AppModule;
import com.sample.mvp.dagger_rxjava_retrofit.di.modules.DataModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by smenesid on 21/11/2016.
 */

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {
    void inject(ListMovieActivity activity);
    void inject(AppRemoteDataStore appRemoteDataStore);
}
