package com.martin.mygallery.presenter;

import com.martin.mygallery.view.downloaded_fragment.DownloadedItemRowView;

public interface DownloadedListPresenter {
    void bindImageListRow(int pos, DownloadedItemRowView downloadedItemRowView);

    int getImagesCountToRv();

    void deleteFromStorage(int position);
}
