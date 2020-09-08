package com.example.kallakurigroup.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.database.UserTableDAO;
import com.example.kallakurigroup.models.userModels.UserTableModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {

    UserTableDAO userTableDAO;
    UserTableModel userTableModel;
    Context context;

    @BindView(R.id.textName)
    TextView textName;

    @BindView(R.id.textEmail)
    TextView textEmail;

    @BindView(R.id.textPhoneNum)
    TextView textPhoneNum;

    @BindView(R.id.textAddress)
    TextView textAddress;

    @BindView(R.id.ll_edit)
    LinearLayout llEdit;

    @BindView(R.id.user_image)
    ImageView userImage;

    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_account, container, false);

        context = getActivity();

        ButterKnife.bind(this, v);

        userTableDAO = new UserTableDAO(context);
        userTableModel = userTableDAO.getData().get(0);

        setData();

        return v;
    }

    void setData(){
        textName.setText(userTableModel.getName());
        textEmail.setText(userTableModel.getEmail());
        textPhoneNum.setText(userTableModel.getPhoneNo());
        textAddress.setText(userTableModel.getDeliveryAddress());
    }
}
