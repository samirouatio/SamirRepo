package com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.MyApp;
import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.R;
import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.Useful.AudioService;
import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.MyAdNetworks.MyAdManager;
import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.Useful.InternetClassReceiver;
import com.yandex.mobile.ads.banner.BannerAdView;

public class PlayerName extends AppCompatActivity {
    EditText editTextPlayerName;
    Button next;

    MyAdManager myAdManager;
    View decoration;

    //Banners
    RelativeLayout maxRLBanner;
    BannerAdView bannerAdViewYndx;

    //audio
    Intent serviceIntent;

    //loading
    private Dialog adLoadingPopup;
    private Dialog AnswerPopup;

    InternetClassReceiver internetClassReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);

        internetClassReceiver =new InternetClassReceiver();

        MyApp.audioIsAtTheStart = true;

        adLoadingPopup = new Dialog(PlayerName.this);
        adLoadingPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adLoadingPopup.setContentView(R.layout.progress_ad_loading);
        adLoadingPopup.setCanceledOnTouchOutside(false);
        adLoadingPopup.setCancelable(false);

        bannerAdViewYndx = findViewById(R.id.yndxBanner);
        maxRLBanner = findViewById(R.id.generalBanner);


        myAdManager = new MyAdManager(this);
        myAdManager.adPicker();
        myAdManager.DisplayBanner(maxRLBanner, bannerAdViewYndx);


        //This will Hide Nav And Status Bar
        decoration = getWindow().getDecorView();
        decoration.setOnSystemUiVisibilityChangeListener(visibility -> {
            if (visibility == 0) {
                decoration.setSystemUiVisibility(SystemBars());
            }
        });


        serviceIntent = new Intent(PlayerName.this, AudioService.class);
        if (!MyApp.audioIsPlaying) {
            startService(serviceIntent);
            MyApp.audioIsPlaying = true;
        }


        //Answer PopUp
        AnswerPopup = new Dialog(PlayerName.this);
        AnswerPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AnswerPopup.setContentView(R.layout.help_us_improve_popup);
        AnswerPopup.setCanceledOnTouchOutside(false);
        AnswerPopup.setCancelable(false);
        AnswerPopup.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            if (visibility == 0) {
                decoration.setSystemUiVisibility(SystemBars());
            }
        });
        final Button Okay = AnswerPopup.findViewById(R.id.Okay);


        Okay.setOnClickListener(view -> {
            AnswerPopup.dismiss();
            adLoadingPopup.show();
            myAdManager.DisplayInter(() -> {
                adLoadingPopup.dismiss();
                Intent intent = new Intent(PlayerName.this, FirstActivity.class);
                startActivity(intent);
            });
        });


        next = findViewById(R.id.next);
        editTextPlayerName = findViewById(R.id.editTextPlayerName);

        next.setOnClickListener(view -> {

            String playerName = editTextPlayerName.getText().toString().trim();

            if (playerName.isEmpty()) {
                Toast.makeText(PlayerName.this, "Player name cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (playerName.length() < 5) {
                Toast.makeText(PlayerName.this, "Player name should contain at least 5 letters", Toast.LENGTH_SHORT).show();
            } else {
                MyApp.PlayerName = playerName;
                AnswerPopup.show();

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AudioService.resumeMusic();
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decoration.setSystemUiVisibility(SystemBars());
        }
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