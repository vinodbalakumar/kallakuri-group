package com.example.kallakurigroup.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kallakurigroup.R;
import com.example.kallakurigroup.activity.ProductsActivity;
import com.example.kallakurigroup.adapters.BrandsAdapter;
import com.example.kallakurigroup.database.BrandsTableDAO;
import com.example.kallakurigroup.database.ProductTableDAO;
import com.example.kallakurigroup.database.TopBrandsTableDAO;
import com.example.kallakurigroup.database.UserTableDAO;
import com.example.kallakurigroup.listeners.BrandsListener;
import com.example.kallakurigroup.models.productsmodels.BrandsDetails;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;
import com.example.kallakurigroup.models.productsmodels.ProductResponceModel;
import com.example.kallakurigroup.models.productsmodels.TopBrandsDetails;
import com.example.kallakurigroup.models.userModels.UserTableModel;
import com.example.kallakurigroup.retrofit.ApiClient;
import com.example.kallakurigroup.retrofit.ApiInterface;
import com.example.kallakurigroup.utils.Dialogs;
import com.example.kallakurigroup.utils.PropertiesFile;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment implements BrandsListener {

    private List<BrandsDetails> brandsList = new ArrayList<>();
    private ProductResponceModel model;
    private Map<String, List<ProductDetails>> productsObject = new HashMap<>();
    private List<TopBrandsDetails> listTopBrands = new ArrayList<>();

    @BindView(R.id.homePlaceHolder)
    ShimmerFrameLayout homePlaceHolder;

   /* @BindView(R.id.brandsRowCardView)
    CardView brandsRowCardView;*/

    @BindView(R.id.homeRecyclerView)
    RecyclerView homeRecyclerView;

    UserTableDAO userTableDAO;
    UserTableModel userTableModel;

    BrandsTableDAO brandsTableDAO;
    ProductTableDAO productTableDAO;
    TopBrandsTableDAO topBrandsTableDAO;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_categories, container, false);

        ButterKnife.bind(this, v);

        brandsTableDAO = new BrandsTableDAO(getActivity());
        productTableDAO = new ProductTableDAO(getActivity());
        topBrandsTableDAO = new TopBrandsTableDAO(getActivity());
        userTableDAO = new UserTableDAO(getActivity());
        userTableModel = userTableDAO.getData().get(0);

       // final FragmentHomeBinding fragmentHomeBinding = FragmentHomeBinding.bind(v);

        homePlaceHolder.startShimmer();

        homeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //homeRecyclerView.addItemDecoration(new GridSpacingItemDecoration(5));

        if(productTableDAO.getData().size()>0){
            brandsList = brandsTableDAO.getData();
            listTopBrands = topBrandsTableDAO.getData();
            setAdapter();
        }

        return v;
    }


    private void setAdapter(){
        BrandsAdapter adapter = new BrandsAdapter(brandsList, CategoriesFragment.this);
        homeRecyclerView.setAdapter(adapter);
        homePlaceHolder.setVisibility(View.GONE);
        homeRecyclerView.setVisibility(View.VISIBLE);
        loadAnimation(homeRecyclerView);
        loadAnimation(homeRecyclerView);

        homePlaceHolder.startShimmer();
    }

    private void loadAnimation(ViewGroup view) {
        Context context = view.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils
                .loadLayoutAnimation(context, R.anim.layout_right_slide);
        view.setLayoutAnimation(layoutAnimationController);
    }

    @Override
    public void brandSelected(int position, String brandName, int brandId) {

        Intent intent = new Intent(getContext(), ProductsActivity.class);
       /* Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("products", selectedProducts);
        intent.putExtras(bundle);*/
        intent.putExtra("brand_name", brandName);
        intent.putExtra("brand_id", String.valueOf(brandId));
        startActivity(intent);
    }
}
