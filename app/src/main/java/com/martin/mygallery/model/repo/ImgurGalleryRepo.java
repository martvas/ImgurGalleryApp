package com.martin.mygallery.model.repo;

import com.martin.mygallery.model.entity.ImgurMap.Image;
import com.martin.mygallery.model.entity.ImgurMap.Item;
import com.martin.mygallery.model.network.ImgurApi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ImgurGalleryRepo {
    public static final String ITEM_EMPTY_EXCEPTION = "item_empty";
    private static final String API_KEY = "295ac460854bb1f";
    private ImgurApi imgurApi;

    public ImgurGalleryRepo(ImgurApi imgurApi) {
        this.imgurApi = imgurApi;
    }

    public Observable<List<String>> getGalleryByTag(String tag) {
        return imgurApi.getGalleryByTag(tag, API_KEY)
                .subscribeOn(Schedulers.io())
                .map(imgurTagGallery -> {
                    List<Item> itemList = imgurTagGallery.getData().getItems();
                    List<Image> imageList = new ArrayList<>();
                    if (itemList != null) {
                        for (Item item : itemList) {
                            if (item.getImages() != null)
                                imageList.addAll(item.getImages());
                        }
                    } else throw new RuntimeException(ITEM_EMPTY_EXCEPTION);
                    Iterator<Image> iterator = imageList.iterator();
                    while (iterator.hasNext()) {
                        if (iterator.next().getAnimated()) iterator.remove();
                    }

                    List<String> imagesUrlList = new ArrayList<>();

                    for (Image image : imageList) {
                        imagesUrlList.add(image.getLink());
                    }

                    return imagesUrlList.size() > 0 ? imagesUrlList : null;
                });

    }
}
