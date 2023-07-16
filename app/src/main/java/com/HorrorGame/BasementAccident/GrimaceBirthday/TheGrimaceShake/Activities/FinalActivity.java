package com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.MyAdNetworks.MyAdManager;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.MyApp;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.MyClasses.WV_Helpers.AdBlockerChecker;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.MyClasses.WV_Helpers.AdBlockerHelper;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.MyClasses.WV_Helpers.MyChrome;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.MyClasses.WV_Helpers.MyBrowser;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.R;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.Useful.AudioService;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.Useful.Const;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.Useful.InternetClassReceiver;
import com.HorrorGame.BasementAccident.GrimaceBirthday.TheGrimaceShake.Useful.RatingBarDialog;

public class FinalActivity extends AppCompatActivity {

    VideoView myVideoView;
    WebView myGameView;

    MyAdManager myAdManager;

    View decoration;

    //loading
    Dialog adLoadingPopup;
    TextView myAdLoadingPopUpTV;

    public static SharedPreferences preferences = null;
    static boolean CheckIfActive = false;

    RatingBarDialog ratingBarDialog;

    MyBrowser myBrowser;

    Intent serviceIntent;

    InternetClassReceiver internetClassReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        internetClassReceiver =new InternetClassReceiver();

        serviceIntent = new Intent(FinalActivity.this, AudioService.class);

        if (MyApp.audioIsPlaying) {
            stopService(serviceIntent);
            MyApp.audioIsPlaying = false;

        }

        myVideoView = findViewById(R.id.myVideoView);
        myGameView = findViewById(R.id.myGameView);

        myAdManager = new MyAdManager(FinalActivity.this);


        //This will Hide Nav And Status Bar
        decoration = getWindow().getDecorView();
        decoration.setOnSystemUiVisibilityChangeListener(visibility -> {
            if (visibility == 0) {
                decoration.setSystemUiVisibility(SystemBars());
            }
        });

        adLoadingPopup = new Dialog(FinalActivity.this);
        adLoadingPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adLoadingPopup.setContentView(R.layout.progress_ad_loading);
        adLoadingPopup.setCanceledOnTouchOutside(false);
        adLoadingPopup.setCancelable(false);

        myAdLoadingPopUpTV = adLoadingPopup.findViewById(R.id.adLoadingTV);

        ratingBarDialog = new RatingBarDialog(this);
        ratingBarDialog.setCancelable(false);
        ratingBarDialog.setCanceledOnTouchOutside(false);


        if (Const.Type_Of_MainActivity.equals("video")) {
            myAdLoadingPopUpTV.setText("Game Loading...");
            myVideoView.setVideoURI(Uri.parse(Const.Video_Url));
            adLoadingPopup.show();
            new Handler().postDelayed(() -> {
                try {
                    adLoadingPopup.dismiss();
                    InitVideoPlayer();

                } catch (Exception ex) {

                }
            }, 5000);
        } else if (Const.Type_Of_MainActivity.equals("webview")) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            myGameView.setVisibility(View.VISIBLE);
            preferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
            //Instantiating the Rate us Dialogue


            if (preferences.getBoolean("firstrun", true)) {

                new Handler().postDelayed(() -> {
                    if (CheckIfActive) {
                        try {
                            ratingBarDialog.show();
                        } catch (Exception exception) {

                        }
                    }
                }, Const.Rating_Timer_To_PopUp);
            }


            if (Const.CPA_Is_Active) {
                new Handler().postDelayed(() -> {
                    try {
                        // TODO Show CPA
//                            CP4_P0pup.show();
                    } catch (Exception ex) {

                    }

                }, Const.CPA_Timer_To_Pop_Up);

            }


            myAdManager.adPicker();
            new AdBlockerHelper.init(this).Init(myGameView);
            AdBlockerChecker.Main_List_Of_Ads.addAll(MyApp.WV_EnablationArray);

            myGameView.clearCache(true);
            myGameView.clearHistory();
            if (Const.Review_Is_Here) {
                myGameView.loadUrl(Const.URL_Webview_Google);
            } else {
                myGameView.loadUrl(Const.URL_Webview_User);
            }

            myBrowser = new MyBrowser(myGameView);
            myGameView.setWebViewClient(myBrowser);
            myGameView.setWebChromeClient(new MyChrome(FinalActivity.this));
            WebSettings myGameViewSettings = myGameView.getSettings();
            myGameViewSettings.setJavaScriptEnabled(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                myGameViewSettings.setDisplayZoomControls(false);
            }

            myGameViewSettings.setBuiltInZoomControls(true);
            myGameViewSettings.setSupportZoom(true);
            myGameViewSettings.setDomStorageEnabled(true);
            myGameViewSettings.setLoadsImagesAutomatically(true);
            myGameViewSettings.setAllowFileAccess(true);
            myGameViewSettings.setUseWideViewPort(true);
            if (Const.WV_Support_Multiple_Windows_Active) {
                myGameViewSettings.setSupportMultipleWindows(true);
                myGameViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            }

            DisplayAdsRecursivlyy();

        }


    }

    public void DisplayAdsRecursivlyy() {
        try {
            new Handler().postDelayed(() -> DisplayChosenInter(), Const.Ads_Timer);
        } catch (Exception exception) {

        }

    }

    public void DisplayChosenInter() {
        try {
            adLoadingPopup.show();
            myGameView.onPause();
            myAdManager.DisplayInter(() -> {
                adLoadingPopup.dismiss();
                myGameView.onResume();
                DisplayAdsRecursivlyy();
            });
        } catch (Exception ex) {

        }
    }

    public void InitVideoPlayer() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        myVideoView.setVisibility(View.VISIBLE);
        myVideoView.start();
        myVideoView.setOnCompletionListener(mp -> {

            if (Const.CPA_Is_Active) {
                ShowXCPA();
            } else {
                ErrorAndGoBackToMenu();
            }
        });


    }

    private void ErrorAndGoBackToMenu() {
        myAdLoadingPopUpTV.setText("Loading...");
        adLoadingPopup.show();
        new Handler().postDelayed(() -> {
            adLoadingPopup.dismiss();
            Toast.makeText(FinalActivity.this, "Something went wront!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(FinalActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }, 2500);
    }

    private void ShowXCPA() {
        new Handler().postDelayed(() -> {
            try {
                // TODO Show CPA
//                                    CP4_P0pup.show();
            } catch (Exception ex) {

            }

        }, Const.CPA_Timer_To_Pop_Up);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myGameView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        myGameView.restoreState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (Const.Type_Of_MainActivity.equals("webview")) {
            if (myGameView.canGoBack()) {
                myGameView.goBack();

            } else {
                myGameView.destroy();
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onStop() {
        CheckIfActive = false;
        unregisterReceiver(internetClassReceiver);
        super.onStop();
    }

    @Override
    protected void onStart() {
        CheckIfActive = true;
        IntentFilter InternetFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetClassReceiver,InternetFilter);
        super.onStart();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (Const.Type_Of_MainActivity.equals("video")) return;
        // Check if auto-rotate is enabled
        boolean autoRotateEnabled = android.provider.Settings.System.getInt(
                getContentResolver(), android.provider.Settings.System.ACCELEROMETER_ROTATION, 0) == 1;

        if (!autoRotateEnabled) {
            // Lock the orientation to the current rotation
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            switch (rotation) {
                case Surface.ROTATION_0:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    break;
                case Surface.ROTATION_90:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
                case Surface.ROTATION_180:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    break;
                case Surface.ROTATION_270:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    break;
            }
        }
    }





}