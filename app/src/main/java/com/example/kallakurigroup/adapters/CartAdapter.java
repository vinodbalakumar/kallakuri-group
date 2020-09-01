package com.example.kallakurigroup.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.databinding.CartRowBinding;
import com.example.kallakurigroup.databinding.ProductsRowBinding;
import com.example.kallakurigroup.listeners.CartItemListener;
import com.example.kallakurigroup.listeners.ProductItemListener;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<ProductDetails> list;
    CartItemListener cartItemListener;
    Context context;

    public CartAdapter(List<ProductDetails> list, CartItemListener cartItemListener, Context context) {
        this.list = list;
        this.cartItemListener = cartItemListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartRowBinding cartRowBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.cart_row, parent, false);
        return new ViewHolder(cartRowBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductDetails productDetails = list.get(position);
        holder.cartRowBinding.setProduct(productDetails);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CartRowBinding cartRowBinding;

        public ViewHolder(@NonNull CartRowBinding itemView) {
            super(itemView.getRoot());
            cartRowBinding = itemView;

            itemView.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  int selectedCount = Integer.parseInt(itemView.quantityCount.getText().toString());
                  float prodPrice = Float.parseFloat(itemView.ProductOfferPrice.getText().toString());
                  if(selectedCount!=10) {
                      selectedCount = selectedCount + 1;
                      float selectedPrice = prodPrice*selectedCount;
                      itemView.quantityCount.setText(String.valueOf(selectedCount));
                      cartItemListener.quantityCountChanges(getAdapterPosition(), selectedCount, selectedPrice, prodPrice, "plus");
                  }
                }
            });

            itemView.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selectedCount = Integer.parseInt(itemView.quantityCount.getText().toString());
                    float prodPrice = Float.parseFloat(itemView.ProductOfferPrice.getText().toString());
                    selectedCount = selectedCount - 1;
                    float selectedPrice = prodPrice*selectedCount;
                    itemView.quantityCount.setText(String.valueOf(selectedCount));
                    cartItemListener.quantityCountChanges(getAdapterPosition(), selectedCount, selectedPrice, prodPrice, "minus");
                }
            });
        }

    }
}

