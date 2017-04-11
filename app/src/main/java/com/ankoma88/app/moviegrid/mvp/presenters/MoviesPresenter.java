package com.ankoma88.app.moviegrid.mvp.presenters;


import com.ankoma88.app.moviegrid.App;
import com.ankoma88.app.moviegrid.api.ApiService;
import com.ankoma88.app.moviegrid.mvp.models.FooterItem;
import com.ankoma88.app.moviegrid.mvp.models.Item;
import com.ankoma88.app.moviegrid.mvp.models.Movie;
import com.ankoma88.app.moviegrid.mvp.views.MoviesView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import static com.ankoma88.app.moviegrid.api.RxUtils.wrapRequest;

@InjectViewState
public class MoviesPresenter extends MvpPresenter<MoviesView> {
    public static final String TAG = MoviesPresenter.class.getSimpleName();

    public static final int LAST_PAGE = 35;
    public static final int ITEMS_PER_PAGE = 22;

    private int page = 1;

    @Inject ApiService apiService;
    private ArrayList<? super Item> movies = new ArrayList<>();

    public MoviesPresenter() {
        App.getComponent().inject(this);
    }

    public void requestMovies(String apiKey) {
        wrapRequest(apiService.queryGetMovies(apiKey, page)).subscribe(result -> {
            movies.addAll(result.getMovies());
            ArrayList<? super Item> items = new ArrayList<>(movies);
            items.add(new FooterItem());
            getViewState().setAdapter(items, page);
        });
    }

    public void incrementPage() {
        page++;
        if (page > LAST_PAGE) {
            page = LAST_PAGE;
        }

    }
}
