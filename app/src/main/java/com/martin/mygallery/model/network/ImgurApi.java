package com.martin.mygallery.model.network;


import com.martin.mygallery.model.entity.ImgurMap.ImgurTagGallery;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ImgurApi {

    @GET("3/gallery/t/{t_name}/viral/year/0")
    Observable<ImgurTagGallery> getGalleryByTag(@Path("t_name") String tagName, @Query("client_id") String clienIdKey);
}
