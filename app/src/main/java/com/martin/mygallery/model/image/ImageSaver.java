package com.martin.mygallery.model.image;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.martin.mygallery.App;
import com.martin.mygallery.model.common.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import io.reactivex.Observable;

public class ImageSaver {
    private static final String IMAGE_FOLDER_NAME = "imgur_images/";

    @SuppressLint("CheckResult")
    public Observable<String> saveImageToStorage(String urlString) {
        return Observable.create(emitter -> {
            String fileFormat = urlString.contains(".jpg") ? ".jpg" : ".png";
            String imageFilePath = getImageDir() + Utils.MD5(urlString) + fileFormat;
            URL url = new URL(urlString);
            Bitmap imageBitMap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            if (!getImageDir().exists() && !getImageDir().mkdirs()) {
                emitter.onError(new RuntimeException("Failed to create directory: " + getImageDir().toString()));
            }

            File imageFile = new File(imageFilePath);

            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                imageBitMap.compress(fileFormat.equals("jpg") ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onNext(imageFilePath);
            emitter.onComplete();
        });
    }

    public Observable<Boolean> deleteImage(String imageFilePath) {
        return Observable.create(emitter -> {
            File imageFile = new File(imageFilePath);
            if (!imageFile.exists()) {
                emitter.onError(new RuntimeException("File doesn't exist: " + imageFilePath));
            }
            emitter.onNext(imageFile.delete());
            emitter.onComplete();
        });
    }

    private File getImageDir() {
        return new File(App.getInstance().getExternalFilesDir(null) + "/" + IMAGE_FOLDER_NAME);
    }
}
