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
public class HomeFragment extends Fragment implements BrandsListener {

    private List<BrandsDetails> brandsList = new ArrayList<>();
    private ProductResponceModel model;
    private Map<String, List<ProductDetails>> productsObject = new HashMap<>();
    private List<TopBrandsDetails> listTopBrands = new ArrayList<>();

    @BindView(R.id.homePlaceHolder)
    ShimmerFrameLayout homePlaceHolder;

    @BindView(R.id.ll_brands)
    LinearLayout llBrands;

    @BindView(R.id.homeCarouselView)
    CarouselView homeCarouselView;

    @BindView(R.id.homeRecyclerView)
    RecyclerView homeRecyclerView;

    UserTableDAO userTableDAO;
    UserTableModel userTableModel;

    BrandsTableDAO brandsTableDAO;
    ProductTableDAO productTableDAO;
    TopBrandsTableDAO topBrandsTableDAO;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

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
        }else {
            getBrands();
        }

        return v;
    }

    void getBrands(){

        JSONObject mainObject = new JSONObject();

        try {
            JSONObject data = new JSONObject();
            JSONObject error = new JSONObject();
            JSONObject header = new JSONObject();
            header.put("role", Integer.parseInt(userTableModel.getRoleNumber()));
            mainObject.put("data", null);
            mainObject.put("error", null);
            mainObject.put("header", header);

        }catch (Exception e){
            e.printStackTrace();
        }

        JsonParser jsonParser = new JsonParser();

        JsonObject jsonObject = (JsonObject) jsonParser.parse(mainObject.toString());


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ProductResponceModel> call = apiService.getProducts(PropertiesFile.baseUrlNew+"v1/products", jsonObject);
        call.enqueue(new Callback<ProductResponceModel>() {
            @Override
            public void onResponse(Call<ProductResponceModel> call, Response<ProductResponceModel> response) {

                Dialogs.Cancel();

                if (response.code() == 200) {

                    model = response.body();

                    brandsList = model.getData().getBrandsList();
                    productsObject = model.getData().getProductPricings();
                    listTopBrands = model.getData().getLatestProducts();

                    storeLocalDb();
                    setAdapter();

                } else {
                    homePlaceHolder.setVisibility(View.GONE);
                    Dialogs.show_popUp(getResources().getString(R.string.networkalert) + "-" +  response.code(), getContext());
                }

            }

            @Override
            public void onFailure(Call<ProductResponceModel> call, Throwable t) {
                Dialogs.show_popUp(getResources().getString(R.string.error) + "-" + t.getMessage(), getContext());
                Dialogs.Cancel();
            }
        });
    }

    private void storeLocalDb() {

        brandsTableDAO.deleteAll();
        productTableDAO.deleteAll();
        topBrandsTableDAO.deleteAll();

        for(BrandsDetails brands : brandsList )
        {
            brandsTableDAO.addData(brands);
        }

        for(TopBrandsDetails topBrands : listTopBrands )
        {
            topBrandsTableDAO.addData(topBrands);
        }

        List<ProductDetails> productsList = new ArrayList<>();
        for (Map.Entry<String, List<ProductDetails>> productList : productsObject.entrySet()) {
            productsList.addAll(productList.getValue());
        }

        productTableDAO.addData(productsList);

    }

    private void setAdapter(){
        BrandsAdapter adapter = new BrandsAdapter(brandsList, HomeFragment.this);
        homeRecyclerView.setAdapter(adapter);
        homePlaceHolder.setVisibility(View.GONE);
        llBrands.setVisibility(View.VISIBLE);
        loadAnimation(llBrands);
        loadAnimation(homeRecyclerView);

        homePlaceHolder.startShimmer();

        homeCarouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(Objects.requireNonNull(getActivity()).getApplicationContext())
                        .load(listTopBrands.get(position).getImagePath())
                        .into(imageView);
            }
        });

        homeCarouselView.setPageCount(listTopBrands.size());

        homeCarouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
            }
        });
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
