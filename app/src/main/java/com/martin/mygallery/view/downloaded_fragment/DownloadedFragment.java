package com.martin.mygallery.view.downloaded_fragment;


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
import com.martin.mygallery.presenter.DownloadedPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DownloadedFragment extends MvpAppCompatFragment implements DownloadedView {
    @InjectPresenter
    DownloadedPresenter downloadedPresenter;

    @BindView(R.id.tv_downl_error)
    TextView tvDownlError;

    @BindView(R.id.pb_downl_loading)
    ProgressBar pbDownlLoading;

    @BindView(R.id.rv_downl_gallery)
    RecyclerView downlRecyclerView;

    DownloadedRVAdapter downloadedRVAdapter;

    @ProvidePresenter
    public DownloadedPresenter provideDownloadedPresenter() {
        DownloadedPresenter downloadedPresenter = new DownloadedPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(downloadedPresenter);
        return downloadedPresenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_downloaded, container, false);
        ButterKnife.bind(this, rootView);
        downlRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        downlRecyclerView.addItemDecoration(new DividerItemDecoration(rootView.getContext(), DividerItemDecoration.VERTICAL));
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        downloadedPresenter.saveDownloadedList();
    }

    @Override
    public void init() {
        downloadedRVAdapter = new DownloadedRVAdapter(downloadedPresenter);
        downlRecyclerView.setAdapter(downloadedRVAdapter);
        downloadedPresenter.loadImagePathesAndUpdateRV();
    }

    @Override
    public void showLoading() {
        pbDownlLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbDownlLoading.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorStr) {
        tvDownlError.setText(errorStr);
    }

    @Override
    public void showToast(String toastText) {
        Toast.makeText(getContext(), toastText, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateRVList() {
        downloadedRVAdapter.notifyDataSetChanged();
    }
}
