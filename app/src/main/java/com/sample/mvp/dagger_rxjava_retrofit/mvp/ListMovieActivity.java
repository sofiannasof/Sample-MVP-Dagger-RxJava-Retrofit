package com.sample.mvp.dagger_rxjava_retrofit.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.sample.mvp.dagger_rxjava_retrofit.R;
import com.sample.mvp.dagger_rxjava_retrofit.adapter.CardAdapter;
import com.sample.mvp.dagger_rxjava_retrofit.data.AppRemoteDataStore;
import com.sample.mvp.dagger_rxjava_retrofit.data.MovieApplication;

import javax.inject.Inject;

/**
 * Created by smenesid on 21/11/2016.
 */

public class ListMovieActivity extends AppCompatActivity implements MovieContract.View {

    private Movie movie;
    private MovieContract.Presenter listMovieActivityPresenter;

    @Inject
    AppRemoteDataStore appRemoteDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MovieApplication.getAppComponent().inject(this);

        new ListMoviePresenter(appRemoteDataStore, this);
        listMovieActivityPresenter.loadMovieDetails();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CardAdapter mCardAdapter = new CardAdapter();
        mRecyclerView.setAdapter(mCardAdapter);

        Button bClear = (Button) findViewById(R.id.button_clear);
        Button bFetch = (Button) findViewById(R.id.button_fetch);
        bClear.setOnClickListener(v -> mCardAdapter.clear());

        //bFetch.setOnClickListener(v -> mCardAdapter.addData(movie.getResults()));

        bFetch.setOnClickListener(v -> {
            listMovieActivityPresenter.loadMovieDetails();
            mCardAdapter.addData(movie.getResults());
        });

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
}

