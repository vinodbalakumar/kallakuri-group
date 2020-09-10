package com.example.kallakurigroup.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Freezable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.activity.OrderTrackingActivity;
import com.example.kallakurigroup.activity.ProductsActivity;
import com.example.kallakurigroup.adapters.MyOrdersAdapter;
import com.example.kallakurigroup.database.UserTableDAO;
import com.example.kallakurigroup.listeners.OrdersItemListener;
import com.example.kallakurigroup.models.MainModel;
import com.example.kallakurigroup.models.productsmodels.OrderDetails;
import com.example.kallakurigroup.models.userModels.UserTableModel;
import com.example.kallakurigroup.retrofit.ApiClient;
import com.example.kallakurigroup.retrofit.ApiInterface;
import com.example.kallakurigroup.utils.Dialogs;
import com.example.kallakurigroup.utils.PropertiesFile;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderHistory extends Fragment implements OrdersItemListener {

    private List<OrderDetails> orderDetailsList = new ArrayList<>();

    MyOrdersAdapter myOrdersAdapter;

    @BindView(R.id.productRecyclerView)
    RecyclerView productRecyclerView;

    @BindView(R.id.rl_noDataFound)
    RelativeLayout rlNoDataFound;

    @BindView(R.id.textMessage)
    TextView textMessage;

    @BindView(R.id.recyclerCard)
    CardView recyclerCard;

    Context context;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String PREFERENCE = "KALLAKURI";

    UserTableDAO userTableDAO;

    UserTableModel userTableModel;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_order_history, container, false);

        ButterKnife.bind(this, v);

        context = getContext();

        userTableDAO = new UserTableDAO(context);

        userTableModel = userTableDAO.getData().get(0);

        sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        productRecyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        getOrders();

        //loadAnimation(llBrands);
        loadAnimation(productRecyclerView);

        return v;

    }


    void getOrders(){

        Dialogs.ProgressDialog(context);

        JSONObject mainObject = new JSONObject();

        try {
            JSONObject data = new JSONObject();
            JSONObject error = new JSONObject();
            JSONObject header = new JSONObject();
            header.put("phoneNo", userTableModel.getPhoneNo());
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

        Call<MainModel> call = apiService.getOrders(PropertiesFile.baseUrlNew+"v1/myOrders", jsonObject);
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {

                Dialogs.Cancel();

                try {
                    if (response.code() == 200) {

                        orderDetailsList = response.body().getData().getOrdersModel();
                        if(orderDetailsList.size()>0){
                            recyclerCard.setVisibility(View.VISIBLE);
                            rlNoDataFound.setVisibility(View.GONE);
                            myOrdersAdapter = new MyOrdersAdapter(orderDetailsList, OrderHistory.this, context);
                            productRecyclerView.setAdapter(myOrdersAdapter);
                        }else {
                            textMessage.setText(response.body().getHeaderModel().getMessage());
                            recyclerCard.setVisibility(View.GONE);
                            rlNoDataFound.setVisibility(View.VISIBLE);
                        }

                    } else {
                        recyclerCard.setVisibility(View.GONE);
                        rlNoDataFound.setVisibility(View.VISIBLE);
                        textMessage.setText(response.body().getHeaderModel().getMessage());
                        //  Dialogs.show_popUp(getResources().getString(R.string.networkalert) + "-" +  response.code(), getContext());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    recyclerCard.setVisibility(View.GONE);
                    rlNoDataFound.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {
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
    public void trackOrder(OrderDetails orderDetails) {
        Intent intent = new Intent(getContext(), OrderTrackingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("orderDetails", orderDetails);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}