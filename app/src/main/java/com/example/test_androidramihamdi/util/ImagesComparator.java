package com.example.test_androidramihamdi.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.test_androidramihamdi.model.RecyclerData;

/*
    Comparator for comparing Images object to avoid duplicates Paging version
 */
public class ImagesComparator extends DiffUtil.ItemCallback<RecyclerData> {
    @Override
    public boolean areItemsTheSame(@NonNull RecyclerData oldItem, @NonNull RecyclerData newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull RecyclerData oldItem, @NonNull RecyclerData newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
}
