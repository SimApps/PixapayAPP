package com.example.test_androidramihamdi.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava2.PagingRx;

import com.example.test_androidramihamdi.model.RecyclerData;
import com.example.test_androidramihamdi.paging.PixabayPagingSourceToFINISH;

import io.reactivex.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class RetrofitWithPagingViewModel extends ViewModel {
    // Define Flowable for movies
    //public Flowable<PagingData<RecyclerData>> moviePagingDataFlowable;
    public Flowable<PagingData<RecyclerData>> imagePagingDataFlowable;

    public RetrofitWithPagingViewModel() {
        init();
    }
    // Init ViewModel Data
    private void init() {
        // Define Paging Source
        PixabayPagingSourceToFINISH moviePagingSource = new PixabayPagingSourceToFINISH();

        // Create new Pager
        Pager<Integer, RecyclerData> pager = new Pager(
                // Create new paging config
                new PagingConfig(20, //  Count of items in one page
                        20, //  Number of items to prefetch
                        false, // Enable placeholders for data which is not yet loaded
                        20, // initialLoadSize - Count of items to be loaded initially
                        20 * 499),// maxSize - Count of total items to be shown in recyclerview
                () -> moviePagingSource); // set paging source

        // inti Flowable
        imagePagingDataFlowable = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(imagePagingDataFlowable, coroutineScope);

    }
}
