package com.martin.mygallery.view.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.martin.mygallery.App;
import com.martin.mygallery.R;
import com.martin.mygallery.presenter.MainPresenter;
import com.martin.mygallery.view.downloaded_fragment.DownloadedFragment;
import com.martin.mygallery.view.imgur_and_favorite.FavoriteFragment;
import com.martin.mygallery.view.imgur_and_favorite.ImgurFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    FragmentManager fragmentManager;
    @Inject
    App app;
    @InjectPresenter
    MainPresenter presenter;
    @BindView(R.id.bottom_nav_view)
    BottomNavigationView bnv;

    @ProvidePresenter

    public MainPresenter provideMainPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        bnv.setOnNavigationItemSelectedListener(getBottonNavListener());
    }

    @NonNull
    private BottomNavigationView.OnNavigationItemSelectedListener getBottonNavListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.action_favorite:
                    presenter.showFavoriteFragment();
                    return true;
                case R.id.action_downloads:
                    presenter.showDownloadedFragment();
                    return true;
                case R.id.action_imgur_search:
                default:
                    presenter.showImgurFragment();
                    return true;
            }
        };
    }

    @Override
    public void showImgurFragment() {
        ImgurFragment imgurFragment = new ImgurFragment();
        FragmentTransaction fragmentTransactionImgur = fragmentManager.beginTransaction();
        fragmentTransactionImgur.replace(R.id.container_for_fragment, imgurFragment);
        fragmentTransactionImgur.commit();
    }

    @Override
    public void showFavoriteFragment() {
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        FragmentTransaction fragmentTransactionFavorite = fragmentManager.beginTransaction();
        fragmentTransactionFavorite.replace(R.id.container_for_fragment, favoriteFragment);
        fragmentTransactionFavorite.commit();
    }

    @Override
    public void showDownloadedFragment() {
        DownloadedFragment downloadedFragment = new DownloadedFragment();
        FragmentTransaction fragmentTransactionDownl = fragmentManager.beginTransaction();
        fragmentTransactionDownl.replace(R.id.container_for_fragment, downloadedFragment);
        fragmentTransactionDownl.commit();
    }
}
