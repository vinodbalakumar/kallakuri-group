package com.example.kallakurigroup.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.databinding.BrandsRowBinding;
import com.example.kallakurigroup.listeners.BrandsListener;
import com.example.kallakurigroup.models.productsmodels.BrandsDetails;

import java.util.ArrayList;
import java.util.List;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.Brands> {

    private List<BrandsDetails> list;
    private BrandsListener brandsListener;

    public BrandsAdapter(List<BrandsDetails> list, BrandsListener brandsListener) {
        this.list = list;
        this.brandsListener = brandsListener;
    }

    @NonNull
    @Override
    public Brands onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BrandsRowBinding brandsRowBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.brands_row, parent, false);
        return new Brands(brandsRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Brands holder, int position) {
        final BrandsDetails details = list.get(position);
        holder.brandsRowBinding.setBrand(details);
        holder.brandsRowBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Brands extends RecyclerView.ViewHolder {

        BrandsRowBinding brandsRowBinding;

        public Brands(@NonNull final BrandsRowBinding itemView) {
            super(itemView.getRoot());
            brandsRowBinding = itemView;

            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   brandsListener.brandSelected(getAdapterPosition(), list.get(getAdapterPosition()).getBrandName(), list.get(getAdapterPosition()).getId());
                }
            });
        }
    }
}
