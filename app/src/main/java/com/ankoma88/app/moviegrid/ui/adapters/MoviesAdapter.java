package com.ankoma88.app.moviegrid.ui.adapters;


import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ankoma88.app.moviegrid.R;
import com.ankoma88.app.moviegrid.mvp.models.Item;
import com.ankoma88.app.moviegrid.mvp.models.Movie;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter{
    private static final String TAG = MoviesAdapter.class.getSimpleName();
    public static final int MOVIE = 2;
    public static final int FOOTER = 3;


    private FragmentCallback fragmentCallback;
    private ArrayList<? super Item> items = new ArrayList<>();

    public MoviesAdapter(FragmentCallback fragmentCallback, ArrayList<? super Item> movies) {
        this.fragmentCallback = fragmentCallback;
        items = movies;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return FOOTER;
        } else return MOVIE;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
        } else {
            return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == MOVIE) {
                bindMovie((MovieViewHolder) holder, (Movie) items.get(holder.getAdapterPosition()));
        }
        else if (getItemViewType(position) == FOOTER) {
            bindFooter((FooterViewHolder) holder);
        }
    }

    private void bindMovie(MovieViewHolder vh, Movie movie) {
        vh.itemView.setOnClickListener(view -> fragmentCallback.onItemClicked(movie));
        vh.ivPoster.setImageURI(Uri.parse(vh.itemView.getContext().getString(R.string.poster_endpoint) + movie.getPosterPath()));
    }

    private void bindFooter(FooterViewHolder vh) {
        vh.llLoadMore.setOnClickListener(v -> fragmentCallback.onLoadMoreClick());
    }


    static class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivPoster)
        SimpleDraweeView ivPoster;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.llLoadMore) LinearLayout llLoadMore;

        FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface FragmentCallback {
        void onItemClicked(Movie movie);

        void onLoadMoreClick();
    }

}
