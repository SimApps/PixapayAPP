package com.example.test_androidramihamdi.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "images_table")
public class RecyclerData implements Serializable {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public String id;

    @SerializedName("pageURL")
    @Expose
    @ColumnInfo(name = "pageURL")
    public String pageURL;

    @SerializedName("type")
    @Expose
    @ColumnInfo(name = "type")
    public String type;

    @SerializedName("tags")
    @Expose
    @ColumnInfo(name = "tags")
    public String tags;

    @SerializedName("previewURL")
    @Expose
    @ColumnInfo(name = "previewURL")
    public String previewURL;

    @SerializedName("largeImageURL")
    @Expose
    @ColumnInfo(name = "largeImageURL")
    public String largeImageURL;

    @SerializedName("views")
    @Expose
    @ColumnInfo(name = "views")
    public String views;

    @SerializedName("downloads")
    @Expose
    @ColumnInfo(name = "downloads")
    public String downloads;

    @SerializedName("collections")
    @Expose
    @ColumnInfo(name = "collections")
    public String collections;

    @SerializedName("likes")
    @Expose
    @ColumnInfo(name = "likes")
    public String likes;

    @SerializedName("comments")
    @Expose
    @ColumnInfo(name = "comments")
    public String comments;

    @SerializedName("user_id")
    @Expose
    @ColumnInfo(name = "user_id")
    public String user_id;

    @SerializedName("user")
    @Expose
    @ColumnInfo(name = "user")
    public String user;

    @SerializedName("userImageURL")
    @Expose
    @ColumnInfo(name = "userImageURL")
    public String userImageURL;

    public void setidL(@NonNull String id) {
        this.id = id;
    }

    public RecyclerData(String pageURL, String type, String tags, String previewURL, String largeImageURL, String views, String downloads, String collections, String likes, String comments, String user_id, String user, String userImageURL) {
        this.pageURL = pageURL;
        this.type = type;
        this.tags = tags;
        this.previewURL = previewURL;
        this.largeImageURL = largeImageURL;
        this.views = views;
        this.downloads = downloads;
        this.collections = collections;
        this.likes = likes;
        this.comments = comments;
        this.user_id = user_id;
        this.user = user;
        this.userImageURL = userImageURL;
    }

    public RecyclerData() {
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getPageURL() {
        return pageURL;
    }

    public String getType() {
        return type;
    }

    public String getTags() {
        return tags;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public String getViews() {
        return views;
    }

    public String getDownloads() {
        return downloads;
    }

    public String getCollections() {
        return collections;
    }

    public String getLikes() {
        return likes;
    }

    public String getComments() {
        return comments;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser() {
        return user;
    }

    public String getUserImageURL() {
        return userImageURL;
    }


    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public void setCollections(String collections) {
        this.collections = collections;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecyclerData user = (RecyclerData) o;
        return id.equals(user.id) &&
                Objects.equals(pageURL, user.pageURL) &&
                Objects.equals(type, user.type) &&
                Objects.equals(tags, user.tags) &&
                Objects.equals(previewURL, user.previewURL) &&

                Objects.equals(largeImageURL, user.largeImageURL) &&
                Objects.equals(views, user.views) &&
                Objects.equals(downloads, user.downloads) &&
                Objects.equals(collections, user.collections) &&
                Objects.equals(likes, user.likes) &&
                Objects.equals(comments, user.comments) &&
                Objects.equals(user_id, user.user_id) &&
                Objects.equals(user, user.user) &&
                Objects.equals(userImageURL, user.userImageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pageURL, type, tags, previewURL, largeImageURL, views, downloads, collections, likes, comments, user_id, user, userImageURL);
    }


}
