package com.example.kallakurigroup.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.database.BrandsTableDAO;
import com.example.kallakurigroup.database.ProductTableDAO;
import com.example.kallakurigroup.database.UserTableDAO;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import static com.example.kallakurigroup.utils.Popup_Class.PREFERENCE;

/**
 * Created by android on 2/3/17.
 */

public class Dialogs {

   public static Dialog _dialog;

    public static void ProgressDialog(Context context){

        try {
            LayoutInflater li = LayoutInflater.from(context);
            RelativeLayout lt = (RelativeLayout) li.inflate(R.layout.custom_progress, null);


            ImageView imageView = (ImageView) lt.findViewById(R.id.img_cricle);

            RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(1500);
            imageView.setAnimation(anim);
            imageView.startAnimation(anim);

            ImageView img_logo = (ImageView) lt.findViewById(R.id.img_logo);

            ObjectAnimator animation = ObjectAnimator.ofFloat(img_logo, "rotationY", 0.0f, 360f);
            animation.setDuration(3000);
            animation.setRepeatCount(ObjectAnimator.INFINITE);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            animation.start();


            _dialog = new Dialog(context);
            _dialog.setCancelable(false);
            _dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            View v = _dialog.getWindow().getDecorView();
            v.setBackgroundResource(R.drawable.trans);
            _dialog.setContentView(lt);
            _dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void Cancel(){

        try {
            if (_dialog != null) {
                if (_dialog.isShowing()) {
                    _dialog.cancel();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void show_popUp(String msg, Context context) {

        final BottomSheetDialog dg =  new Popup_Class().showDialog(false, context.getString(R.string.error), msg, context);
        TextView k = (TextView) dg.findViewById(R.id.textOk);

        LinearLayout ll_ok = (LinearLayout)dg.findViewById(R.id.ll_ok);

        ll_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dg.dismiss();

            }
        });

        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dg.dismiss();

            }
        });
    }

    public static void dialogRefer(String title, String msg, String buttonText, Context context) {


        final BottomSheetDialog dg =  new Popup_Class().showDialog(false, title, msg, context);
        TextView k = (TextView) dg.findViewById(R.id.textOk);
        k.setText(buttonText);
        LinearLayout ll_ok = (LinearLayout) dg.findViewById(R.id.ll_ok);

        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, msg);
                context.startActivity(Intent.createChooser(intent, "Share"));

            }
        });

        ll_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, msg);
                context.startActivity(Intent.createChooser(intent, "Share"));

            }
        });

    }

}
