package com.martin.mygallery.view.imgur_and_favorite;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.martin.mygallery.presenter.ImgurPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ImgurFragment extends MvpAppCompatFragment implements ImgurView {

    private static final int PERMISSIONS_REQUEST_ID = 0;
    private static final String[] permissons = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @InjectPresenter
    ImgurPresenter imgurPresenter;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    @BindView(R.id.rv_imgur_gallery)
    RecyclerView imgurRecyclerView;

    ImgurRVAdapter imgurRVAdapter;
    SearchView searchView;

    @ProvidePresenter
    public ImgurPresenter provideImgurPresenter() {
        ImgurPresenter imgurPresenter = new ImgurPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(imgurPresenter);
        return imgurPresenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ID: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPermissionsGranted();
                } else {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.permissons_required)
                            .setMessage(R.string.permissions_required_message)
                            .setPositiveButton("OK", (dialog, which) -> requestPermissions())
                            .setOnCancelListener(dialog -> requestPermissions())
                            .create()
                            .show();
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_imgur, container, false);
        ButterKnife.bind(this, rootView);

        imgurRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        imgurRecyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), DividerItemDecoration.VERTICAL));

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        imgurPresenter.saveFavoriteImagesUrlList();
        imgurPresenter.saveDownloadedList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(getSearchViewListener());
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void init() {
        imgurRVAdapter = new ImgurRVAdapter(imgurPresenter);
        imgurRecyclerView.setAdapter(imgurRVAdapter);
        checkPermissions();
        imgurPresenter.loadOrCreateFavoriteImagesUrlList();
        imgurPresenter.loadOrCreateDownloadedImagesPathes();
    }

    @Override
    public void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String errorStr) {
        tvError.setText(errorStr);
    }

    @Override
    public void checkPermissions() {
        for (String permission : permissons) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions();
                return;
            }
        }
        onPermissionsGranted();
    }

    @Override
    public void showToast(String toastText) {
        Toast.makeText(getContext(), toastText, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateRVList() {
        imgurRVAdapter.notifyDataSetChanged();
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), permissons, PERMISSIONS_REQUEST_ID);
    }

    private void onPermissionsGranted() {
        imgurPresenter.onPermissionsGranted();
    }

    @NonNull
    private SearchView.OnQueryTextListener getSearchViewListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                imgurPresenter.loadImgurImagesByTag(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {

                } else {

                }
                return true;
            }
        };
    }
}
