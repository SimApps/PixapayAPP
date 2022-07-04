package com.example.test_androidramihamdi.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.test_androidramihamdi.R;
import com.example.test_androidramihamdi.adapter.RecyclerViewAdapter;
import com.example.test_androidramihamdi.adapter.RecyclerViewClickInterface;
import com.example.test_androidramihamdi.databinding.FragmentMainBinding;
import com.example.test_androidramihamdi.model.RecyclerData;
import com.example.test_androidramihamdi.network.InternetCheck;
import com.example.test_androidramihamdi.util.GridSpace;
import com.example.test_androidramihamdi.viewmodel.RetrofitViewModel;
import com.example.test_androidramihamdi.viewmodel.RoomViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@AndroidEntryPoint
public class MainFragment extends Fragment implements RecyclerViewClickInterface {
    Snackbar snackbar;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private FragmentMainBinding binding;

    private RecyclerViewAdapter recyclerViewAdapter;
    RetrofitViewModel retrofitViewModel;
    RoomViewModel roomViewModel;

    // RetrofitWithPagingViewModel retrofitWithPagingViewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retrofitViewModel = new ViewModelProvider(requireActivity()).get(RetrofitViewModel.class);
        roomViewModel = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);

        // Create ViewModel WHEN USING PAGING TO FINISH LATER
        //   retrofitWithPagingViewModel = new ViewModelProvider(requireActivity()).get(RetrofitWithPagingViewModel.class);


        initRecyclerView();
        // Subscribe to to paging data To finish
    /*    retrofitWithPagingViewModel.imagePagingDataFlowable.subscribe(imagePagingData -> {
            // submit new data to recyclerview adapter

            recyclerViewAdapter.submitData(getLifecycle(), imagePagingData);
            recyclerViewAdapter.notifyDataSetChanged();
        });*/


        observeQueryInputStringChange();


        Observable.create((ObservableOnSubscribe<String>) emitter -> binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        binding.searchView.clearFocus();
                        retrofitViewModel.makeApiCall(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        //only search when offline and searsh string >2
                        if (!emitter.isDisposed() && !InternetCheck.isInternetAvailable(requireContext())
                                && newText.length() > 2)
                            emitter.onNext(newText);

                        return false;
                    }
                }))
                .subscribeOn(Schedulers.io())
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull String queryString) {
                        RoomViewModel.setQueryString(queryString);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        compositeDisposable.clear();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissSnackbar();
    }

    @Override
    public void onResume() {
        super.onResume();
        ObserveRetrofitCall();
    }

    private void initRecyclerView() {
        binding.recyclerView.setHasFixedSize(true);
        //  StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        // staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        // binding.recyclerView.setLayoutManager(staggeredGridLayoutManager);

// Create GridlayoutManger with span of count of 2
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);
        // Finally set LayoutManger to recyclerview
        binding.recyclerView.setLayoutManager(gridLayoutManager);


        recyclerViewAdapter = new RecyclerViewAdapter(this);

        // Add ItemDecoration to add space between recyclerview items
        binding.recyclerView.addItemDecoration(new GridSpace(2, 20, true));

        binding.recyclerView.setAdapter(recyclerViewAdapter);
//when using paging to finish later
    /*    binding.recyclerView.setAdapter(
                // This will show end user a progress bar while pages are being requested from server
                recyclerViewAdapter.withLoadStateFooter(
                        // When we will scroll down and next page request will be sent
                        // while we get response form server Progress bar will show to end user
                        new RecyclersLoadStateAdapter(v -> {
                            recyclerViewAdapter.retry();
                        }))
        );


        //
        //imagesAdapter.addLoadStateListener();
        // set Grid span to set progress at center
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // If progress will be shown then span size will be 1 otherwise it will be 2
                return recyclerViewAdapter.getItemViewType(position) == RecyclerViewAdapter.LOADING_ITEM ? 1 : 2;
            }
        });*/
    }

    private void fillRecyclerView(List<RecyclerData> recyclerData) {
        recyclerViewAdapter.setListItems(recyclerData);
        recyclerViewAdapter.notifyDataSetChanged();
    }


    private void ObserveRetrofitCall() {
        retrofitViewModel.getLiveData().observe(getViewLifecycleOwner(), recyclerData -> {
            switch (recyclerData.getStatus()) {
                case SUCCESS:

                    if (recyclerData.getData() != null) {

                        dismissSnackbar();
                        setprogressbarVisibility(false);

                        List<RecyclerData> reclerDtaList = recyclerData.getData();
                        roomViewModel.addImage(reclerDtaList);

                        fillRecyclerView(reclerDtaList);

                        if(reclerDtaList.size()==0) initSnackbar(getString(R.string.noResult), getString(R.string.success));

                    }

                    break;

                case ERROR:
                    if (recyclerData.getError() != null) {
                        initSnackbar(recyclerData.getError().toString(), binding.searchView.getQuery().toString());
                        //Load data from Room
                        queryRoomInit(binding.searchView.getQuery().toString());
                        observeForRoomChanges();
                    }


                    break;

                case LOADING:
                    if (recyclerData.loading() != null) setprogressbarVisibility(true);
                    break;
            }

        });

    }


    private void queryRoomInit(String query) {
        query = "%" + query + "%";
        roomViewModel.queryImageRoomInit(query);
    }

    private void observeForRoomChanges() {

        // if (!InternetCheck.isInternetAvailable(requireContext())){ //Load cached data onlywhen wifi and mobile data off

        roomViewModel.queriedRecyclerDataList.observe(getViewLifecycleOwner(), new Observer<List<RecyclerData>>() {
            @Override
            public void onChanged(List<RecyclerData> recyclerData) {
                fillRecyclerView(recyclerData);

                setprogressbarVisibility(false);

                if(recyclerData.size()==0) initSnackbar(getString(R.string.noResult), getString(R.string.success));
                removeRoomObserver();
            }
        });
        //   }



    }

    @Override
    public void onItemClick(RecyclerData listItems) {
        retrofitViewModel.postSingleItemLiveData(listItems);
        NavHostFragment.findNavController(MainFragment.this)
                .navigate(R.id.action_FirstFragment_to_dialogFragment);
    }


    private void initSnackbar(String message, String queryvalue) {
        snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.reessaye, v -> {
                    if (InternetCheck.isInternetAvailable(v.getContext())) {
                        initSnackbar(getString(R.string.erreur_conexion, ""), queryvalue);
                    }
                        if(Objects.equals(message, getString(R.string.noResult))) {
                            binding.searchView.setQuery(getString(R.string.success),true);
                            binding.searchView.setQueryHint(getString(R.string.success));
                        }
                        else {
                            binding.searchView.setQuery(queryvalue,true);
                            binding.searchView.setQueryHint(queryvalue);
                        }






                });
        snackbar.show();


    }

    private void removeRoomObserver() {
        if(roomViewModel.queriedRecyclerDataList!=null)
            roomViewModel.queriedRecyclerDataList.removeObservers(getViewLifecycleOwner());
    }

    private void observeQueryInputStringChange() {

        if (roomViewModel != null) {
            roomViewModel.getQueryString().observe(getViewLifecycleOwner(), query -> {
                binding.searchView.setQuery(query,true);
            });
        }
    }

    private void dismissSnackbar() {
        if (snackbar != null) snackbar.dismiss();
    }

    private void setprogressbarVisibility(boolean visible) {
        if (visible) binding.progressBar.setVisibility(View.VISIBLE);
        else binding.progressBar.setVisibility(View.GONE);
    }

}