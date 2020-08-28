package com.example.kallakurigroup.productspage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;


import com.example.kallakurigroup.activity.Homepage;
import com.example.kallakurigroup.adapters.ProductsAdapter;
import com.example.kallakurigroup.databinding.ActivityProductsBinding;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity implements IProductsActivity {

    private ArrayList<ProductDetails> productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityProductsBinding productsBinding = ActivityProductsBinding.inflate(getLayoutInflater());
        View view = productsBinding.getRoot();
        setContentView(view);

        productsBinding.productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        productsBinding.productToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductsActivity.this, Homepage.class));
            }
        });

        productsList = this.getIntent().getExtras().getParcelableArrayList("products");

        productsBinding.productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        productsBinding.productRecyclerView.setAdapter(new ProductsAdapter(productsList, ProductsActivity.this));

    }

    @Override
    public void productSelected(int position) {
        Toast.makeText(this, productsList.get(position).getProductFinalPrice(), Toast.LENGTH_SHORT).show();
    }
}
