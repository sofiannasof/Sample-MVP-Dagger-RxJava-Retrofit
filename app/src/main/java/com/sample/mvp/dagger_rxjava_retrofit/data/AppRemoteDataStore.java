package com.sample.mvp.dagger_rxjava_retrofit.data;

import android.util.Log;

import com.sample.mvp.dagger_rxjava_retrofit.api.MovieService;
import com.sample.mvp.dagger_rxjava_retrofit.mvp.Movie;

import javax.inject.Inject;

import retrofit.RestAdapter;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by smenesid on 21/11/2016.
 */

public class AppRemoteDataStore implements MovieService {

    @Inject
    RestAdapter retrofit;

    public AppRemoteDataStore() {
        MovieApplication.getAppComponent().inject(this);
    }

    @Override
    public Observable<Movie> getMoviesPopular(@Query("api_key") String apiKey) {
        Log.d("REMOTE", "Loaded movies");
        Observable<Movie> call = null;
        if (retrofit != null) {
            MovieService apiService = retrofit.create(MovieService.class);

            call = apiService.getMoviesPopular(apiKey) ;
        }
        return call;
    }
}
