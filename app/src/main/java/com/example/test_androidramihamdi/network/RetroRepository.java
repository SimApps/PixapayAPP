package com.example.test_androidramihamdi.network;

import static com.example.test_androidramihamdi.util.Constants.API_KEY;

import androidx.annotation.NonNull;

import com.example.test_androidramihamdi.model.RecyclerData;
import com.example.test_androidramihamdi.model.RecyclerList;
import com.example.test_androidramihamdi.model.StateLiveData;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroRepository {



    @Inject
     RetroServiceInterface retroServiceInterface;

    public RetroRepository(RetroServiceInterface retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }


    public  void makeAPICall(String query, StateLiveData<List<RecyclerData>> responseListLiveData){
        responseListLiveData.postLoading();

        Call<RecyclerList> call = retroServiceInterface.getImagesFromPixabay(API_KEY,query);

        call.enqueue(new Callback<RecyclerList>() {
            @Override
            public void onResponse(@NonNull Call<RecyclerList> call, @NonNull Response<RecyclerList> response) {
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        responseListLiveData.postSuccess(response.body().getHits());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecyclerList> call, @NonNull Throwable t) {
                responseListLiveData.postError(t);
            }
        });


    }


}
