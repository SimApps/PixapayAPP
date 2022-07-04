package com.example.test_androidramihamdi.paging;

import static com.example.test_androidramihamdi.util.Constants.API_KEY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxPagingSource;

import com.example.test_androidramihamdi.model.RecyclerData;
import com.example.test_androidramihamdi.model.RecyclerList;
import com.example.test_androidramihamdi.network.APIClientPagingToFINISH;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class PixabayPagingSourceToFINISH extends RxPagingSource<Integer, RecyclerData> {

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, RecyclerData> pagingState) {
        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, RecyclerData>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        try {
            // If page number is already there then init page variable with it otherwise we are loading fist page
            int page = loadParams.getKey() != null ? loadParams.getKey() : 1;
            // Send request to server with page number
            return APIClientPagingToFINISH.getAPIInterface()
                    .getImagesFromPixabay(API_KEY,"pink",page)//make query dynamiclly change !!
                    // Subscribe the result
                    .subscribeOn(Schedulers.io())
                    // Map result top List of images
                    .map(RecyclerList::getHits)
                    // Map result to LoadResult Object
                    .map(images -> toLoadResult(images, page))
                    // when error is there return error
                    .onErrorReturn(LoadResult.Error::new);
        } catch (Exception e) {
            // Request ran into error return error
            return Single.just(new LoadResult.Error(e));
        }
    }

    // Method to map Images to LoadResult object
    private LoadResult<Integer, RecyclerData> toLoadResult(List<RecyclerData> images, int page) {
        return new LoadResult.Page(images, page == 1 ? null : page - 1, page + 1);
    }
}
