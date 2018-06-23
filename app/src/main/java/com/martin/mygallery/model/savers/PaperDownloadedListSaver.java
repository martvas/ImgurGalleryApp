package com.martin.mygallery.model.savers;

import java.util.List;

import io.paperdb.Paper;

public class PaperDownloadedListSaver implements DownloadedListSaver {
    private static final String DOWNL_IMAGES_BOOK = "downloaded_book";
    private static final String DOWNL_IMAGES_KEY = "donwloaded_key";

    @Override
    public void saveDownloadedFilePathesList(List<String> downloadedFilePathes) {
        Paper.book(DOWNL_IMAGES_BOOK).write(DOWNL_IMAGES_KEY, downloadedFilePathes);
    }

    @Override
    public List<String> getDownloadedFilePathesList() {
        return Paper.book(DOWNL_IMAGES_BOOK).read(DOWNL_IMAGES_KEY);
    }
}
