package com.example.kallakurigroup.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.utils.Dialogs;
import com.example.kallakurigroup.utils.LoginCommon;
import com.example.kallakurigroup.utils.Network_info;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Login extends AppCompatActivity implements View.OnClickListener{

    boolean Cleared = true;

    InputMethodManager inputMethodManager;
    @BindView(R.id.buttonSubmit)
    Button mSubmit;

    @BindView(R.id.textForgotPin)
    TextView mForgotPin;

    @BindView(R.id.edit_username)
    EditText mEditUserName;

    @BindView(R.id.edit_pin)
    EditText mEditPin;

    @BindView(R.id.showPassImage)
    ImageView showPassImage;

    @BindView(R.id.buttonSignup)
    Button buttonSignup;

    @BindView(R.id.loginwith_text)
    TextView loginwith_text;

    @BindView(R.id.newUserLinear)
    LinearLayout newUserLinear;

    String mPhoneNumber, mPass;
    Context context;

    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;
    String PREFERENCE = "KALLAKURI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = Login.this;

        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


        mSubmit.setOnClickListener(this);
        mForgotPin.setOnClickListener(this);
        buttonSignup.setOnClickListener(this);

        showPassImage.setTag("hide");
        showPassImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showPassImage.getTag().toString().equalsIgnoreCase("hide")){
                    showPassImage.setImageResource(R.drawable.show_password);
                    showPassImage.setTag("show");
                    mEditPin.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);//show
                    mEditPin.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));

                }else{
                    showPassImage.setImageResource(R.drawable.dont_show);
                    showPassImage.setTag("hide");
                    mEditPin.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);//hide
                    mEditPin.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));

                }

            }
        });

       /* mEditPin.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                if (!mEditPin.getText().toString().equals("") && mEditPin.getText().toString() != null && !mEditUserName.getText().toString().equals("") && mEditUserName.getText().toString() != null) {

                    if (mEditPin.getText().toString().length() <= 5) {
                        Cleared = true;
                    }
                    if (mEditPin.getText().toString().length() == 6) {

                        if (mEditUserName.getText().toString().length() == 10) {

                            mPhoneNumber = mEditUserName.getText().toString();

                            mPass = mEditPin.getText().toString();

                            if(Cleared) {
                                Cleared =  false;
                                validations();
                            }
                        }

                    }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        mEditUserName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                //if (ClassicSingleton.isSupport){
                    if (!mEditUserName.getText().toString().equals("") && mEditUserName.getText().toString() != null){

                        if(mEditUserName.getText().toString().length()<=9){
                            Cleared = true;
                        }

                        if (mEditUserName.getText().toString().length() == 10) {

                            mPhoneNumber = mEditUserName.getText().toString();

                            mPass = mEditPin.getText().toString();

                            if(Cleared) {
                                Cleared =  false;
                                validations();
                            }

                       // }
                    }
                }else if (!mEditUserName.getText().toString().equals("") && mEditUserName.getText().toString() != null && !mEditPin.getText().toString().equals("") && mEditPin.getText().toString() != null) {

                    if(mEditUserName.getText().toString().length()<=9){
                        Cleared = true;
                    }

                    if (mEditUserName.getText().toString().length() == 10) {

                        if (mEditPin.getText().toString().length() == 6) {

                            mPhoneNumber = mEditUserName.getText().toString();

                            mPass = mEditPin.getText().toString();

                            if(Cleared) {
                                Cleared =  false;
                                validations();
                            }
                        }
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


        */
    }




    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.buttonSubmit:

                    validations();

                    break;
                case R.id.textForgotPin:
                    Intent pin = new Intent(Login.this, Forgot_password_activity.class);
                    pin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(pin);
                    break;
               // case R.id.back_arrow:
                   /* if (ClassicSingleton.isSupport){

                    }else {*/
                      //  finish();
                   // }

                case R.id.buttonSignup:

                    Intent i = new Intent(Login.this, SignUp.class);
                    //  i.putExtra("from","newsignup");
                    //  i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(i);

                default:
                    break;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void validations(){

        try {
            mPass = mEditPin.getText().toString();
            mPhoneNumber = mEditUserName.getText().toString();

            if (mPhoneNumber.isEmpty()) {

                Dialogs.show_popUp(getResources().getString(R.string.enter_mobile_number), context);

            }else if (!Network_info.valid(mPhoneNumber)) {

                Dialogs.show_popUp(getResources().getString(R.string.enter_valid_ten_digit_mobile_number), context);

            } else if (mPass.isEmpty() || mPass.length()<6) {

                Dialogs.show_popUp(getResources().getString(R.string.enter_Valid_pin), context);

            } else if (Network_info.isNetworkAvailable(context)) {

                try {
                    inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (Network_info.isNetworkAvailable(context)) {

                    new LoginCommon(context, mPhoneNumber, mPass).doLogin();

                } else {

                    Dialogs.show_popUp(getResources().getString(R.string.no_internet_connection), context);

                }

                //  clearData();

            } else {

                Dialogs.show_popUp(getResources().getString(R.string.no_internet_connection), context);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void clearData(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mEditPin.setText("");
                mEditUserName.setText("");
                mEditUserName.requestFocus();

            }
        }, 1000);


    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPhoneNumber = null;
        mPass =null;
        context = null;
        inputMethodManager = null;
    }

}
