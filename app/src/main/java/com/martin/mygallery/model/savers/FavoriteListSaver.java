package com.martin.mygallery.model.savers;

import java.util.List;

public interface FavoriteListSaver {
    void saveFavoriteImageUrlLIst(List<String> favoriteImagesUrl);

    List<String> getFavavoriteImagesUrlList();
}
