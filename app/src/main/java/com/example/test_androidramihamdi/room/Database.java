package com.example.test_androidramihamdi.room;

import androidx.room.RoomDatabase;

import com.example.test_androidramihamdi.model.RecyclerData;

@androidx.room.Database(entities = {RecyclerData.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract ImagesDao imageDao();
}

