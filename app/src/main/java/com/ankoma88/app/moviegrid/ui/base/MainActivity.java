package com.ankoma88.app.moviegrid.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ankoma88.app.moviegrid.R;
import com.ankoma88.app.moviegrid.ui.MoviesFragment;


public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private MoviesFragment moviesFragment;


    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (savedInstanceState == null) {
            updateFragment();
        }
    }

    private void updateFragment() {
        if (moviesFragment == null) {
            moviesFragment = MoviesFragment.newInstance();
        }
        showRootFragment(moviesFragment);

    }

    public void showRootFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment, null)
                .commit();
    }

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
