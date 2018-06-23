package com.martin.mygallery.view.imgur_and_favorite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.martin.mygallery.R;
import com.martin.mygallery.presenter.ImgurListPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImgurRVAdapter extends RecyclerView.Adapter<ImgurRVAdapter.PhotoViewHolder> {
    private ImgurListPresenter imgurListPresenter;

    public ImgurRVAdapter(ImgurListPresenter imgurListPresenter) {
        this.imgurListPresenter = imgurListPresenter;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_imgur_gallery_and_fav, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.addToFavoriteListener(v -> {
            imgurListPresenter.addOrRemoveFromFavorite(position, holder);
        });
        holder.addDownloadIconListener(v -> {
            holder.makeDownloadIconYellow();
            imgurListPresenter.saveImage(position, holder);
        });
        imgurListPresenter.bindImageListRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return imgurListPresenter.getImagesCountToRV();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder implements ImgurRowView {

        @BindView(R.id.photo_image_view)
        ImageView photoImageView;

        @BindView(R.id.ic_star)
        ImageView starIcon;

        @BindView(R.id.ic_download)
        ImageView downloadIcon;

        PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void addToFavoriteListener(View.OnClickListener listener) {
            starIcon.setOnClickListener(listener);
        }

        public void addDownloadIconListener(View.OnClickListener listener) {
            downloadIcon.setOnClickListener(listener);
        }

        @Override
        public void setPictureToContainer(String pictureUrl) {
            Glide.with(photoImageView.getContext()).load(pictureUrl).into(photoImageView);
        }

        @Override
        public void makeFavoriteStarYellow() {
            starIcon.setImageResource(R.drawable.ic_star_yellow);
        }

        @Override
        public void makeFavoriteStarTransparent() {
            starIcon.setImageResource(R.drawable.ic_star_white);
        }

        @Override
        public void makeDownloadIconYellow() {
            downloadIcon.setImageResource(R.drawable.ic_file_download_yellow_24dp);
        }

        @Override
        public void makeDownloadIconWhite() {
            downloadIcon.setImageResource(R.drawable.ic_file_download_white_24dp);
        }
    }
}
