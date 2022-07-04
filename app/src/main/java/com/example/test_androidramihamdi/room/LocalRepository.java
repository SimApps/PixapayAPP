package com.example.test_androidramihamdi.room;

import androidx.lifecycle.LiveData;

import com.example.test_androidramihamdi.model.RecyclerData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

public class LocalRepository {

    private final ImagesDao imagesDao;

    @Inject
    public LocalRepository(ImagesDao imagesDao) {
        this.imagesDao = imagesDao;
    }


    public Completable addImageRoom(List<RecyclerData> recyclerData) {
        return imagesDao.upsert(recyclerData);
    }

    public Completable deleteImageRoom(RecyclerData recyclerData) {
        return imagesDao.deleteImage(recyclerData);
    }

    public Completable updateImageRoom(RecyclerData recyclerData) {
        return imagesDao.updateRoom(recyclerData);
    }


    public LiveData<List<RecyclerData>> getAllImageRoom() {
        return imagesDao.getAllRoomImages();
    }

    public LiveData<List<RecyclerData>> queryImageRoom(String query) {
        return imagesDao.queryRoomImages(query);
    }

    public Completable deleteListOfUsers(List<RecyclerData> recyclerDataArrayList) {
        return imagesDao.deleteListOfRoomImagess(recyclerDataArrayList);
    }



}
