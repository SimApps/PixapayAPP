package com.example.test_androidramihamdi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test_androidramihamdi.model.RecyclerData;
import com.example.test_androidramihamdi.model.StateData;
import com.example.test_androidramihamdi.model.StateLiveData;
import com.example.test_androidramihamdi.network.RetroRepository;
import com.example.test_androidramihamdi.network.RetroServiceInterface;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RetrofitViewModel extends ViewModel {

    StateLiveData<List<RecyclerData>> responseListLiveData;

    @Inject
    RetroServiceInterface retroServiceInterface;

    @Inject
    public RetrofitViewModel() {
      //  responseListLiveData = new StateLiveData<List<RecyclerData>>();
    }



    private final MutableLiveData<RecyclerData> singleItemLiveData = new MutableLiveData<RecyclerData>();

    public void postSingleItemLiveData(RecyclerData item) {

        singleItemLiveData.setValue(item);
    }

    public LiveData<RecyclerData> getSingleItemLiveData() {

        return singleItemLiveData;
    }

    public StateLiveData<List<RecyclerData>> getLiveData(){
        if(responseListLiveData==null ){
            responseListLiveData = new StateLiveData<>();
            //when app first open or responseListLiveData not initialazed
            makeApiCall("success");
        }

        return responseListLiveData;
    }

    public void makeApiCall(String queryvalue){
        RetroRepository  retroRepository = new RetroRepository(retroServiceInterface);
         retroRepository.makeAPICall(queryvalue,responseListLiveData);
    }

}
