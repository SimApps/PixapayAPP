package com.example.test_androidramihamdi.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.test_androidramihamdi.model.RecyclerData;

import java.util.List;


import io.reactivex.Completable;

@Dao
public interface ImagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable upsert(List<RecyclerData> user);

    @Delete
    Completable deleteImage(RecyclerData image);

    @Query("select * from images_table order by user desc")
    LiveData<List<RecyclerData>> getAllRoomImages();

    @Update
    Completable updateRoom(RecyclerData user);



    //get image by tags or user value
  //  @Query("select * from images_table where tags like :query or user like :query order by tags desc")
    @Query("select * from images_table where tags like :query order by tags desc")
    LiveData<List<RecyclerData>> queryRoomImages(String query);

    @Delete
    Completable deleteListOfRoomImagess(List<RecyclerData> imageArrayList);
}
