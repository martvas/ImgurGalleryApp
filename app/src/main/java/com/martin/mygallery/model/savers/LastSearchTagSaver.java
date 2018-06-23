package com.martin.mygallery.model.savers;

public interface LastSearchTagSaver {
    void saveLastSearchTag(String lastTagString);

    String getLastSearchTag();
}
