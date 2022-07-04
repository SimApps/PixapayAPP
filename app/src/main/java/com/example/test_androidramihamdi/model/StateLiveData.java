package com.example.test_androidramihamdi.model;

import androidx.lifecycle.MutableLiveData;

public class StateLiveData<T> extends MutableLiveData<StateData<T>> {


    public void postLoading() {
        postValue(new StateData<T>().loading());
    }


    public void postError(Throwable throwable) {
        postValue(new StateData<T>().error(throwable));
    }


    public void postSuccess(T data) {
        postValue(new StateData<T>().success(data));
    }



}
