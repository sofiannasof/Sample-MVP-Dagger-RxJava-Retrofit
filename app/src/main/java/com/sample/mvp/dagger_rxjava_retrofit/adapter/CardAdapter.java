package com.sample.mvp.dagger_rxjava_retrofit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.mvp.dagger_rxjava_retrofit.R;
import com.sample.mvp.dagger_rxjava_retrofit.mvp.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smenesid on 21/11/2016.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    List<Movie.Results> mItems;

    public CardAdapter() {
        super();
        mItems = new ArrayList<Movie.Results>();
    }

    public void addData(List<Movie.Results> movie) {
        mItems = movie;
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view, viewGroup, false);
        CardAdapter.ViewHolder viewHolder = new CardAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder viewHolder, int i) {
        Movie.Results movie = mItems.get(i);
        viewHolder.login.setText(movie.getOriginal_title());
        viewHolder.repos.setText("rating: " + movie.getVote_average());
        viewHolder.blog.setText("date: " + movie.getRelease_date());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView login;
        public TextView repos;
        public TextView blog;

        public ViewHolder(View itemView) {
            super(itemView);
            login = (TextView) itemView.findViewById(R.id.login);
            repos = (TextView) itemView.findViewById(R.id.repos);
            blog = (TextView) itemView.findViewById(R.id.blog);
        }
    }
}
