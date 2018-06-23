package com.martin.mygallery.view.imgur_and_favorite;


public interface ImgurRowView {
    void setPictureToContainer(String pictureUrl);

    void makeFavoriteStarYellow();

    void makeFavoriteStarTransparent();

    void makeDownloadIconYellow();

    void makeDownloadIconWhite();
}