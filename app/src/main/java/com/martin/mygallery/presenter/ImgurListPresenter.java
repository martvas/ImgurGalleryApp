package com.martin.mygallery.presenter;

import com.martin.mygallery.view.imgur_and_favorite.ImgurRowView;

public interface ImgurListPresenter {
    void bindImageListRow(int pos, ImgurRowView imgurRowView);

    int getImagesCountToRV();

    void addOrRemoveFromFavorite(int position, ImgurRowView imgurRowView);

    void saveImage(int position, ImgurRowView imgurRowView);
}
