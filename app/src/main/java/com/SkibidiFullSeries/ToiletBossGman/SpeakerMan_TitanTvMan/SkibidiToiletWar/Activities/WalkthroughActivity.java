package com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.MyApp;
import com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.MyClasses.WalkthroughClass;
import com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.R;
import com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.MyAdNetworks.MyAdManager;
import com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.Useful.AudioService;
import com.SkibidiFullSeries.ToiletBossGman.SpeakerMan_TitanTvMan.SkibidiToiletWar.Useful.InternetClassReceiver;
import com.yandex.mobile.ads.banner.BannerAdView;

public class WalkthroughActivity extends AppCompatActivity {


    private String fullDescription;
    private int charIndex;
    private Handler handler;
    private Runnable runnable;
    private ScrollView scrollView;

    private ImageView next, previos;

    TextView descriptionText;
    TextView titre;
    ImageView theImg;
    MyAdManager myAdManager;
    LinearLayout imgHolder;


    int modelPosition = 0;

    View decoration;

    //Banners
    RelativeLayout maxRLBanner;
    BannerAdView bannerAdViewYndx;

    //audio
    Intent serviceIntent;

    //loading
    Dialog adLoadingPopup;

    InternetClassReceiver internetClassReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);

        internetClassReceiver =new InternetClassReceiver();

        next = findViewById(R.id.next);
        previos = findViewById(R.id.previos);
        next.setVisibility(View.GONE);
        previos.setVisibility(View.GONE);

        titre = findViewById(R.id.guide_title);
        descriptionText = findViewById(R.id.guide_description);
        theImg = findViewById(R.id.guide_image);
        imgHolder = findViewById(R.id.imgHolder);

        bannerAdViewYndx = findViewById(R.id.yndxBanner);
        maxRLBanner = findViewById(R.id.generalBanner);

        serviceIntent = new Intent(WalkthroughActivity.this, AudioService.class);
        if (!MyApp.audioIsPlaying && !MyApp.activityDestroyed) {
            startService(serviceIntent);
            MyApp.audioIsPlaying = true;
        }


        //The Loading Dialogue While Waiting For the Ads
        adLoadingPopup = new Dialog(WalkthroughActivity.this);
        adLoadingPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adLoadingPopup.setContentView(R.layout.progress_ad_loading);
        adLoadingPopup.setCanceledOnTouchOutside(false);

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

        scrollView = findViewById(R.id.myScrollView);

        FillTheWalkThrough();

        next.setOnClickListener(view -> {
            adLoadingPopup.show();
            myAdManager.DisplayInter(() -> {
                adLoadingPopup.dismiss();
                nextWalkThrough();
            });

        });


    }

    public void writeDescription() {
        charIndex = 0;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (charIndex < fullDescription.length()) {
                    char currentChar = fullDescription.charAt(charIndex);
                    descriptionText.append(String.valueOf(fullDescription.charAt(charIndex)));
                    charIndex++;


                    // Define the pause durations at certain marks
                    long delay = 20; // Default delay between characters
                    if (currentChar == ',') {
                        delay = 50;
                    }


                    handler.postDelayed(this, delay); // Adjust the delay time as needed

                    if (isScrolledToBottom()) {
                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        });
                    }

                    // Check if text has finished writing
                    if (charIndex == fullDescription.length()) {
                        animateViewVisible(next, previos);

                    }
                }
            }
        };

        handler.postDelayed(runnable, 50);
    }

    private void FillTheWalkThrough() {
        WalkthroughClass currentModel = MyApp.walkthroughClassArrayList.get(modelPosition);
        titre.setText(currentModel.getTitre());
        descriptionText.setText("");
        fullDescription = currentModel.getDescription();
        if (!currentModel.getImage().equals("")) {
            imgHolder.setVisibility(View.VISIBLE);
        } else {
            imgHolder.setVisibility(View.GONE);
        }
        Glide.with(getApplicationContext()).load(currentModel.getImage()).into(theImg);
        writeDescription();
    }

    private void nextWalkThrough() {
        if (modelPosition + 1 < MyApp.walkthroughClassArrayList.size()) {
            next.setVisibility(View.GONE);
            previos.setVisibility(View.GONE);
            modelPosition++;
            FillTheWalkThrough();
        } else {
            Intent intent = new Intent(WalkthroughActivity.this, FinalActivity.class);
            this.startActivity(intent);
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

    private void animateViewVisible(View next, View previous) {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        next.startAnimation(fadeInAnimation);
        next.setVisibility(View.VISIBLE);
        if (modelPosition == 0) return;
        previous.startAnimation(fadeInAnimation);
        previous.setVisibility(View.VISIBLE);
    }

    private boolean isScrolledToBottom() {
        int scrollY = scrollView.getScrollY();
        int scrollViewHeight = scrollView.getHeight();
        int contentHeight = scrollView.getChildAt(0).getHeight();

        return (scrollY + scrollViewHeight >= contentHeight);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);

        if (MyApp.audioIsPlaying) {
            stopService(serviceIntent);
            MyApp.audioIsPlaying = false;
        }
        MyApp.activityDestroyed = true;
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


    @Override
    protected void onRestart() {
        super.onRestart();
    }
}