package com.simulation.kingapp.learning.newtricks.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.simulation.kingapp.learning.newtricks.MyApp;
import com.simulation.kingapp.learning.newtricks.R;
import com.simulation.kingapp.learning.newtricks.Useful.AudioService;
import com.simulation.kingapp.learning.newtricks.Useful.MyAdManager;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);



        adLoadingPopup = new Dialog(PlayerName.this);
        adLoadingPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adLoadingPopup.setContentView(R.layout.progress_ad_loading);
        adLoadingPopup.setCanceledOnTouchOutside(false);
        adLoadingPopup.setCancelable(false);

        bannerAdViewYndx = findViewById(R.id.yndxBanner);
        maxRLBanner = findViewById(R.id.generalBanner);


        myAdManager = new MyAdManager(this);
        myAdManager.adPicker();
        myAdManager.DisplayBanner(maxRLBanner,bannerAdViewYndx);


        //This will Hide Nav And Status Bar
        decoration =getWindow().getDecorView();
        decoration.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility==0){
                    decoration.setSystemUiVisibility(SystemBars());
                }
            }
        });


        serviceIntent = new Intent(PlayerName.this, AudioService.class);
        if(!MyApp.audioIsPlaying){
            startService(serviceIntent);
            MyApp.audioIsPlaying = true;
        }


        //Answer PopUp
        AnswerPopup = new Dialog(PlayerName.this);
        AnswerPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AnswerPopup.setContentView(R.layout.help_us_improve_popup);
        AnswerPopup.setCanceledOnTouchOutside(false);
        AnswerPopup.setCancelable(false);
        final Button Okay = AnswerPopup.findViewById(R.id.Okay);



        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerPopup.dismiss();
                adLoadingPopup.show();
                myAdManager.DisplayInter(new MyAdManager.ManagerAdIzOver() {
                    @Override
                    public void ManagerInterIsOver() {
                        adLoadingPopup.dismiss();
                        Intent intent = new Intent(PlayerName.this, FirstActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });






        next = findViewById(R.id.next);
        editTextPlayerName = findViewById(R.id.editTextPlayerName);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String playerName = editTextPlayerName.getText().toString().trim();

                if (playerName.isEmpty()) {
                    Toast.makeText(PlayerName.this, "Player name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (playerName.length() < 5) {
                    Toast.makeText(PlayerName.this, "Player name should contain at least 5 letters", Toast.LENGTH_SHORT).show();
                } else {
                    MyApp.PlayerName = playerName;
                    AnswerPopup.show();

                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AudioService.resumeMusic();
    }

    public int SystemBars(){
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
        if(hasFocus){
            decoration.setSystemUiVisibility(SystemBars());
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
//        AudioService.pauseMusic();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(MyApp.audioIsPlaying){
            stopService(serviceIntent);
            MyApp.audioIsPlaying = false;
        }
    }
}