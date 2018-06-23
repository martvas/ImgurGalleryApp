package com.martin.mygallery.model.savers;

import io.paperdb.Paper;

public class PaperLastSearchTagSaver implements LastSearchTagSaver {
    private static final String TAGNAME = "tagname";
    private static final String LAST_TAG = "last_tag";

    @Override
    public void saveLastSearchTag(String lastTag) {
        Paper.book(TAGNAME).write(LAST_TAG, lastTag);
    }

    @Override
    public String getLastSearchTag() {
        return Paper.book(TAGNAME).read(LAST_TAG);
    }
}
