package com.martin.mygallery.view.imgur_and_favorite;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.martin.mygallery.App;
import com.martin.mygallery.R;
import com.martin.mygallery.presenter.FavoritePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FavoriteFragment extends MvpAppCompatFragment implements FavoriteView {
    @InjectPresenter
    FavoritePresenter favoritePresenter;

    @BindView(R.id.tv_fav_error)
    TextView tvFavError;

    @BindView(R.id.pb_fav_loading)
    ProgressBar pbFavLoading;

    @BindView(R.id.rv_fav_imgur_gallery)
    RecyclerView favoriteRecyclerView;

    ImgurRVAdapter favImgurRVAdapter;

    @ProvidePresenter
    public FavoritePresenter provideFavoritePresenter() {
        FavoritePresenter favoritePresenter = new FavoritePresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(favoritePresenter);
        return favoritePresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, rootView);
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        favoriteRecyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), DividerItemDecoration.VERTICAL));
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        favoritePresenter.saveFavoriteImagesUrlList();
        favoritePresenter.saveDownloadedList();
    }

    @Override
    public void init() {
        favImgurRVAdapter = new ImgurRVAdapter(favoritePresenter);
        favoriteRecyclerView.setAdapter(favImgurRVAdapter);
        favoritePresenter.loadFavoriteUrlList();
        favoritePresenter.loadOrCreateDownloadedImagesList();
        favoritePresenter.loadFavoriteImagesFromSaver();
    }

    @Override
    public void showLoading() {
        pbFavLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbFavLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String errorStr) {
        tvFavError.setText(errorStr);
    }

    @Override
    public void showToast(String toastText) {
        Toast.makeText(getContext(), toastText, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateRVList() {
        favImgurRVAdapter.notifyDataSetChanged();
    }
}
