package com.sample.mvp.dagger_rxjava_retrofit.mvp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.sample.mvp.dagger_rxjava_retrofit.MovieListener;
import com.sample.mvp.dagger_rxjava_retrofit.R;
import com.sample.mvp.dagger_rxjava_retrofit.adapter.HomeAdapter;
import com.sample.mvp.dagger_rxjava_retrofit.data.AppRemoteDataStore;
import com.sample.mvp.dagger_rxjava_retrofit.data.MovieApplication;

import javax.inject.Inject;

/**
 * Created by smenesid on 21/11/2016.
 */

public class ListMovieActivity extends AppCompatActivity implements MovieContract.View, MovieListener {

    private Movie movie;
    private MovieContract.Presenter listMovieActivityPresenter;
    private SwipeRefreshLayout refreshLayout;
    private HomeAdapter mHomeAdapter;
    private int mCurrentMoviePageNumber = 1;

    @Inject
    AppRemoteDataStore appRemoteDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MovieApplication.getAppComponent().inject(this);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mHomeAdapter = new HomeAdapter();

        //Calling loadMore function in Runnable to fix the
        // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
        mHomeAdapter.setOnMovieClickedListener(() -> mRecyclerView.post(() -> {
            fetchPage();
        }));

        new ListMoviePresenter(appRemoteDataStore, this);
        listMovieActivityPresenter.loadMovieDetails(mCurrentMoviePageNumber);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mHomeAdapter);

        if(movie!=null)
            mHomeAdapter.addData(movie.getResults());

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.photo_refresh);
        refreshLayout.setOnRefreshListener(() -> refreshCards());


        Button bClear = (Button) findViewById(R.id.button_clear);
        bClear.setOnClickListener(v -> {
            mCurrentMoviePageNumber = 0;
            mHomeAdapter.clear();
        });

        Button bFetch = (Button) findViewById(R.id.button_fetch);
        bFetch.setOnClickListener(v -> {
            fetchPage();
        });
    }

    private void fetchPage() {
        mCurrentMoviePageNumber++;
        listMovieActivityPresenter.loadMovieDetails(mCurrentMoviePageNumber);
        if(movie!=null)
            mHomeAdapter.addData(movie.getResults());
    }

    private void refreshCards() {
        mCurrentMoviePageNumber = 0;
        mHomeAdapter.clear();
        fetchPage();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showMovieDetails(Movie movie) {
        this.movie = movie;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error loading movies", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComplete() {

    }

    @Override
    public void setPresenter(MovieContract.Presenter presenter) {
        listMovieActivityPresenter = presenter;
    }

    @Override
    public void onLoadMoreData() {
        fetchPage();
    }
}

