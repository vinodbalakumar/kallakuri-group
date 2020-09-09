package com.example.kallakurigroup.fragments;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.kallakurigroup.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AboutUsFragment extends Fragment {

    @BindView(R.id.textVersion)
    TextView textVersion;

    Context context;

    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_about_us, container, false);

        context = getActivity();

        ButterKnife.bind(this, v);

        PackageInfo pinfo = null;
        try {
            pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            int versionNumber = pinfo.versionCode;
            String versionName = pinfo.versionName;
            textVersion.setText("Version Number: "+versionNumber/*+"\n"+"Version Name: "+versionName*/);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }
}
