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
import com.example.kallakurigroup.databinding.MyOrdersRowBinding;
import com.example.kallakurigroup.databinding.ProductsRowBinding;
import com.example.kallakurigroup.listeners.OrdersItemListener;
import com.example.kallakurigroup.listeners.ProductItemListener;
import com.example.kallakurigroup.models.productsmodels.OrderDetails;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;

import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {

    List<OrderDetails> list;
    OrdersItemListener ordersItemListener;
    Context context;

    public MyOrdersAdapter(List<OrderDetails> list, OrdersItemListener ordersItemListener, Context context) {
        this.list = list;
        this.ordersItemListener = ordersItemListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyOrdersRowBinding myOrdersRowBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.my_orders_row, parent, false);
        return new ViewHolder(myOrdersRowBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderDetails orderDetails = list.get(position);
        holder.ordersRowBinding.setProduct(orderDetails);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MyOrdersRowBinding ordersRowBinding;

        public ViewHolder(@NonNull MyOrdersRowBinding itemView) {
            super(itemView.getRoot());
            ordersRowBinding = itemView;

            itemView.trackOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ordersItemListener.trackOrder(list.get(getAdapterPosition()));
                }
            });
        }

    }
}

