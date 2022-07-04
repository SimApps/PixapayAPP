package com.example.test_androidramihamdi.network;

import com.example.test_androidramihamdi.model.RecyclerList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroServiceInterface {


    @GET("/api/")
    Call<RecyclerList> getImagesFromPixabay(@Query("key") String key,
                                          @Query("q") String query
                                            /*,
                                           @Query("page") int page,
                                           @Query("per_page") int perPage*/);
}
