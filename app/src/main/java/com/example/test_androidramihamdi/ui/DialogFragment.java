package com.example.test_androidramihamdi.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test_androidramihamdi.R;
import com.example.test_androidramihamdi.databinding.FragmentDialogBinding;
import com.example.test_androidramihamdi.viewmodel.RetrofitViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DialogFragment extends BottomSheetDialogFragment {
    private FragmentDialogBinding binding;
    RetrofitViewModel retrofitViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         retrofitViewModel = new ViewModelProvider(requireActivity()).get(RetrofitViewModel.class);
        retrofitViewModel.getSingleItemLiveData().observe(getViewLifecycleOwner(), item -> {
            // Update the UI.
            binding.messageTxtVw.setText(getString(R.string.infodetail,item.user,item.tags));
            binding.TitleTxtVw.setText(getString(R.string.seedtail));

            binding.btnOui.setOnClickListener(view1 -> {
                retrofitViewModel.postSingleItemLiveData(item);
                NavHostFragment.findNavController(DialogFragment.this)
                        .navigate(R.id.action_dialogFragment_to_detailFragment);

                dismiss();
            });
        });



        binding.btnNon.setOnClickListener(view12 -> dismiss());



    }
}