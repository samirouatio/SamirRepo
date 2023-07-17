package com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.Activities;

import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.MyAdNetworks.MyAdManager;
import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.MyClasses.SpinningWheel.SpinningWheel;
import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.MyClasses.SpinningWheel.OnLuckyWheelReachTheTarget;
import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.MyClasses.SpinningWheel.SpinningWheelItem;
import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.R;
import com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.Useful.InternetClassReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinningActivity extends AppCompatActivity {

    TextView app_title;
    int randomIndex;
    String chosen;
    private SpinningWheel lw;
    List<SpinningWheelItem> spinningWheelItems;

    MyAdManager myAdManager;
    Dialog adLoadingPopup;

    InternetClassReceiver internetClassReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        style(this);
        setContentView(R.layout.activity_spinning);

        internetClassReceiver =new InternetClassReceiver();

        myAdManager = new MyAdManager(this);
        myAdManager.adPicker();

        adLoadingPopup = new Dialog(SpinningActivity.this);
        adLoadingPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adLoadingPopup.setContentView(R.layout.progress_ad_loading);
        adLoadingPopup.setCanceledOnTouchOutside(false);
        adLoadingPopup.setCancelable(false);

        app_title = findViewById(R.id.app_title);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.vector).animate().rotationBy(360)
                        .withEndAction(this).setDuration(3000)
                        .setInterpolator(new LinearInterpolator()).start();
            }
        };

        findViewById(R.id.vector).animate().rotationBy(360)
                .withEndAction(runnable).setDuration(3000)
                .setInterpolator(new LinearInterpolator()).start();

        Animation animDown = AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom);
        Animation animUp = AnimationUtils.loadAnimation(this, R.anim.slide_from_top);
        Animation zoomIn = AnimationUtils.loadAnimation(this, R.anim.main_bounce);
        findViewById(R.id.downBox).setAnimation(animDown);
        findViewById(R.id.upBox).setAnimation(animUp);
        findViewById(R.id.luckyWheel).setAnimation(zoomIn);

        findViewById(R.id.rescratch).setOnClickListener(enter -> {
            Animation hang_fall = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
            hang_fall.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationEnd(Animation animation) {
                    adLoadingPopup.show();
                    myAdManager.DisplayInter(() -> {
                        adLoadingPopup.dismiss();
                        next();
                    });
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }
            });
            enter.startAnimation(hang_fall);
        });

        findViewById(R.id.finish).setOnClickListener(enter -> {
            Animation hang_fall = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
            hang_fall.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationEnd(Animation animation) {
                    adLoadingPopup.show();
                    myAdManager.DisplayInter(() -> {
                        adLoadingPopup.dismiss();
                        finish();
                    });
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }
            });
            enter.startAnimation(hang_fall);
        });

        generateWheelItems();
        randomItem();

        lw = findViewById(R.id.luckyWheel);
        lw.addSpinningWheelItems(spinningWheelItems);
        lw.setMyTarget(randomIndex + 1);

        lw.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                app_title.setText("You won " + chosen + ".");
                SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                int new_score = Integer.parseInt(chosen);
                int old_score = sp.getInt("key_score", -1);
                int total_score = old_score + new_score;
                editor.putInt("key_score", total_score);
                editor.apply();
                randomItem();
                //SHOW AD TODO
                adLoadingPopup.show();
                myAdManager.DisplayInter(() -> {
                    adLoadingPopup.dismiss();
                });

            }
        });

        findViewById(R.id.play).setOnClickListener(v -> {
            Animation hang_fall = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
            hang_fall.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationEnd(Animation animation) {
                    adLoadingPopup.show();
                    myAdManager.DisplayInter(() -> {
                        adLoadingPopup.dismiss();
                        play();
                    });

                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }
            });
            v.startAnimation(hang_fall);
        });

        findViewById(R.id.quit).setOnClickListener(v -> {
            Animation hang_fall = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
            hang_fall.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationEnd(Animation animation) {
                    finish();
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }
            });
            v.startAnimation(hang_fall);
        });
    }

    void play() {
        randomItem();
        lw.spinTheWheelTo(randomIndex + 1);
    }

    private void randomItem() {
        Random random = new Random();
        randomIndex = random.nextInt(spinningWheelItems.size());
        chosen = spinningWheelItems.get(randomIndex).text;
    }

    private void generateWheelItems() {
        spinningWheelItems = new ArrayList<>();
        spinningWheelItems.add(new SpinningWheelItem(Color.parseColor("#FFE5B4"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_coin), "100"));
        spinningWheelItems.add(new SpinningWheelItem(Color.parseColor("#FFA500"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_coin), "25"));
        spinningWheelItems.add(new SpinningWheelItem(Color.parseColor("#D58A00"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_coin), "30"));

        spinningWheelItems.add(new SpinningWheelItem(Color.parseColor("#FFE5B4"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_coin), "0"));
        spinningWheelItems.add(new SpinningWheelItem(Color.parseColor("#FFA500"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_coin), "90"));
        spinningWheelItems.add(new SpinningWheelItem(Color.parseColor("#D58A00"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_coin), "110"));

        spinningWheelItems.add(new SpinningWheelItem(Color.parseColor("#FFE5B4"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_coin), "0"));
        spinningWheelItems.add(new SpinningWheelItem(Color.parseColor("#FFA500"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_coin), "10"));
        spinningWheelItems.add(new SpinningWheelItem(Color.parseColor("#D58A00"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_coin), "60"));

        spinningWheelItems.add(new SpinningWheelItem(Color.parseColor("#FFE5B4"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_coin), "125"));
        spinningWheelItems.add(new SpinningWheelItem(Color.parseColor("#FFA500"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_coin), "0"));
        spinningWheelItems.add(new SpinningWheelItem(Color.parseColor("#D58A00"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_coin), "75"));
    }

    private void next() {
        Intent intent = new Intent(getApplicationContext(), SpinningActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    public void onStart() {
        if (findViewById(R.id.ad_frame_banner) != null){

        }
            //TODO Show Banner
//            ANChooser.ShowBanner(this, findViewById(R.id.ad_frame_banner));

        IntentFilter InternetFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetClassReceiver,InternetFilter);
            super.onStart();
    }

    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.fade_out, R.anim.fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onStop() {
        unregisterReceiver(internetClassReceiver);
        super.onStop();
    }

    public static void style(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        View v = activity.getWindow().getDecorView();
        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        v.setOnSystemUiVisibilityChangeListener(visibility -> {
            if (visibility == 0)
                v.setSystemUiVisibility(
                        SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        });
    }




}