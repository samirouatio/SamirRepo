package com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.Adapters.MenuAdapter;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.Adapters.SpannableGridLayoutManager;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.MyApp;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.MyClasses.MenuItems;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.R;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.Useful.AudioService;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.MyAdNetworks.MyAdManager;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.Useful.InternetClassReceiver;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.Useful.RatingBarDialog;
import com.yandex.mobile.ads.banner.BannerAdView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    MyAdManager myAdManager;
    View decoration;

    //Banners
    RelativeLayout maxRLBanner;
    BannerAdView bannerAdViewYndx;

    //audio
    Intent serviceIntent;

    //loading
    Dialog adLoadingPopup;
    RatingBarDialog ratingBarDialog;

    RecyclerView recyclerView;
    MenuAdapter castAdapter;
    List<MenuItems> castItems;

    InternetClassReceiver internetClassReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        internetClassReceiver =new InternetClassReceiver();

        MyApp.audioIsAtTheStart = false;

        bannerAdViewYndx = findViewById(R.id.yndxBanner);
        maxRLBanner = findViewById(R.id.generalBanner);

        myAdManager = new MyAdManager(this);
        myAdManager.adPicker();
        myAdManager.DisplayBanner(maxRLBanner, bannerAdViewYndx);

        adLoadingPopup = new Dialog(MenuActivity.this);
        adLoadingPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adLoadingPopup.setContentView(R.layout.progress_ad_loading);
        adLoadingPopup.setCanceledOnTouchOutside(false);
        adLoadingPopup.setCancelable(false);

        ratingBarDialog = new RatingBarDialog(this);
        ratingBarDialog.setCancelable(false);
        ratingBarDialog.setCanceledOnTouchOutside(false);
        ratingBarDialog.show();

        //This will Hide Nav And Status Bar
        decoration = getWindow().getDecorView();
        decoration.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    decoration.setSystemUiVisibility(SystemBars());
                }
            }
        });


        serviceIntent = new Intent(MenuActivity.this, AudioService.class);

        if (MyApp.audioIsPlaying) {
            stopService(serviceIntent);
            MyApp.audioIsPlaying = false;

        }
        menu();

    }

    private void menu() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        SpannableGridLayoutManager gridLayoutManager = new SpannableGridLayoutManager(position -> {
            if (position % 12 == 0 || position % 12 == 7) {
                return new SpannableGridLayoutManager.SpanInfo(2, 2);
                //this will count of row and column you want to replace
            } else {
                return new SpannableGridLayoutManager.SpanInfo(1, 1);
            }
        }, 3, 1f);

        GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 1);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        castItems = new ArrayList<>();

        castItems.add(new MenuItems("Tips\nHow To Play", "", "#FB923C"));
        castItems.add(new MenuItems("", "https://i.imgur.com/T9KM7ZG.png", "#FBBF24"));
//        castItems.add(new MenuItems("", "https://img.icons8.com/play.png", "#FBBF24"));
        castItems.add(new MenuItems("", "https://img.icons8.com/ios/150/ffffff/rgb-circle-1.png", "#A3E635"));
        castItems.add(new MenuItems("", "https://img.icons8.com/ios/150/ffffff/erase-image.png", "#38BDF8"));
        castItems.add(new MenuItems("", "https://img.icons8.com/ios/150/ffffff/how-many-quest--v2.png", "#2DD4BF"));
        castItems.add(new MenuItems("", "https://img.icons8.com/ios/150/ffffff/what.png", "#4ADE80"));

        castAdapter = new MenuAdapter(castItems, this, this);
        recyclerView.setAdapter(castAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (MyApp.audioIsPlaying) {
            stopService(serviceIntent);
            MyApp.audioIsPlaying = false;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decoration.setSystemUiVisibility(SystemBars());
        }
    }

    public int SystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    }

    @Override
    protected void onStart() {
        IntentFilter InternetFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetClassReceiver,InternetFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(internetClassReceiver);
        super.onStop();
    }

}