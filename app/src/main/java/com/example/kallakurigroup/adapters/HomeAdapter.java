package com.example.kallakurigroup.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.databinding.BrandsRowBinding;
import com.example.kallakurigroup.databinding.HomeRowBinding;
import com.example.kallakurigroup.listeners.BrandsListener;
import com.example.kallakurigroup.models.productsmodels.BrandsDetails;
import com.example.kallakurigroup.models.productsmodels.TopBrandsDetails;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Brands> {

    private List<TopBrandsDetails> list;

    public HomeAdapter(List<TopBrandsDetails> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Brands onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeRowBinding homeRowBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.home_row, parent, false);
        return new Brands(homeRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Brands holder, int position) {
        final TopBrandsDetails details = list.get(position);
        holder.brandsRowBinding.setBrand(details);
        holder.brandsRowBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Brands extends RecyclerView.ViewHolder {

        HomeRowBinding brandsRowBinding;

        public Brands(@NonNull final HomeRowBinding itemView) {
            super(itemView.getRoot());
            brandsRowBinding = itemView;
        }
    }
}
