package com.sample.mvp.dagger_rxjava_retrofit.api;

import com.sample.mvp.dagger_rxjava_retrofit.mvp.Movie;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by smenesid on 21/11/2016.
 */

public interface MovieService {

    @GET("/3/movie/popular")
    Observable<Movie> getMoviesPopular(@Query("api_key") String apiKey, @Query("page") int page);

}
