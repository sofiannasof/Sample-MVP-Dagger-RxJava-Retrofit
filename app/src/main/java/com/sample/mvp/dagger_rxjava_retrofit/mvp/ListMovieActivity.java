package com.sample.mvp.dagger_rxjava_retrofit.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sample.mvp.dagger_rxjava_retrofit.LoadListener;
import com.sample.mvp.dagger_rxjava_retrofit.R;
import com.sample.mvp.dagger_rxjava_retrofit.adapter.HomeAdapter;
import com.sample.mvp.dagger_rxjava_retrofit.data.AppRemoteDataStore;
import com.sample.mvp.dagger_rxjava_retrofit.data.MovieApplication;

import javax.inject.Inject;

/**
 * Created by smenesid on 21/11/2016.
 */

public class ListMovieActivity extends AppCompatActivity implements MovieContract.View, LoadListener {

    private Movie movie;
    private MovieContract.Presenter listMovieActivityPresenter;
    private HomeAdapter mHomeAdapter;
    private int mCurrentMoviePageNumber = 0;
    private ProgressBar mProgressBar;

    @Inject
    AppRemoteDataStore appRemoteDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MovieApplication.getAppComponent().inject(this);
        new ListMoviePresenter(appRemoteDataStore, this);

        fetchPage();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mHomeAdapter = new HomeAdapter();

        //Calling loadMore function in Runnable to fix the
        // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
        mHomeAdapter.setOnMovieClickedListener(() -> mRecyclerView.post(() -> fetchPage()));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mHomeAdapter);
    }

    private void fetchPage() {
        mCurrentMoviePageNumber++;
        listMovieActivityPresenter.loadMovieDetails(mCurrentMoviePageNumber);
        if(movie!=null) {
            mHomeAdapter.addData(movie.getResults());
        }
    }

    @Override
    public void showMovieDetails(Movie movie) {
        mProgressBar.setVisibility(View.VISIBLE);
        this.movie = movie;
        mHomeAdapter.addData(movie.getResults());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error loading movies", Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showComplete() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(MovieContract.Presenter presenter) {
        listMovieActivityPresenter = presenter;
    }

    @Override
    public void onLoadMoreData() {
        mProgressBar.setVisibility(View.VISIBLE);
        fetchPage();
    }
}

