package com.example.test_androidramihamdi.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.test_androidramihamdi.R;
import com.example.test_androidramihamdi.databinding.FragmentDetailBinding;
import com.example.test_androidramihamdi.viewmodel.RetrofitViewModel;


public class DetailFragment extends Fragment {

    RetrofitViewModel retrofitViewModel;

    private FragmentDetailBinding binding;

    public DetailFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrofitViewModel = new ViewModelProvider(requireActivity()).get(RetrofitViewModel.class);
        retrofitViewModel.getSingleItemLiveData().observe(getViewLifecycleOwner(), item -> {
            // Update the UI.
            binding.fragmentDetailUserName.setText(item.user);

            binding.fragmentDetailComments.setText(item.comments);
            binding.fragmentDetailLikes.setText(item.likes);
            binding.fragmentDetailTags.setText(item.tags);
            binding.fragmentDetailDownloads.setText(item.downloads);


            Glide.with(requireContext())
                    .load(item.largeImageURL)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.fragmentDetailImage);

        });

    }
}