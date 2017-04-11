package com.ankoma88.app.moviegrid.ui.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.ankoma88.app.moviegrid.App;
import com.ankoma88.app.moviegrid.R;
import com.ankoma88.app.moviegrid.api.ApiService;
import com.arellomobile.mvp.MvpAppCompatFragment;

import javax.inject.Inject;

import butterknife.Unbinder;

import static com.ankoma88.app.moviegrid.util.Utils.checkNotNull;

public class BaseFragment extends MvpAppCompatFragment {
    public static final String TAG = BaseFragment.class.getSimpleName();

    @Inject protected ApiService apiService;
    protected Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != unbinder) unbinder.unbind();
    }

    protected void setToolbarTitle(String title) {
        ActionBar actionBar = getToolBar();
        actionBar.setTitle(title);
    }

    @NonNull
    private ActionBar getToolBar() {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        checkNotNull(actionBar);
        return actionBar;
    }

    protected void showToolbarBack(boolean show) {
        if (show) {
                getToolBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getToolBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    protected void showFragment(Fragment fragment) {
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.showFragment(fragment);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

}
