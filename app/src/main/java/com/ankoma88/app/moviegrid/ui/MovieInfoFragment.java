package com.ankoma88.app.moviegrid.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankoma88.app.moviegrid.R;
import com.ankoma88.app.moviegrid.mvp.models.Movie;
import com.ankoma88.app.moviegrid.ui.base.BaseFragment;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.parceler.Parcels;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.fresco.processors.BlurPostprocessor;

import static com.ankoma88.app.moviegrid.util.Utils.dateFormat;
import static com.ankoma88.app.moviegrid.util.Utils.dateFormatDefault;

public class MovieInfoFragment extends BaseFragment {
    public static final String TAG = MovieInfoFragment.class.getSimpleName();
    private static final String EXTRA_MOVIE = "extraMovies";


    @BindView(R.id.ivBlur) SimpleDraweeView ivBlur;
    @BindView(R.id.llRoot) LinearLayout llRoot;
    @BindView(R.id.ivPoster) SimpleDraweeView ivPoster;
    @BindView(R.id.tvScore) TextView tvScore;
    @BindView(R.id.tvRating) TextView tvRating;
    @BindView(R.id.tvReleaseDate) TextView tvReleaseDate;
    @BindView(R.id.tvTitle) TextView  tvTitle;
    @BindView(R.id.tvDescription) TextView tvDescription;

    private Movie movie;

    public MovieInfoFragment() {
    }

    public static Fragment newInstance(Movie movie) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_MOVIE, Parcels.wrap(movie));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_movie_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (movie == null) {
            movie = Parcels.unwrap(getArguments().getParcelable(EXTRA_MOVIE));
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbarBack(true);
        setHasOptionsMenu(false);

        blurBackgroundWithPoster(movie);
        ivPoster.setImageURI(Uri.parse(getContext().getString(R.string.poster_endpoint) + movie.getPosterPath()));
        tvScore.setText(String.valueOf(movie.getVoteCount()));
        tvRating.setText(String.valueOf(movie.getVoteAverage()));
        tvTitle.setText(movie.getTitle());
        tvDescription.setText(movie.getOverview());
        try {
            tvReleaseDate.setText(dateFormat.format(dateFormatDefault.parse(movie.getReleaseDate())));
        } catch (ParseException e) {
            tvReleaseDate.setText(movie.getReleaseDate());
        }
    }


    private void blurBackgroundWithPoster(Movie movie) {
        ImageRequest request =
                ImageRequestBuilder.newBuilderWithSource(
                        Uri.parse(getContext().getString(R.string.poster_endpoint) + movie.getPosterPath()))
                        .setPostprocessor(new BlurPostprocessor(getContext(), 25))
                        .build();
        PipelineDraweeController controller =
                (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(ivBlur.getController())
                        .build();
        ivBlur.setController(controller);
    }


}
