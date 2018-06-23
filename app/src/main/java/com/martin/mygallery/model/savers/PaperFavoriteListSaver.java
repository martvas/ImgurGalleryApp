package com.martin.mygallery.model.savers;

import java.util.List;

import io.paperdb.Paper;

public class PaperFavoriteListSaver implements FavoriteListSaver {
    private static final String FAV_IMAGES_BOOK = "fav_book";
    private static final String FAV_IMAGES_KEY = "fav_images";

    @Override
    public void saveFavoriteImageUrlLIst(List<String> favoriteImagesUrl) {
        Paper.book(FAV_IMAGES_BOOK).write(FAV_IMAGES_KEY, favoriteImagesUrl);
    }

    @Override
    public List<String> getFavavoriteImagesUrlList() {
        return Paper.book(FAV_IMAGES_BOOK).read(FAV_IMAGES_KEY);
    }
}
