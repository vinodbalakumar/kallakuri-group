package com.example.kallakurigroup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.kallakurigroup.R;
import com.example.kallakurigroup.adapters.ProductsAdapter;
import com.example.kallakurigroup.database.ProductTableDAO;
import com.example.kallakurigroup.databinding.ActivityProductsBinding;
import com.example.kallakurigroup.listeners.ProductItemListener;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity implements ProductItemListener {

    private List<ProductDetails> productsList;

    @BindView(R.id.left_lay)
    RelativeLayout left_lay;

    @BindView(R.id.header_text)
    TextView header_text;

    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;

    @BindView(R.id.rl_noDataFound)
    RelativeLayout rlNoDataFound;

    ProductTableDAO productTableDAO;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_products);

        context = this;

        ButterKnife.bind(this);

        productTableDAO = new ProductTableDAO(this);

        header_text.setText(getIntent().getStringExtra("brand_name"));

        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //productsList = this.getIntent().getExtras().getParcelableArrayList("products");


        productsList = productTableDAO.getProductByBrandId(getIntent().getStringExtra("brand_id"));

        if(productsList!=null && productsList.size()>0){
            productRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

            productRecyclerView.setAdapter(new ProductsAdapter(productsList, ProductsActivity.this));
        }else {
            productRecyclerView.setVisibility(View.GONE);
            rlNoDataFound.setVisibility(View.VISIBLE);
        }

        //loadAnimation(llBrands);
        loadAnimation(productRecyclerView);

        left_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void productSelected(int position) {
        startActivity(new Intent(context, ProductsDetailsActivity.class).putExtra("product_id", productsList.get(position).getId()));
    }

    private void loadAnimation(ViewGroup view) {
        Context context = view.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils
                .loadLayoutAnimation(context, R.anim.layout_right_slide);
        view.setLayoutAnimation(layoutAnimationController);
    }
}
