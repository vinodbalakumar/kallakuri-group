package com.example.kallakurigroup.activity;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.kallakurigroup.R;
import com.example.kallakurigroup.fragments.AboutUsFragment;
import com.example.kallakurigroup.fragments.ContactUsFragment;
import com.example.kallakurigroup.fragments.FeedBackFragment;
import com.example.kallakurigroup.fragments.homefragment.HomeFragment;
import com.example.kallakurigroup.models.localdbmodels.UserTableModel;
import com.example.kallakurigroup.utils.Storage;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;


public class Homepage extends AppCompatActivity {

    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    MaterialToolbar toolbar;

    // define a variable to track hamburger-arrow state
    protected boolean isHomeAsUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(Homepage.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // overwrite Navigation OnClickListener that is set by ActionBarDrawerToggle
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (isHomeAsUp){
                    onBackPressed();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        setHomeAsUp(isHomeAsUp);

        View view = navigationView.getHeaderView(0);
        TextView username =view.findViewById(R.id.headerUsername);
        TextView phoneNo =view.findViewById(R.id.headerPhoneNo);

        Storage storage = new Storage(Homepage.this);
        UserTableModel userDetails = storage.getUserDetails();
        username.setText(userDetails.getName());
        phoneNo.setText(userDetails.getPhoneNo());

        HomeFragment homeFragment = new HomeFragment();
        loadFragment(homeFragment);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.optionHome:
                        HomeFragment homeFragment = new HomeFragment();
                        loadFragment(homeFragment);
                        break;
                    case R.id.optionContactUs:
                        ContactUsFragment contactUsFragment = new ContactUsFragment();
                        loadFragment(contactUsFragment);
                        break;
                    case R.id.optionFeedBack:
                        FeedBackFragment feedBackFragment = new FeedBackFragment();
                        loadFragment(feedBackFragment);
                        break;
                    case R.id.optionAboutUs:
                        AboutUsFragment aboutUsFragment = new AboutUsFragment();
                        loadFragment(aboutUsFragment);
                        break;
                    case R.id.optionLogout:
                        exitApp("Logout","KkGroups Alert!", "Are you sure you want to logout?");
                        break;
                }

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.homeOptionKart:
                Toast.makeText(this, "Kart Selected", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment, "HomePage");
        fragmentTransaction.commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            exitApp("Close", "KkGroups Alert!", "Are you sure you want to leave this page?");
        }
    }

    private void exitApp(final String actionType, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Homepage.this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (actionType.equals("Close")) {
                            moveTaskToBack(true);
                        }
                        if (actionType.equals("Logout")) {
                            Storage storage = new Storage(Homepage.this);
                            storage.deleteUser();
                            startActivity(new Intent(Homepage.this, Login.class));
                        }
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    // call this method for animation between hamburged and arrow
    protected void setHomeAsUp(boolean isHomeAsUp) {
        if (this.isHomeAsUp != isHomeAsUp) {
            this.isHomeAsUp = isHomeAsUp;

            ValueAnimator anim = isHomeAsUp ? ValueAnimator.ofFloat(0, 1) : ValueAnimator.ofFloat(1, 0);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float slideOffset = (Float) valueAnimator.getAnimatedValue();
                    actionBarDrawerToggle.onDrawerSlide(drawerLayout, slideOffset);
                }
            });
            anim.setInterpolator(new DecelerateInterpolator());
            // You can change this duration to more closely match that of the default animation.
            anim.setDuration(400);
            anim.start();
        }
    }

}
