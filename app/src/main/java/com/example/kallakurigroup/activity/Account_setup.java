package com.example.kallakurigroup.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.database.UserTableDAO;
import com.example.kallakurigroup.models.loginmodel.LoginResponceModel;
import com.example.kallakurigroup.models.userModels.UserTableModel;
import com.example.kallakurigroup.retrofit.ApiClient;
import com.example.kallakurigroup.retrofit.ApiInterface;
import com.example.kallakurigroup.utils.Dialogs;
import com.example.kallakurigroup.utils.Validations;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Account_setup extends Activity {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String PREFERENCE = "KALLAKURI";
    Button submit;
    TextView textRefreshAddress;
    RelativeLayout left_lay;
    EditText fname_et;
    EditText password_et;
    EditText confirmPass_et;
    public Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    Context context=this;
    EditText emailid_Et;
    public static AlertDialog.Builder date_builder;
    EditText shop_address_et;
    EditText pin_code_et;
    EditText city_et;
    EditText village_et;
    EditText district_et;
    Spinner state_et;
    EditText role_et;
    String gender;
    String user_DOB;
    String state_clicked, roleNameClicked = "role";
    String roleNumClicked = "0";
    String mMobileNum, imeiNum = "";
    RadioButton toggle_female;
    RadioButton toggle_male;
    TextView header_text;

    private static final int REQUEST_LOCATION = 1;

    LocationManager locationManager;
    String latitude, longitude;

    String currentLocationAddress = "", geoLocation = "";

    UserTableDAO userTableDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_set);

        userTableDAO = new UserTableDAO(this);

        sharedpreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        roleNumClicked = getIntent().getStringExtra("role_num");
        roleNameClicked = getIntent().getStringExtra("role_name");
        mMobileNum = getIntent().getStringExtra("mobile_num");

        dateView = (EditText) findViewById(R.id.dob_et);

        header_text = (TextView) findViewById(R.id.header_text);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //initialising date dialog

        date_builder = new AlertDialog.Builder(getApplicationContext());

        String date = day+"/"+(month + 1)+"/"+year;
        user_DOB = date;
        textRefreshAddress = (TextView) findViewById(R.id.textRefreshAddress);
        submit = (Button) this.findViewById(R.id.submit_btn_accntsetup);
        left_lay = (RelativeLayout) findViewById(R.id.left_lay);
        fname_et = (EditText) findViewById(R.id.account_full_name);
        password_et = (EditText) findViewById(R.id.account_pass);
        confirmPass_et = (EditText) findViewById(R.id.account_confirm_pass);
        emailid_Et = (EditText) findViewById(R.id.email);
        toggle_female = (RadioButton) findViewById(R.id.toggleButton1);
        toggle_male = (RadioButton) findViewById(R.id.toggleButton2);
        shop_address_et = (EditText) findViewById(R.id.shop_address_Et_id);
        pin_code_et = (EditText) findViewById(R.id.postal_code_et);
        city_et = (EditText) findViewById(R.id.city_et);
        village_et = (EditText) findViewById(R.id.village_et);
        district_et = (EditText) findViewById(R.id.district_et);
        state_et = (Spinner) findViewById(R.id.state_et);
       // role_et = (Spinner) findViewById(R.id.role_et);
        role_et = (EditText) findViewById(R.id.roleName);
        role_et.setText(roleNameClicked);
        gender = toggle_male.getText().toString();

        textRefreshAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationFetching();
            }
        });

        header_text.setText(getString(R.string.businessDetails));
        final String[] items = getResources().getStringArray(R.array.state_arrays);

        // Creating adapter for state spinner
        ArrayAdapter<String> dataAdapter_state = new ArrayAdapter<String>(this,
                R.layout.spinner_account_setup_text, items);
        // Drop down layout style - list view with radio button
        dataAdapter_state.setDropDownViewResource(R.layout.spinner_accountsetup);

        // attaching data adapter to spinner
        state_et.setAdapter(dataAdapter_state);
        state_et.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {

                state_clicked = arg0.getItemAtPosition(position).toString();

                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                if(state_clicked.equalsIgnoreCase("state")){
                    textView.setTextColor(ContextCompat.getColor(context, R.color.grey));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        toggle_female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    gender = toggle_female.getText().toString();

                    toggle_male.setChecked(false);
                }
            }
        });

        toggle_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    gender = toggle_male.getText().toString();

                    toggle_female.setChecked(false);
                }
            }
        });

        left_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  Intent i1 = new Intent(Account_setup.this, Login.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i1);*/
              finish();
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

       // getRoles();

        locationFetching();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* Intent i1 = new Intent(Account_setup.this, Login.class);
        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i1);*/
    }

    @SuppressWarnings("deprecation")
    public void onDobClick(View view) {
        showDialog(999);
    }

    @SuppressWarnings("deprecation")
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        if (day > calendar.get(Calendar.DAY_OF_MONTH) && year == calendar.get(Calendar.YEAR) && month == calendar.get(Calendar.MONTH)) {
            Dialogs.show_popUp(getResources().getString(R.string.dob_pop_error),context);
        }
        else if (month > calendar.get(Calendar.MONTH) && year == calendar.get(Calendar.YEAR)) {
            Dialogs.show_popUp(getResources().getString(R.string.dob_pop_error),context);
        }
        else if (year > calendar.get(Calendar.YEAR)) {
            Dialogs.show_popUp(getResources().getString(R.string.dob_pop_error),context);
        }
        else {
            if((calendar.get(Calendar.YEAR)-year)>=18)
            {
                dateView.setText(new StringBuilder().append(day).append("/")
                        .append(month).append("/").append(year));
                user_DOB = dateView.getText().toString();
            }
            else
                Dialogs.show_popUp(getResources().getString(R.string.enter_age_less),context);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        context = null;
    }

    public void getAddress(String latitude, String longitude){
        try {
            Geocoder geocoder;
            List<Address> addresses;
            String addressCurrent = null;

            geocoder = new Geocoder(context, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                currentLocationAddress = address.toString();
                geoLocation = "Latitude:"+latitude+",Logitude:"+longitude;

                String city = addresses.get(0).getLocality();
                String village = addresses.get(0).getSubLocality();
                String state = addresses.get(0).getAdminArea();
                String SubAdminArea = addresses.get(0).getSubAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                //String knownName = addresses.get(0).getFeatureName();
                String locality = addresses.get(0).getLocality();
                //String AdminArea = addresses.get(0).getAdminArea();
                String SubLocality = addresses.get(0).getSubLocality();
                //String CountryName = addresses.get(0).getCountryName();
                //String ddressLine = addresses.get(0).getAddressLine(1);
                //String premises = addresses.get(0).getPremises();
               // String throughfare = addresses.get(0).getThoroughfare();


                if(SubAdminArea!=null){
                    district_et.setText(""+SubAdminArea);
                }

                try {
                    if(state!=null) {
                        String[] statesArr = getResources().getStringArray(R.array.state_arrays);
                        for (int i = 0; i < statesArr.length; i++) {
                            if (state.equalsIgnoreCase(statesArr[i])) {
                                state_et.setSelection(i);

                                break;
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(address!=null){

                    shop_address_et.setText(""+address);
                }

                if(city != null) {

                    city_et.setText(""+city);
                }

                if(postalCode != null) {
                    pin_code_et.setText(""+postalCode);
                }

                if(village!=null){
                    village_et.setText(village);
                }

            } catch (IOException e) {
                e.printStackTrace();

            }

        }catch (Exception e){
            e.printStackTrace();

        }

    }

   /* private void getRoles(){

        Dialogs.ProgressDialog(context);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<RolesResponceModel> call = apiService.getRoles();

        call.enqueue(new Callback<RolesResponceModel>() {
            @Override
            public void onResponse(Call<RolesResponceModel> call, Response<RolesResponceModel> response) {

                if (!response.isSuccessful()) {
                    Dialogs.show_popUp(getResources().getString(R.string.try_again), context);
                    return;
                }

                if (response.code() == 200) {

                    RolesResponceModel model = response.body();

                    roleValues = new ArrayList<>();
                    roleNames = new ArrayList<>();
                    roleValues.add(-1);
                    roleNames.add("Role");
                    for(Map.Entry<String, Integer> rolesMap : model.getRolesData().getRoles().entrySet()) {

                        roleValues.add(rolesMap.getValue());

                        roleNames.add(rolesMap.getKey());
                    }

                    Dialogs.Cancel();
                    setRoles(roleNames);

                } else {
                    RolesResponceModel model = response.body();
                    Dialogs.Cancel();
                    Dialogs.show_popUp(model.getHeader().getSuccess(), context);                }

            }

            @Override
            public void onFailure(Call<RolesResponceModel> call, Throwable t) {
                Dialogs.Cancel();
                Dialogs.show_popUp(getResources().getString(R.string.error) + ": " + t.getMessage(), context);
            }
        });
    }*/

  /*  void setRoles(ArrayList<String> items){
        // Creating adapter for state spinner
        ArrayAdapter<String> dataAdapter_roles = new ArrayAdapter<String>(this,
                R.layout.spinner_account_setup_text, items);
        // Drop down layout style - list view with radio button
        dataAdapter_roles.setDropDownViewResource(R.layout.spinner_accountsetup);

        // attaching data adapter to spinner
        role_et.setAdapter(dataAdapter_roles);
        role_et.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {

                if(roleValues.size()>1) {
                    roleNumClicked = roleValues.get(position);
                    roleNameClicked = arg0.getItemAtPosition(position).toString();
                }
                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                if(roleNameClicked.equalsIgnoreCase("role")){
                    textView.setTextColor(ContextCompat.getColor(context, R.color.grey));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }*/

    private void register() {

        String name = fname_et.getText().toString().trim();
        String email = emailid_Et.getText().toString().trim();
        String password = password_et.getText().toString().trim();
        String confirmPass = confirmPass_et.getText().toString();
        String village = village_et.getText().toString();
        String city = city_et.getText().toString().trim();//town
        String district = district_et.getText().toString().trim();
        String state = state_clicked;
        String pincode = pin_code_et.getText().toString().trim();

        if (name.isEmpty()) {
            Dialogs.show_popUp(getResources().getString(R.string.enter_yourname), context);
            fname_et.requestFocus();
        } else if (password.isEmpty()) {
            Dialogs.show_popUp(getResources().getString(R.string.enter_password), context);
            password_et.requestFocus();
        } else if (confirmPass.isEmpty()) {
            Dialogs.show_popUp(getResources().getString(R.string.enter_confirm_pass), context);
            confirmPass_et.requestFocus();
        } else if (!password.equalsIgnoreCase(confirmPass)) {
            Dialogs.show_popUp(getResources().getString(R.string.pass_does_not_matched), context);
            confirmPass_et.requestFocus();
        } else if (email.isEmpty()) {
            Dialogs.show_popUp(getResources().getString(R.string.enter_email), context);
            emailid_Et.requestFocus();
        } else if (pincode.isEmpty() || !Validations.postalcodeValidation(pincode)){
            Dialogs.show_popUp(getResources().getString(R.string.enter_Valid_pin), context);
            pin_code_et.requestFocus();
        } else if (village.isEmpty()) {
            Dialogs.show_popUp(getResources().getString(R.string.enter_village), context);
            city_et.requestFocus();
        }else if (city.isEmpty()) {
            Dialogs.show_popUp(getResources().getString(R.string.enter_city), context);
            city_et.requestFocus();
        } else if (district.isEmpty()) {
            Dialogs.show_popUp(getResources().getString(R.string.enter_district), context);
            district_et.requestFocus();
        }  else if(state_clicked.isEmpty() || state_clicked.equalsIgnoreCase("State")){
            Dialogs.show_popUp(getResources().getString(R.string.Please_select_your_state),context);
        } else {

            Dialogs.ProgressDialog(context);

            JSONObject mainObject = new JSONObject();

            JSONObject data = new JSONObject();

            JSONObject userProfile = new JSONObject();

            JSONObject userProfileBody = new JSONObject();

            JSONArray userProfilesArray = new JSONArray();

            JSONObject userProfiles = new JSONObject();

            JSONObject error = new JSONObject();

            JSONObject ustrd = new JSONObject();

            JSONArray ustrdArray = new JSONArray();

            JSONObject ustrdArrayEmptyObject = new JSONObject();

            JSONObject header = new JSONObject();

            try {
                userProfileBody.put("createdAt", JSONObject.NULL);
                userProfileBody.put("district", district);
                userProfileBody.put("email", email);
                userProfileBody.put("geoLocation", geoLocation);
                userProfileBody.put("id", 0);
                userProfileBody.put("imeiNo", "");
                userProfileBody.put("name", name);
                userProfileBody.put("password", password);
                userProfileBody.put("phoneNo", mMobileNum);
                userProfileBody.put("pincode", pincode);
                userProfileBody.put("reference", "");
                userProfileBody.put("role", roleNumClicked);
                userProfileBody.put("state", state_clicked);
                userProfileBody.put("status", "");
                userProfileBody.put("town", city);
                userProfileBody.put("updatedAt", JSONObject.NULL);
                userProfileBody.put("village", village);
                userProfileBody.put("deliveryAddress", currentLocationAddress);

                userProfile.put("userProfile", userProfileBody);

                userProfiles.put("userProfiles", userProfilesArray);

                ustrdArray.put(ustrdArrayEmptyObject);

                ustrd.put("ustrd", ustrdArray);

                error.put("xcptInf", ustrd);

                data.put("reqType", "String");
                data.put("roles", new JSONObject());
                data.put("signIn", JSONObject.NULL);
                data.put("userProfile", userProfileBody);
                data.put("userProfiles", userProfilesArray);

                mainObject.put("data", data);
                mainObject.put("error", error);
                mainObject.put("header", header);

                JsonParser jsonParser = new JsonParser();

                JsonObject jsonObject = (JsonObject) jsonParser.parse(mainObject.toString());

                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);

                Call<LoginResponceModel> call = apiService.createUser("application/json", jsonObject);

                call.enqueue(new Callback<LoginResponceModel>() {
                    @Override
                    public void onResponse(Call<LoginResponceModel> call, Response<LoginResponceModel> response) {

                        Dialogs.Cancel();

                        if (response.code() == 200) {

                            if(response.body().getHeaderModel().getCode() == 200){
                                Toast.makeText(context, getResources().getString(R.string.registration_successful), Toast.LENGTH_SHORT).show();

                                Intent i1 = new Intent(Account_setup.this, Login.class);
                                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i1);
                            }else {
                                Dialogs.show_popUp(response.body().getHeaderModel().getMessage(), context);
                            }

                        } else {
                            Dialogs.show_popUp(response.message(), context);
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponceModel> call, Throwable t) {
                       Dialogs.Cancel();
                       Dialogs.show_popUp(getResources().getString(R.string.error) + ": " + t.getMessage(), context);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    void locationFetching(){
        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }

    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                Account_setup.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                Account_setup.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                getAddress(latitude, longitude);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  locationFetching();
    }
}

