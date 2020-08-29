package com.example.kallakurigroup.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.kallakurigroup.R;
import com.example.kallakurigroup.adapters.ProductsAdapter;
import com.example.kallakurigroup.databinding.ActivityProductsBinding;
import com.example.kallakurigroup.listeners.ProductItemListener;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity implements ProductItemListener {

    private ArrayList<ProductDetails> productsList;

    @BindView(R.id.left_lay)
    RelativeLayout left_lay;

    @BindView(R.id.header_text)
    TextView header_text;

    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_products);

        context = this;

        ButterKnife.bind(this);


        header_text.setText(getIntent().getStringExtra("brand_name"));

        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        productsList = this.getIntent().getExtras().getParcelableArrayList("products");

        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        productRecyclerView.setAdapter(new ProductsAdapter(productsList, ProductsActivity.this));

        left_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void productSelected(int position) {
        Toast.makeText(this, productsList.get(position).getProductFinalPrice(), Toast.LENGTH_SHORT).show();
    }
}
