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
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kallakurigroup.R;
import com.example.kallakurigroup.adapters.BrandsAdapter;
import com.example.kallakurigroup.listeners.BrandsListener;
import com.example.kallakurigroup.models.productsmodels.BrandsDetails;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;
import com.example.kallakurigroup.models.productsmodels.ProductResponceModel;
import com.example.kallakurigroup.activity.ProductsActivity;
import com.example.kallakurigroup.retrofit.ApiClient;
import com.example.kallakurigroup.retrofit.ApiInterface;
import com.example.kallakurigroup.utils.Dialogs;
import com.example.kallakurigroup.utils.Storage;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
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

    private ArrayList<BrandsDetails> brandsList;
    private ProductResponceModel model;
    private Map<String, ArrayList<ProductDetails>> productsObject;

    @BindView(R.id.homePlaceHolder)
    ShimmerFrameLayout homePlaceHolder;

    @BindView(R.id.ll_brands)
    LinearLayout llBrands;

    @BindView(R.id.homeCarouselView)
    CarouselView homeCarouselView;

    @BindView(R.id.homeRecyclerView)
    RecyclerView homeRecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, v);

       // final FragmentHomeBinding fragmentHomeBinding = FragmentHomeBinding.bind(v);

        homePlaceHolder.startShimmer();

        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_3.jpg");
        arrayList.add("https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_1.jpg");
        arrayList.add("https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_2.jpg");

        homeCarouselView.setPageCount(arrayList.size());
        homeCarouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(Objects.requireNonNull(getActivity()).getApplicationContext())
                        .load(arrayList.get(position))
                        .into(imageView);
            }
        });
        homeCarouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), arrayList.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        homeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        getBrands();

        return v;
    }

    void getBrands(){

        Storage storage = new Storage(getActivity().getApplicationContext());

        // Dialogs.ProgressDialog(getContext());

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ProductResponceModel> call = apiService.getProducts(storage.getUserDetails().getRoleNumber());
        call.enqueue(new Callback<ProductResponceModel>() {
            @Override
            public void onResponse(Call<ProductResponceModel> call, Response<ProductResponceModel> response) {

                Dialogs.Cancel();

                if (response.code() == 200) {

                    model = response.body();

                    brandsList = model.getData().getBrandsList();
                    productsObject = model.getData().getProductPricings();

                    BrandsAdapter adapter = new BrandsAdapter(brandsList, HomeFragment.this);
                    homeRecyclerView.setAdapter(adapter);
                    homePlaceHolder.setVisibility(View.GONE);
                    llBrands.setVisibility(View.VISIBLE);
                    loadAnimation(llBrands);
                    loadAnimation(homeRecyclerView);

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

    private void loadAnimation(ViewGroup view) {
        Context context = view.getContext();
        LayoutAnimationController layoutAnimationController = AnimationUtils
                .loadLayoutAnimation(context, R.anim.layout_right_slide);
        view.setLayoutAnimation(layoutAnimationController);
    }

    @Override
    public void brandSelected(int position, String brandName) {

        ArrayList<ProductDetails> selectedProducts = new ArrayList<>();
        for (Map.Entry<String, ArrayList<ProductDetails>> productList : productsObject.entrySet()) {
            if(productList.getKey().equals(String.valueOf(brandsList.get(position).getId()))) {
                selectedProducts.addAll(productList.getValue());
            }
        }

        Intent intent = new Intent(getContext(), ProductsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("products", selectedProducts);
        intent.putExtras(bundle);
        intent.putExtra("brand_name", brandName);
        startActivity(intent);
    }
}
