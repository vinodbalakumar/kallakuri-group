package com.example.kallakurigroup.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.databinding.BrandsRowBinding;
import com.example.kallakurigroup.fragments.homefragment.IHomeFragment;
import com.example.kallakurigroup.models.productsmodels.BrandsDetails;

import java.util.ArrayList;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.Brands> {

    private ArrayList<BrandsDetails> list;
    private IHomeFragment iHomeFragment;

    public BrandsAdapter(ArrayList<BrandsDetails> list, IHomeFragment iHomeFragment) {
        this.list = list;
        this.iHomeFragment = iHomeFragment;
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
                    iHomeFragment.brandSelected(getAdapterPosition());
                }
            });
        }
    }
}
