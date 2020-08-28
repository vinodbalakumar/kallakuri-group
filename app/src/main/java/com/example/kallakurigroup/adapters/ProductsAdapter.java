package com.example.kallakurigroup.adapters;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.databinding.ProductsRowBinding;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;
import com.example.kallakurigroup.productspage.IProductsActivity;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    ArrayList<ProductDetails> list;
    IProductsActivity iProductsActivity;

    public ProductsAdapter(ArrayList<ProductDetails> list, IProductsActivity iProductsActivity) {
        this.list = list;
        this.iProductsActivity = iProductsActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductsRowBinding productsRowBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.products_row, parent, false);
        return new ViewHolder(productsRowBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductDetails productDetails = list.get(position);
        holder.productsRowBinding.brandsRowCost
                .setPaintFlags(holder.productsRowBinding.brandsRowCost.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productsRowBinding.brandsRowCost
                .setText(productDetails.getProductCost(), TextView.BufferType.SPANNABLE);
        holder.productsRowBinding.setProduct(productDetails);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ProductsRowBinding productsRowBinding;

        public ViewHolder(@NonNull ProductsRowBinding itemView) {
            super(itemView.getRoot());
            productsRowBinding = itemView;

            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iProductsActivity.productSelected(getAdapterPosition());
                }
            });
        }

    }

}
