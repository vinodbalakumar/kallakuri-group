package com.example.kallakurigroup.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.activity.ProductsActivity;
import com.example.kallakurigroup.databinding.ProductsRowBinding;
import com.example.kallakurigroup.listeners.ProductItemListener;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    List<ProductDetails> list;
    ProductItemListener productItemListener;
    Context context;

    public ProductsAdapter(List<ProductDetails> list, ProductItemListener productItemListener, Context context) {
        this.list = list;
        this.productItemListener = productItemListener;
        this.context = context;
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
        holder.productsRowBinding.setProduct(productDetails);

        if(list.get(position).getSelectedQty()!=null && !list.get(position).getSelectedQty().equalsIgnoreCase("0")){
            holder.productsRowBinding.productAdd.setVisibility(View.GONE);
            holder.productsRowBinding.innerLt.setVisibility(View.VISIBLE);
        }
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

            itemView.prodImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productItemListener.productSelected(getAdapterPosition());
                }
            });

            itemView.productAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemView.innerLt.setVisibility(View.VISIBLE);
                    itemView.productAdd.setVisibility(View.GONE);
                    int selectedCount = 1;
                    float prodPrice = Float.parseFloat(itemView.ProductOfferPrice.getText().toString());
                    float selectedPrice = prodPrice*selectedCount;
                    itemView.quantityCount.setText(String.valueOf(selectedCount));
                    productItemListener.quantityCountChanges(getAdapterPosition(), selectedCount, selectedPrice, prodPrice, "plus");
                }
            });

            itemView.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  int selectedCount = Integer.parseInt(itemView.quantityCount.getText().toString());
                  float prodPrice = Float.parseFloat(itemView.ProductOfferPrice.getText().toString());
                  if(selectedCount!=10) {
                      selectedCount = selectedCount + 1;
                      float selectedPrice = prodPrice*selectedCount;
                      itemView.quantityCount.setText(String.valueOf(selectedCount));
                      productItemListener.quantityCountChanges(getAdapterPosition(), selectedCount, selectedPrice, prodPrice, "plus");
                  }
                }
            });

            itemView.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selectedCount = Integer.parseInt(itemView.quantityCount.getText().toString());
                    float prodPrice = Float.parseFloat(itemView.ProductOfferPrice.getText().toString());
                    if(selectedCount==1) {
                        itemView.innerLt.setVisibility(View.GONE);
                        itemView.productAdd.setVisibility(View.VISIBLE);
                        }
                    selectedCount = selectedCount - 1;
                    float selectedPrice = prodPrice*selectedCount;
                    itemView.quantityCount.setText(String.valueOf(selectedCount));
                    productItemListener.quantityCountChanges(getAdapterPosition(), selectedCount, selectedPrice, prodPrice, "minus");
                }
            });

            itemView.textViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productItemListener.productSelected(getAdapterPosition());
                }
            });
        }

    }
}

