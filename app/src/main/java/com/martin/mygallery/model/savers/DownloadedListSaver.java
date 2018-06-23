package com.martin.mygallery.model.savers;

import java.util.List;

public interface DownloadedListSaver {
    void saveDownloadedFilePathesList(List<String> downloadedFilePathes);

    List<String> getDownloadedFilePathesList();
}
