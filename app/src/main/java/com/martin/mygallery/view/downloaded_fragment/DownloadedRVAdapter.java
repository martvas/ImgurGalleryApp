package com.martin.mygallery.view.downloaded_fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.martin.mygallery.R;
import com.martin.mygallery.presenter.DownloadedPresenter;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadedRVAdapter extends RecyclerView.Adapter<DownloadedRVAdapter.DownloadedViewHolder> {
    private DownloadedPresenter downloadedPresenter;

    public DownloadedRVAdapter(DownloadedPresenter downloadedPresenter) {
        this.downloadedPresenter = downloadedPresenter;
    }

    @NonNull
    @Override
    public DownloadedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DownloadedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_downloaded, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadedViewHolder holder, int position) {
        downloadedPresenter.bindImageListRow(position, holder);
        holder.addDeleteListener(v -> {
            downloadedPresenter.deleteFromStorage(position);
        });
    }

    @Override
    public int getItemCount() {
        return downloadedPresenter.getImagesCountToRv();
    }


    class DownloadedViewHolder extends RecyclerView.ViewHolder implements DownloadedItemRowView {

        @BindView(R.id.iv_downloaded)
        ImageView ivDownloadedImage;

        @BindView(R.id.ic_delete_image)
        ImageView ivDeleteImage;

        DownloadedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void addDeleteListener(View.OnClickListener listener) {
            ivDeleteImage.setOnClickListener(listener);
        }

        @Override
        public void setPictureToContainer(String pictureFilePath) {
            Glide.with(ivDownloadedImage.getContext()).load(new File(pictureFilePath)).into(ivDownloadedImage);
        }
    }
}
