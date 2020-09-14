package com.example.kallakurigroup.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.activity.Login;
import com.example.kallakurigroup.database.BrandsTableDAO;
import com.example.kallakurigroup.database.ProductTableDAO;
import com.example.kallakurigroup.database.UserTableDAO;
import com.example.kallakurigroup.retrofit.ApiClient;
import com.example.kallakurigroup.retrofit.ApiInterface;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.j256.ormlite.stmt.query.In;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

/**
 * Created by aw on 23-11-2016.
 */

public class Popup_Class {

    public static final String PREFERENCE = "KALLAKURI";
    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;

   public static BottomSheetDialog showDialog(final boolean finishAcivity, String Title, String message, final Context context) {

       final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
       LayoutInflater li = LayoutInflater.from(context);

       LinearLayout lt = (LinearLayout) li.inflate(R.layout.layout_dialog_registration, null);

       TextView textTitle = (TextView) lt.findViewById(R.id.textTitle);
       ImageView imgClose = (ImageView) lt.findViewById(R.id.imgClose);
       TextView t_msg = (TextView) lt.findViewById(R.id.textMessage);
       TextView textOk = (TextView) lt.findViewById(R.id.textOk);
       LinearLayout ll_ok = (LinearLayout) lt.findViewById(R.id.ll_ok);


       RelativeLayout rel_close = (RelativeLayout) lt.findViewById(R.id.rel_close);

       if (!message.equalsIgnoreCase(null)) {
           t_msg.setText(message);
       }

       if (!Title.equalsIgnoreCase(null)) {

           textTitle.setText(Title);
       }



       textOk.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               mBottomSheetDialog.dismiss();

               try {
                   if (finishAcivity) {
                       ((Activity) context).finish();
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }

           }
       });

       ll_ok.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mBottomSheetDialog.dismiss();

               try {
                   if (finishAcivity) {
                       ((Activity) context).finish();
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
       });

       imgClose.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mBottomSheetDialog.dismiss();

               try {
                   if (finishAcivity) {
                       ((Activity) context).finish();
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
       });

       rel_close.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mBottomSheetDialog.dismiss();

               try {
                   if (finishAcivity) {
                       ((Activity) context).finish();
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
       });
       mBottomSheetDialog.setContentView(lt);
       mBottomSheetDialog.setCanceledOnTouchOutside(false);
       mBottomSheetDialog.show();


       return mBottomSheetDialog;
    }

    public BottomSheetDialog showDialog_single_ok_Title(final Context context, String message, String title) {


        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
        LayoutInflater li = LayoutInflater.from(context);

        LinearLayout lt = (LinearLayout) li.inflate(R.layout.layout_dialog_registration, null);

        TextView t_msg = (TextView) lt.findViewById(R.id.textMessage);
        if (!message.equalsIgnoreCase(null)) {
            t_msg.setText(message);
        }

        TextView textOk = (TextView) lt.findViewById(R.id.textOk);

        LinearLayout ll_ok= (LinearLayout) lt.findViewById(R.id.ll_ok);

        RelativeLayout rel_close = (RelativeLayout)lt.findViewById(R.id.rel_close);

        TextView textTitle = (TextView) lt.findViewById(R.id.textTitle);

        textTitle.setText(""+title);

        ll_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();

            }
        });

        textOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBottomSheetDialog.dismiss();
            }
        });

        rel_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

        mBottomSheetDialog.setContentView(lt);
        mBottomSheetDialog.setCanceledOnTouchOutside(false);
        mBottomSheetDialog.show();


        return mBottomSheetDialog;
    }

    public Dialog showDialog_dual_action_yes(final Context context, String message, String strtitle) {

        LayoutInflater li = LayoutInflater.from(context);

        LinearLayout lt = (LinearLayout) li.inflate(R.layout.logout_dialog, null);

        TextView t_yes = (TextView) lt.findViewById(R.id.p_yes);
        TextView t_no = (TextView) lt.findViewById(R.id.p_no);
        TextView t_msg = (TextView) lt.findViewById(R.id.p_text);
        // ImageView iv = (ImageView) lt.findViewById(R.id.p_image);
        TextView title = (TextView) lt.findViewById(R.id.title);

        title.setText(strtitle);

        t_msg.setText(message);


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(R.drawable.trans);
        dialog.setContentView(lt);
        dialog.getWindow().setLayout((int) context.getResources().getDimension(R.dimen._274sdp), ViewGroup.LayoutParams.WRAP_CONTENT);
        //   dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        return dialog;
    }

    public void showDialog_logout(final Context context, String message, final Activity activity, String type) {

        sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        LayoutInflater li = LayoutInflater.from(context);

        LinearLayout lt = (LinearLayout) li.inflate(R.layout.logout_dialog, null);

        TextView t_yes = (TextView) lt.findViewById(R.id.p_yes);
        TextView t_no = (TextView) lt.findViewById(R.id.p_no);
        TextView t_msg = (TextView) lt.findViewById(R.id.p_text);
        //  ImageView iv = (ImageView) lt.findViewById(R.id.p_image);

      /*  TextView title = (TextView) lt.findViewById(R.id.title);

        title.setText(context.getString(R.string.logout));*/

        t_msg.setText(message);


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(R.drawable.trans);
        dialog.setContentView(lt);
        dialog.getWindow().setLayout((int) context.getResources().getDimension(R.dimen._274sdp), ViewGroup.LayoutParams.WRAP_CONTENT);
        //   dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        t_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        t_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


                if(type.equalsIgnoreCase("close")){
                    activity.moveTaskToBack(true);
                }else {

                    refreshData(context);

                    //activity.finishAffinity();
                    Intent i = new Intent(context, Login.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }

            }
        });
    }

    public void refreshData(Context context){
        try {
            editor.clear().apply();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            new UserTableDAO(context).deleteAll();
            new BrandsTableDAO(context).deleteAll();
            new ProductTableDAO(context).deleteAll();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            editor.clear();
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void sendError(String description, String errorMessage, int id, String phoneNo){

       try{
           JSONObject jsonObject1 = new JSONObject();
           jsonObject1.put("description", description);
           jsonObject1.put("errorMessage", errorMessage);
           jsonObject1.put("id", id);
           jsonObject1.put("phoneNo", phoneNo);

           JsonObject jsonObject = (JsonObject) new JsonParser().parse(jsonObject1.toString());

           ApiInterface apiService =
                   ApiClient.getClient().create(ApiInterface.class);

           Call<JsonElement> call = apiService.sendError(jsonObject);
           call.enqueue(new Callback<JsonElement>() {
               @Override
               public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

               }

               @Override
               public void onFailure(Call<JsonElement> call, Throwable t) {
               }
           });
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}