
package com.martin.mygallery.model.entity.ImgurMap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("accent")
    private Object mAccent;
    @SerializedName("background_hash")
    private String mBackgroundHash;
    @SerializedName("background_is_animated")
    private Boolean mBackgroundIsAnimated;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("description_annotations")
    private DescriptionAnnotations mDescriptionAnnotations;
    @SerializedName("display_name")
    private String mDisplayName;
    @SerializedName("followers")
    private Long mFollowers;
    @SerializedName("following")
    private Boolean mFollowing;
    @SerializedName("is_promoted")
    private Boolean mIsPromoted;
    @SerializedName("items")
    private List<Item> mItems;
    @SerializedName("logo_destination_url")
    private Object mLogoDestinationUrl;
    @SerializedName("logo_hash")
    private Object mLogoHash;
    @SerializedName("name")
    private String mName;
    @SerializedName("thumbnail_hash")
    private Object mThumbnailHash;
    @SerializedName("thumbnail_is_animated")
    private Boolean mThumbnailIsAnimated;
    @SerializedName("total_items")
    private Long mTotalItems;

    public Object getAccent() {
        return mAccent;
    }

    public void setAccent(Object accent) {
        mAccent = accent;
    }

    public String getBackgroundHash() {
        return mBackgroundHash;
    }

    public void setBackgroundHash(String backgroundHash) {
        mBackgroundHash = backgroundHash;
    }

    public Boolean getBackgroundIsAnimated() {
        return mBackgroundIsAnimated;
    }

    public void setBackgroundIsAnimated(Boolean backgroundIsAnimated) {
        mBackgroundIsAnimated = backgroundIsAnimated;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public DescriptionAnnotations getDescriptionAnnotations() {
        return mDescriptionAnnotations;
    }

    public void setDescriptionAnnotations(DescriptionAnnotations descriptionAnnotations) {
        mDescriptionAnnotations = descriptionAnnotations;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public Long getFollowers() {
        return mFollowers;
    }

    public void setFollowers(Long followers) {
        mFollowers = followers;
    }

    public Boolean getFollowing() {
        return mFollowing;
    }

    public void setFollowing(Boolean following) {
        mFollowing = following;
    }

    public Boolean getIsPromoted() {
        return mIsPromoted;
    }

    public void setIsPromoted(Boolean isPromoted) {
        mIsPromoted = isPromoted;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    public Object getLogoDestinationUrl() {
        return mLogoDestinationUrl;
    }

    public void setLogoDestinationUrl(Object logoDestinationUrl) {
        mLogoDestinationUrl = logoDestinationUrl;
    }

    public Object getLogoHash() {
        return mLogoHash;
    }

    public void setLogoHash(Object logoHash) {
        mLogoHash = logoHash;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Object getThumbnailHash() {
        return mThumbnailHash;
    }

    public void setThumbnailHash(Object thumbnailHash) {
        mThumbnailHash = thumbnailHash;
    }

    public Boolean getThumbnailIsAnimated() {
        return mThumbnailIsAnimated;
    }

    public void setThumbnailIsAnimated(Boolean thumbnailIsAnimated) {
        mThumbnailIsAnimated = thumbnailIsAnimated;
    }

    public Long getTotalItems() {
        return mTotalItems;
    }

    public void setTotalItems(Long totalItems) {
        mTotalItems = totalItems;
    }

}
