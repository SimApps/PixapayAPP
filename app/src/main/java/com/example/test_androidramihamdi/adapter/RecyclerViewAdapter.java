package com.example.test_androidramihamdi.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.test_androidramihamdi.R;
import com.example.test_androidramihamdi.databinding.RecylerViewItemBinding;
import com.example.test_androidramihamdi.model.RecyclerData;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
//public class RecyclerViewAdapter extends PagingDataAdapter<RecyclerData, RecyclerViewAdapter.MyViewHolder> {
    // Define Loading ViewType PAGINGVERSION
  //  public static final int LOADING_ITEM = 0;
    // Define Movie ViewType PAGINGVERSION
  //  public static final int IMAGE_ITEM = 1;
  private List<RecyclerData> listItems;

  public void setListItems(List<RecyclerData> listItems){
   this.listItems = listItems;
  }

     static   RecyclerViewClickInterface recyclerViewClickInterface;
    /*
    //PAGING VERSION
    public static DiffUtil.ItemCallback<RecyclerData> DIFF_CALLBACK = new DiffUtil.ItemCallback<RecyclerData>() {
          @Override
          public boolean areItemsTheSame(@NonNull RecyclerData oldItem, @NonNull RecyclerData newItem) {
              return oldItem.id == newItem.id;
          }

          @Override
          public boolean areContentsTheSame(@NonNull RecyclerData oldItem, @NonNull RecyclerData newItem) {
              return oldItem.equals(newItem);
          }
      };



    public RecyclerViewAdapter(RecyclerViewClickInterface itemClickListener) {
          super(DIFF_CALLBACK); WITHPAGING
          this.recyclerViewClickInterface = itemClickListener;
      }*/
  public RecyclerViewAdapter(RecyclerViewClickInterface recyclerViewClickInterface) {
      RecyclerViewAdapter.recyclerViewClickInterface = recyclerViewClickInterface;
  }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecylerViewItemBinding recyclerRowBinding = RecylerViewItemBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindView(listItems.get(position));

    }

    @Override
    public int getItemCount() {
      if (listItems == null)
        return 0;
      else
         return listItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public final RecylerViewItemBinding recylerViewItemBinding;
        public MyViewHolder(@NonNull RecylerViewItemBinding itemView) {
            super(itemView.getRoot());
            this.recylerViewItemBinding = itemView;

        }

        public void bindView(RecyclerData listItems){
            recylerViewItemBinding.authorPixbayTxVw.setText(listItems.user);
            recylerViewItemBinding.tagsPixbayTxVw.setText(listItems.tags);
            Glide.with(recylerViewItemBinding.imgageView)
                    .load(listItems.previewURL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(recylerViewItemBinding.imgageView);


            recylerViewItemBinding.cardviewItem.setOnClickListener(view -> recyclerViewClickInterface.onItemClick(listItems));
        }
    }
    //WITH PAGING
/*
    @Override
    public int getItemViewType(int position) {
        // set ViewType
        return position == getItemCount() ? IMAGE_ITEM : LOADING_ITEM;
    }

 */
}
