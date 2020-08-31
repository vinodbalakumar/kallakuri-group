package com.example.kallakurigroup.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.database.UserTableDAO;
import com.example.kallakurigroup.models.userModels.UserTableModel;

public class SplashScreen extends AppCompatActivity {

    UserTableDAO userTableDAO;
    UserTableModel userTableModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userTableDAO = new UserTableDAO(this);
                setContentView(R.layout.activity_splashscreen);   new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(userTableDAO.getData().size()>0){
                    Intent intent = new Intent(SplashScreen.this, Homepage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }, 3000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}