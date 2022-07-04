package com.example.test_androidramihamdi.di;


import android.content.Context;

import androidx.room.Room;

import com.example.test_androidramihamdi.room.Database;
import com.example.test_androidramihamdi.room.ImagesDao;
import com.example.test_androidramihamdi.room.LocalRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static Database provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, Database.class, "pixabay_database")
                .build();
    }

    @Provides
    @Singleton
    public static ImagesDao provideImageDao(Database db) {
        return db.imageDao();
    }



    @Provides
    @Singleton
    public static LocalRepository provideLocalRepository(ImagesDao imagesDao) {
        return new LocalRepository(imagesDao);
    }
}

