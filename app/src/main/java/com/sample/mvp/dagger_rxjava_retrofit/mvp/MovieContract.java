package com.sample.mvp.dagger_rxjava_retrofit.mvp;

import com.sample.mvp.dagger_rxjava_retrofit.base.BaseView;
import com.sample.mvp.dagger_rxjava_retrofit.base.BasePresenter;

/**
 * Created by smenesid on 21/11/2016.
 */

public class MovieContract {
    public interface View extends BaseView<Presenter> {

        void showMovieDetails(Movie movie);

        void showError(String message);

        void showComplete();
    }

    public interface Presenter extends BasePresenter {
        void loadMovieDetails(int page);
    }
}
