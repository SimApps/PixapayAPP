package com.example.test_androidramihamdi.viewmodel;


import android.app.Application;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;



import com.example.test_androidramihamdi.R;
import com.example.test_androidramihamdi.model.RecyclerData;
import com.example.test_androidramihamdi.room.LocalRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class RoomViewModel extends AndroidViewModel {
     public LiveData<List<RecyclerData>> recyclerDataList;
    private final LocalRepository repository;

    public LiveData<List<RecyclerData>> queriedRecyclerDataList;

    @Inject
    public RoomViewModel(@NonNull Application application, LocalRepository repository) {
        super(application);
        this.repository = repository;
    }

    public void init() {
        recyclerDataList =   repository.getAllImageRoom();
    }

    public void queryImageRoomInit(String query) {
        queriedRecyclerDataList = repository.queryImageRoom(query);
    }


    private Toast toast;

    private static final MutableLiveData<String> queryString = new MutableLiveData<>();

    public static void setQueryString(String query) {
        queryString.setValue(query);
    }

    public LiveData<String> getQueryString() {
        return queryString;
    }



    public void addImage(List<RecyclerData> recyclerData) {

        repository.addImageRoom(recyclerData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        Log.d("TAG", "Inside onSubscribe of addImage in ViewModel");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "Inside onComplete of addImage in ViewModel");
                      //  successToast("cache data added successfully");
                        queriedRecyclerDataList = null;

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "Inside onError of addImage in ViewModel." + e.getMessage());
                        failureToast("TAG  "+e.getMessage());
                    }
                });
    }

    public void deleteImage(RecyclerData recyclerData) {

        repository.deleteImageRoom(recyclerData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("TAG", "Inside onSubscribe of deleteImage in ViewModel");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "Inside onComplete of deleteImage in ViewModel");
                        successToast("deleteImage data removed successfully");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "Inside onError of deleteImage in ViewModel");
                        failureToast(e.getMessage());
                    }
                });
    }

    public void updateImage(RecyclerData recyclerData) {

        repository.updateImageRoom(recyclerData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("TAG", "Inside onSubscribe of updateImage in ViewModel");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "Inside onComplete of updateImage in ViewModel");
                        successToast("updateImage Data updated successfully");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "Inside onError of updateImage in ViewModel");
                        failureToast(e.getMessage());
                    }
                });
    }


    public void deleteListOfImages(List<RecyclerData> imageArrayList) {
        Log.e("TAG", "deleteListOfImages: ---> " + imageArrayList.size());
        repository.deleteListOfUsers(imageArrayList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        Log.d("TAG", "Inside onSubscribe of deleteListOfImages in ViewModel");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "Inside onComplete of deleteListOfImages in ViewModel");
                        successToast("deleteListOfImages Data deleted successfully");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.d("TAG", "Inside onError of deleteListOfImages in ViewModel");
                    }
                });
    }


    private void successToast(String message) {

        if (toast != null)
            toast.cancel();

        toast = Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT);
        View view = toast.getView();

        view.getBackground().setColorFilter(ContextCompat.getColor(getApplication(), R.color.teal_200), PorterDuff.Mode.SRC_IN);

        toast.show();
    }

    private void failureToast(String message) {

        if (toast != null)
            toast.cancel();

        toast = Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT);
        View view = toast.getView();

        view.getBackground().setColorFilter(ContextCompat.getColor(getApplication(), R.color.orange), PorterDuff.Mode.SRC_IN);

        toast.show();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
