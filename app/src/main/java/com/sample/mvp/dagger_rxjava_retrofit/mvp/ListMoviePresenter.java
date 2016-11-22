package com.sample.mvp.dagger_rxjava_retrofit.mvp;

import android.util.Log;

import com.sample.mvp.dagger_rxjava_retrofit.data.AppRemoteDataStore;
import com.sample.mvp.dagger_rxjava_retrofit.data.MovieApplication;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smenesid on 21/11/2016.
 */

public class ListMoviePresenter implements MovieContract.Presenter {
    private Subscription subscription;
    private AppRemoteDataStore appRemoteDataStore;
    private MovieContract.View view;

    public ListMoviePresenter(AppRemoteDataStore appRemoteDataStore, MovieContract.View view) {
        this.appRemoteDataStore = appRemoteDataStore;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadMovieDetails() {
        if (appRemoteDataStore == null) {
            appRemoteDataStore = new AppRemoteDataStore();
        }

        subscription = appRemoteDataStore.getMoviesPopular(MovieApplication.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public final void onCompleted() {
                        Log.d("movie","completed");
                        view.showComplete();
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e("movie",e.getMessage());
                        view.showError(e.toString());
                    }

                    @Override
                    public void onNext(Movie movie) {
                        Log.d("movie","transfer success");
                        view.showMovieDetails(movie);
                    }
                });
    }

    @Override
    public void subscribe() {
        loadMovieDetails();
    }

    @Override
    public void unsubscribe() {

        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();

    }
}
