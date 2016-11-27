package com.sample.mvp.dagger_rxjava_retrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.mvp.dagger_rxjava_retrofit.LoadListener;
import com.sample.mvp.dagger_rxjava_retrofit.R;
import com.sample.mvp.dagger_rxjava_retrofit.mvp.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smenesid on 21/11/2016.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ItemViewHolder> {

    List<Movie.Results> mMovies;
    private LoadListener mLoadListener;
    private Context mContext;
    private boolean mLoading;
    private int mLastMoviesCount = 0;

    public HomeAdapter() {
        super();
        mMovies = new ArrayList<>();
    }

    public void addData(List<Movie.Results> movie) {
        mMovies.addAll(movie);
        mLoading = false;
        notifyDataSetChanged();
    }

    public void clear() {
        mMovies.clear();
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_view, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {

        if(canLoadMoreMovies(i)) {
            if(mLoadListener != null) {
                mLoadListener.onLoadMoreData();
                mLastMoviesCount = mMovies.size();
                mLoading = true;
            }
        } else {
            final Movie.Results movie = mMovies.get(i);
            itemViewHolder.login.setText(movie.getOriginal_title());
            itemViewHolder.repos.setText("rating: " + movie.getVote_average());
            itemViewHolder.blog.setText("date: " + movie.getRelease_date());
        }
    }

    private boolean canLoadMoreMovies(int currentPosition) {
        return !mLoading && currentPosition == mMovies.size() - 1 &&
                mLastMoviesCount != mMovies.size();
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView login;
        public TextView repos;
        public TextView blog;

        public ItemViewHolder(View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.login);
            repos = (TextView) itemView.findViewById(R.id.repos);
            blog = (TextView) itemView.findViewById(R.id.blog);
        }
    }

    public void setOnMovieClickedListener(LoadListener loadListener) {
        this.mLoadListener = loadListener;
    }
}
