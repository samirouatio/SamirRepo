package com.simulation.kingapp.learning.newtricks.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.simulation.kingapp.learning.newtricks.MyApp;
import com.simulation.kingapp.learning.newtricks.MyClasses.FirstClass;
import com.simulation.kingapp.learning.newtricks.R;
import com.simulation.kingapp.learning.newtricks.Useful.AudioService;
import com.simulation.kingapp.learning.newtricks.Useful.MyAdManager;
import com.bumptech.glide.Glide;
import com.yandex.mobile.ads.banner.BannerAdView;

import java.util.ArrayList;


public class FirstActivity extends AppCompatActivity {
    MyAdManager myAdManager;
    ArrayList<FirstClass> FirstClassArray = new ArrayList<>();

    LinearLayout clickMe;

    TextView Question, Answer1,Answer2;
    ImageView Iv1,Iv2;
    int modelPosition = 0;

    View decoration;

    //Banners
    RelativeLayout maxRLBanner;
    BannerAdView bannerAdViewYndx;

    //audio
    Intent serviceIntent;

    //loading
    private Dialog adLoadingPopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        adLoadingPopup = new Dialog(FirstActivity.this);
        adLoadingPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adLoadingPopup.setContentView(R.layout.progress_ad_loading);
        adLoadingPopup.setCanceledOnTouchOutside(false);
        adLoadingPopup.setCancelable(false);


        Question = findViewById(R.id.TextViewSelect);
        Answer1 = findViewById(R.id.Answer1);
        Answer2 = findViewById(R.id.Answer2);
        Iv1 = findViewById(R.id.myGif1);
        Iv2 = findViewById(R.id.myGif2);

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

        fillTheArrayModels();
        fillWithQuestions();


         serviceIntent = new Intent(FirstActivity.this, AudioService.class);


        clickMe = findViewById(R.id.clickMe);
        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();
//                adLoadingPopup.show();
//                myAdManager.DisplayInter(new MyAdManager.ManagerAdIzOver() {
//                    @Override
//                    public void ManagerInterIsOver() {
//                        adLoadingPopup.dismiss();
//
//                    }
//                });
            }
        });

    }





    @Override
    protected void onResume() {
        super.onResume();
        AudioService.resumeMusic();
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
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decoration.setSystemUiVisibility(SystemBars());
        }
    }

    public int SystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    }

    private void fillWithQuestions(){
        FirstClass currentModel = FirstClassArray.get(modelPosition);
        Question.setText(currentModel.getQuestion());
        Answer1.setText(currentModel.getAnswer1());
        Answer2.setText(currentModel.getAnswer2());
        Glide.with(getApplicationContext()).load(currentModel.getIv1()).circleCrop().into(Iv1);
        Glide.with(getApplicationContext()).load(currentModel.getIv2()).circleCrop().into(Iv2);
    }
    private void nextQuestion(){
        if(modelPosition+1<FirstClassArray.size()){
            modelPosition++;
            fillWithQuestions();
        }
        else {
            Intent intent= new Intent(FirstActivity.this,SecondActivity.class);
            this.startActivity(intent);
        }

    }
    private void fillTheArrayModels() {
        FirstClass model1 = new FirstClass("Please select your gender","Boy","Girl", R.drawable.male,R.drawable.female);
        FirstClass model2 = new FirstClass("Are you 18+ ?","Above 18","Under 18", R.drawable.above_eighteen,R.drawable.under_eighteen);
        FirstClass model3 = new FirstClass("Please select your gender","Male","Female", R.drawable.male,R.drawable.female);
        FirstClass model4 = new FirstClass("Please select your gender","Male","Female", R.drawable.male,R.drawable.female);
        FirstClass model5 = new FirstClass("Please select your gender","Male","Female", R.drawable.male,R.drawable.female);
        FirstClassArray.add(model1);
        FirstClassArray.add(model2);
        FirstClassArray.add(model3);
        FirstClassArray.add(model4);
        FirstClassArray.add(model5);
    }



}