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


    public  void makeAPICall(String query, StateLiveData<List<RecyclerData>> responseListLiveData/*,String page*/){
        responseListLiveData.postLoading();

        Call<RecyclerList> call = retroServiceInterface.getImagesFromPixabay(API_KEY,query/*,page*/);
        /**
         * whe can pass page as a prameter fora temporary measure util proper implementation of paging
         * 1 get list siez from recycler list
         * 2 say divide list sieze by item per page
         * 3 populate a rceycler view with number from 1 to result in step 2
         * 4 make call whit recycler view idenx as a page number
         */

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
